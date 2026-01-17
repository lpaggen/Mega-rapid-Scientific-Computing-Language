package Parser;

import AST.Expressions.*;
import AST.Expressions.BinaryOperations.BinaryNode;
import AST.Expressions.Functions.FunctionCallNode;
import AST.Expressions.Functions.FunctionDeclarationNode;
import AST.Expressions.Functions.BuiltIns.ImportNode;
import AST.Literals.*;
import AST.Literals.Abstract.BraceBlockNode;
import AST.Literals.Abstract.BracketLiteralNode;
import AST.Literals.Graph.GraphLiteralNode;
import AST.Literals.Graph.NodeLiteralNode;
import AST.Literals.Linalg.MatrixLiteralNode;
import AST.Statements.*;
import Types.*;
import Types.Abstract.*;
import Util.ErrorHandler;
import Lexer.TokenKind;
import Lexer.Token;
import Util.WarningLogger;
import jdk.jfr.Label;

import java.lang.reflect.Array;
import java.util.*;

// IMPORTANT NOTE
// the parser is ONLY responsible for parsing the code and building the AST
// it does NOT check for semantic errors or type mismatches
// that is the job of the interpreter or a separate semantic analyzer
// the parser will throw runtime exceptions if it encounters unexpected tokens
// TODO the parser ONLY cares about SYNTAX AND GRAMMAR -> yeah, why did I deviate from this again?
// -> so "int x = 5.7;" is perfectly fine for the parser, but not for the interpreter
public class Parser {
    private final List<Token> tokens;
    private int tokenPos = 0;
    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    // AST is just an array of statements
    public ArrayList<Statement> parseProgram() {  // this method builds the AST, and must therefore return it
        WarningLogger warningHandler = new WarningLogger();
        warningHandler.clearWarningsFile();
        ArrayList<Statement> programBody = new ArrayList<>();
        while (!isAtEnd()) {
            Statement statement = parseStatement();
            if (statement == null) {
                // if we reach here, it means we have an empty statement or an unrecognized token
                advance(); // just consume the token and continue -- will have to fix this in a future build
                // continue;
            }
        }
        // warningHandler.printWarnings();
        return programBody;
    }

    // everything should really start from the Statement, since we have to declare variables, functions, etc.
    private Statement parseStatement() {
        System.out.println("current token at parseStatement: " + peek());
        if (isDeclarationStart()) {
            System.out.println("Parsing variable declaration...");
            return parseDeclaration();
        }
        else if (isFunctionDeclarationStart()) {
            System.out.println("Parsing function declaration...");
            return parseFunctionDeclaration();
        }
        else if (isConditionalBranch()) {
            System.out.println("Parsing conditional branch...");
            return parseConditionalBranch();
        }
        else if (check(TokenKind.INCLUDE)) {
            System.out.println("Parsing module import statement: " + peek());
            advance(); // consume INCLUDE
            Token moduleName = consume(TokenKind.IDENTIFIER);
            consume(TokenKind.SEMICOLON);
            return new ImportNode(moduleName.getLexeme(), null);
        }
        else if (peek().getKind() == TokenKind.IDENTIFIER && lookAhead(1).getKind() == TokenKind.EQUAL) {
            System.out.println("Parsing variable reassignment...");
            return parseVariableReassignment();
        }
        else if (peek().getKind() == TokenKind.WHILE) {
            return parseWhileLoop();
        }
        else {
            Expression expr = parseExpression(); // will handle identifiers, function calls, parenthesis, etc.
            consume(TokenKind.SEMICOLON); // ensure semicolon is consumed
            return new ExpressionStatementNode(expr);
        }
    }

    private Expression parseExpression() {
        System.out.println("current token at parseExpression: " + peek());
        return parseEquality();
    }

    // i don't know if this still even works anymore
    private Expression parseEquality() {
        Expression expression = parseComparison();
        while (match(TokenKind.EQUAL_EQUAL, TokenKind.NOT_EQUAL)) {
            Expression rhs = parseComparison();
            expression = new BinaryNode(expression, TokenKind.EQUAL, rhs);
        }
        return expression;
    }

    private Expression parseComparison() {
        Expression expression = parseTerm();
        while (match(TokenKind.GREATER, TokenKind.LESS, TokenKind.GREATER_EQUAL, TokenKind.LESS_EQUAL)) {
            Token operator = previous(); // because match() consumes the token (advances position)
            Expression rhs = parseTerm(); // here we are also consuming the next token, which ensures the while loop actually works
            expression = new BinaryNode(expression, operator.getKind(), rhs);
        }
        return expression;
    }

    private Expression parseTerm() {
        Expression expression = parseFactor();
        while (match(TokenKind.PLUS, TokenKind.MINUS)) {
            System.out.println("current token at parseTerm: " + peek());
            Token operator = previous();
            Expression rhs = parseFactor();
            expression = new BinaryNode(expression, operator.getKind(), rhs);
        }
        return expression;
    }

    private Expression parseFactor() {
        Expression expression = parseUnary();
        while (match(TokenKind.MUL, TokenKind.DIV)) {
            System.out.println("current token at parseFactor: " + peek());
            Token operator = previous();
            Expression rhs = parseUnary();
            expression = new BinaryNode(expression, operator.getKind(), rhs);
        }
        return expression;
    }

    private Expression parseUnary() {
        if (match(TokenKind.NOT, TokenKind.MINUS, TokenKind.PLUS)) { // handle both ! and negation
            // with match() we skip the token and advance the position
            Token operator = previous();
            Expression rhs = parseUnary();
            return new UnaryNode(operator, rhs);
        }
        System.out.println("current token at parseUnary: " + peek());
        return parsePrimary();
    }

    private Expression parsePrimary() {
        System.out.println("current token at parsePrimary: " + peek());
        if (match(TokenKind.FALSE)) return new BooleanLiteralNode(false); // this will return a PrimaryNode with a BooleanNode inside
        else if (match(TokenKind.TRUE)) return new BooleanLiteralNode(true); // this will return a PrimaryNode with a BooleanNode inside
        else if (match(TokenKind.NULL)) return new PrimaryNode(null);
        else if (match(TokenKind.STRING)) {
            System.out.println("Parsing string literal: " + previous().getLiteral());
            return new StringLiteralNode(previous().getLexeme());
        }
        else if (match(TokenKind.INTEGER, TokenKind.FLOAT)) {
            System.out.println("Parsing scalar literal: " + previous().getLiteral());
            return new ScalarLiteralNode((Number) previous().getLiteral());
        }
        else if (match(TokenKind.OPEN_PAREN)) {
            Expression expr = parseExpression();
            consume(TokenKind.CLOSE_PAREN);
            return new GroupingNode(expr);
        }
        else if (match(TokenKind.OPEN_BRACKET)) {  // need a mechanism to differentiate between graphs, matrices...
            return parseBracketLiteral();
        }
        else if (match(TokenKind.OPEN_BRACE)) {
            return parseBraceLiteral();
        }
        // this is really not good and not safe, and it's a dumb check. but it works until i figure something better out
        else if (match(TokenKind.IDENTIFIER)) {
            Token name = previous();
            System.out.println("current token after identifier match: " + peek());
            Expression expr = new VariableNode(name.getLexeme());
            while (match(TokenKind.DOT)) {  // member access detected
                System.out.println("Parsing member access for: " + name.getLexeme());
                expr = parseMemberAccess(expr);
            }
            if (match(TokenKind.OPEN_PAREN)) {  // function call detected
                java.util.List<Expression> args = parseFunctionArguments();
                consume(TokenKind.CLOSE_PAREN);
                return new FunctionCallNode(name.getLexeme(), args);
            }
            System.out.println("Parsing variable: " + name.getLexeme());
            // just check for increment here
            if (match(TokenKind.INCREMENT, TokenKind.DECREMENT)) {
                Token operator = previous();
                return new IncrementNode(operator.getKind(), new VariableNode(name.getLexeme()));
            }
            return expr;
        }
        System.out.println("Unexpected token at parsePrimary: " + peek());
        throw new ErrorHandler(
                "parsing",
                peek().getLine(),
                "ParsePrimary Unexpected token: " + peek().getLexeme(),
                "Expected an expression, variable, or literal value."
        );
    }

    private Expression parseBracketLiteral() {
        List<Expression> elements = new ArrayList<>();
        if (!check(TokenKind.CLOSE_BRACKET)) {
            do {
                elements.add(parseExpression());
            } while (match(TokenKind.COMMA));
        }
        consume(TokenKind.CLOSE_BRACKET);
        return new BracketLiteralNode(elements);
    }

    private Expression parseBraceLiteral() {
        List<Statement> elements = new ArrayList<>();
        if (!check(TokenKind.CLOSE_BRACE)) {
            do {
                elements.add(parseStatement());
            } while (match(TokenKind.COMMA));
        }
        consume(TokenKind.CLOSE_BRACE);
        return new BraceBlockNode(elements);
    }

    // return the GetMember function under the hood for some attributes
    private Expression parseMemberAccess(Expression base) {
        String member = consume(TokenKind.IDENTIFIER).getLexeme();
        Expression access = new FunctionCallNode("getMember", List.of(base, new StringLiteralNode(member)));
        while (match(TokenKind.DOT)) {
            String nextMember = consume(TokenKind.IDENTIFIER).getLexeme();
            access = new FunctionCallNode("getMember", List.of(access, new StringLiteralNode(nextMember)));
        }
        return access;
    }

    private TypeNode parseType() {
        TokenKind type = peek().getKind();
        return switch (type) {
            case SCALAR_TYPE -> { advance(); yield new ScalarTypeNode(); }
            case MATRIX_TYPE -> { advance(); yield parseMatrixType(); }
            case GRAPH_TYPE -> { advance(); yield parseGraphType(); }
            case NODE_TYPE -> { advance(); yield new NodeTypeNode(); }
            // case EDGE_TYPE -> { advance(); yield new EdgeTypeNode(); }
            case LIST_TYPE -> { advance(); yield parseListType(); }
            default -> throw new RuntimeException("Expected a type, got " + type);
        };
    }

    private TypeNode parseListType() {
        consume(TokenKind.LESS);
        TypeNode innerType = parseType();
        consume(TokenKind.GREATER);
        return new ListTypeNode(innerType);
    }

    // using Dimension to prevent user from calling functions etc. as dimensions, gets hard to solve
    private TypeNode parseMatrixType() {  // have to advance tokens etc
        Dimension A = null;
        Dimension B = null;
        consume(TokenKind.LESS);
        TypeNode innerType = parseType();
        if (match(TokenKind.COMMA)) {  // user specifies a dimension already
            MatrixShape dimensions = parseDimension();
            A = dimensions.rows();
            B = dimensions.cols();
        }
        consume(TokenKind.GREATER);
        return new MatrixTypeNode(innerType, A, B);
    }

    private MatrixShape parseDimension() {
        Dimension left = parseDimAddSub();
        consume(TokenKind.AT);
        Dimension right = parseDimAddSub();
        return new MatrixShape(left, right);
    }

    // parsing the dimensions is once again a binary, recursive descent problem
    private Dimension parseDimAddSub() {
        Dimension left = parseDimMulDiv();
        while (match(TokenKind.PLUS, TokenKind.MINUS)) {
            Token op = previous();
            Dimension right = parseDimMulDiv();
            left = new BinaryDimension(left, right, toDimOp(op));
        }
        return left;
    }

    private Dimension parseDimMulDiv() {
        Dimension left = parseDimAtom();
        while (match(TokenKind.MUL, TokenKind.DIV)) {
            Token op = previous();
            Dimension right = parseDimAtom();
            left = new BinaryDimension(left, right, toDimOp(op));
        }
        return left;
    }

    private Dimension parseDimAtom() {
        if (match(TokenKind.INTEGER)) {
            return new KnownDimension((Integer) previous().getLiteral());  // make a new method to avoid casting, unsafe
        }
        if (match(TokenKind.IDENTIFIER)) {
            return new SymbolicDimension(previous().getLexeme());
        }
        if (match(TokenKind.OPEN_PAREN)) {
            Dimension dim = parseDimAddSub();
            consume(TokenKind.CLOSE_PAREN);
            return dim;
        }
        throw new RuntimeException("Line " + peek().getLine() + ": invalid dimension. " +
                "Expected one of <INTEGER, SYMBOL>, got dimension of type "
                + peek().getKind() + " with value: " + peek().getLexeme());
    }

    private BinaryDimension.Op toDimOp(Token token) {
        return switch (token.getKind()) {
            case PLUS  -> BinaryDimension.Op.ADD;
            case MINUS -> BinaryDimension.Op.SUB;
            case MUL  -> BinaryDimension.Op.MUL;
            case DIV -> BinaryDimension.Op.DIV;
            case MOD   -> BinaryDimension.Op.MOD;
            default -> throw new RuntimeException("Invalid operator in dimension expression");
        };
    }

    // will fix this to use an Enum to be safer
    private TypeNode parseGraphType() {
        consume(TokenKind.LESS);
        // we will just parse the attributes, but not do anything with them yet
        boolean isDirected = GraphAttributes.get(consume(TokenKind.IDENTIFIER).getLexeme()); // directed or undirected
        consume(TokenKind.COMMA);
        boolean isWeighted = GraphAttributes.get(consume(TokenKind.IDENTIFIER).getLexeme()); // weighted or unweighted
        consume(TokenKind.COMMA);
        TypeNode dataType = parseType(); // data type of the graph nodes/edges
        consume(TokenKind.GREATER);
        return new GraphTypeNode(dataType, isWeighted, isDirected); // placeholder, will have to build a GraphTypeNode later
    }

    // this method handles variable declarations, i will add more error checks at some stage
    // for now i just want to be able to recognize variables and declare them into the env
    // atm we can declare with or without a value
    // !! type mismatch errors happen at another stage of the interpreter
    private VariableDeclarationNode parseDeclaration() {
        System.out.println(peek());
        TypeNode type = parseType();          // mat<num>
        Token name = consume(TokenKind.IDENTIFIER);
        Expression initializer = null;  // can be anything
        if (match(TokenKind.EQUAL)) {  // allow for null init if no = provided
            if (type instanceof GraphTypeNode) {
                initializer = parseGraphLiteral();
            } else if (type instanceof MatrixTypeNode) {
                initializer = parseMatrixLiteral();
            } else if (type instanceof NodeTypeNode) {
                initializer = parseNodeLiteral();
            } else if (type instanceof ListTypeNode) {
                initializer = parseListLiteral();
            }
            else {
                initializer = parseExpression();
            }
        }
        consume(TokenKind.SEMICOLON);
        return new VariableDeclarationNode(type, name.getLexeme(), initializer);
    }

    private MatrixLiteralNode parseMatrixLiteral() {  // we land here with peek() being open_bracket
        List<List<Expression>> rows = new ArrayList<>();
        if (!check(TokenKind.CLOSE_BRACKET)) {  // check but don't consume
            do {
                consume(TokenKind.OPEN_BRACKET);
                List<Expression> row = new ArrayList<>();
                row.add(parseExpression());
                while (match(TokenKind.COMMA)) {
                    row.add(parseExpression());
                }
                consume(TokenKind.CLOSE_BRACKET);
                rows.add(row);
            } while (match(TokenKind.COMMA));
        }
        return new MatrixLiteralNode(rows);
    }

    private GraphLiteralNode parseGraphLiteral() {
        List<String> nodes = new ArrayList<>();
        List<String> edges = new ArrayList<>();
        consume(TokenKind.OPEN_BRACKET);
        parseField();
        parseField();
        return null;
    }

    private NodeLiteralNode parseNodeLiteral() {
        System.out.println("parsing node literal");
        return null;
    }

    private ListLiteralNode parseListLiteral() {
        System.out.println("parsing list literal");
        consume(TokenKind.OPEN_BRACKET);
        List<Expression> body = new ArrayList<>();
        do {
            body.add(parseExpression());
        } while (match(TokenKind.COMMA));
        consume(TokenKind.CLOSE_BRACKET);
        return new ListLiteralNode(body);
    }

    private Map.Entry<String, Expression> parseField() {  // used for stuff like graph nodes, edges, more to come later
        Token name = consume(TokenKind.IDENTIFIER);
        consume(TokenKind.COLON);
        Expression value = parseExpression();
        return Map.entry(name.getLexeme(), value);
    }

    private FunctionDeclarationNode parseFunctionDeclaration() { // we should build the logic to allow users to define a function
        advance(); // consume the FUNC token
        if (!match(TokenKind.IDENTIFIER)) { // should make sure you're not defining a function with a reserved keyword or literal
            throw new ErrorHandler(
                    "parsing",
                    peek().getLine(),
                    "Unexpected token: " + peek().getLexeme(),
                    "Expected a function name after 'fn' keyword."
            );
            //throw new RuntimeException(peek() + " Expected function name after 'func' keyword.");
        }
        Token functionName = previous(); // we get the name etc, then we have to build the FunctionSymbol somehow
        if (!match(TokenKind.OPEN_PAREN)) {
            throw new ErrorHandler(
                    "parsing",
                    peek().getLine(),
                    "Unexpected token: " + peek().getLexeme(),
                    "Expected '(' after function name."
            );
            //throw new RuntimeException(peek() + " Expected '(' after function name.");
        }
        // the parameters are optional, so we can have a function with no parameters
        // but the logic will be implemented later on, since i have no idea how to do it now
        java.util.List<VariableSymbol> parameters = parseFunctionParameters(); // this will parse the parameters of the function
        consume(TokenKind.CLOSE_PAREN); // consume the closing parenthesis
        // so, for built ins, probably we will handle them at the start of this method
        if (!match(TokenKind.ARROW)) { // if we don't have an arrow, we assume it's a built-in function
            throw new ErrorHandler(
                    "parsing",
                    peek().getLine(),
                    "Unexpected token: " + peek().getLexeme(),
                    "Expected '->' after function parameters. Declaring a return type is mandatory."
            );
            //throw new RuntimeException(peek() + " Expected '->' after function parameters.");
        } // now we should get the return type of the function
        TokenKind returnType = consume(typeKeywords).getKind();
        System.out.println("Function return type: " + returnType);
        if (!match(TokenKind.OPEN_BRACE)) {
            throw new ErrorHandler(
                    "parsing",
                    peek().getLine(),
                    "Unexpected token: " + peek().getLexeme(),
                    "Expected '{' after return type."
            );
            //throw new RuntimeException(peek() + " Expected '{' after return type.");
        }

        // somehow the body has to be parsed before return
        // TODO -> parse function body ? or merely parse on the fly during interpretation?
        // then we could have whatever we pass to the function become its own piece of code with own environment
        
        consume(TokenKind.RETURN); // consume the closing brace, we will handle the body later

        // TODO -> parse the function body somehow ...
        java.util.List<Statement> functionBody = null; // for now, we will just return null, since we don't have a body yet
        consume(TokenKind.CLOSE_BRACE); // consume the closing brace, we will handle the body later

        return new FunctionDeclarationNode( // does FunctionNode need its environment passed as well? most likely yes
                functionName.getLexeme(),
                returnType,
                parameters,
                functionBody
        );
    }

    // less than ideal, but it is fine for the demo TODO change this nonsense
    private final Map<String, Boolean> GraphAttributes = Map.of(
            "ud", false,
            "d", true,
            "u", false,
            "w", true,
            "dw", true,
            "uw", false
    );

    // still have no idea how i will even apply these things, but it will happen at some point
    // for now all i can really do is just gather the params, but i can't do anything with them quite yet
    private List<VariableSymbol> parseFunctionParameters() {
        List<VariableSymbol> parameters = new ArrayList<>();
        if (!check(TokenKind.CLOSE_PAREN)) {  // a function can be defined with no parameters
            System.out.println("Parsing function parameters...");
            do {
                Token type = consume(typeKeywords); // parameter type
                Token name = consume(TokenKind.IDENTIFIER);
                parameters.add(new VariableSymbol(name.getLexeme(), type.getKind(), null));
            } while (match(TokenKind.COMMA));
        }
        System.out.println("Function parameters parsed: " + parameters.size());
        return parameters;
    }

    // arguments are the values passed to the function, while parameters are the variables defined in the function signature
    // so these may actually be Expression
    private List<Expression> parseFunctionArguments() {
        List<Expression> arguments = new ArrayList<>();
        if (!check(TokenKind.CLOSE_PAREN)) {
            while (true) {
                Expression arg = parseExpression();
                arguments.add(arg);
                if (!match(TokenKind.COMMA))
                    break;
                if (check(TokenKind.CLOSE_PAREN))
                    throw new IllegalArgumentException("Trailing comma in argument list.");
            }
        }
        return arguments;
    }

    private Expression parseFunctionCall() {
        Token functionNameToken = consume(TokenKind.IDENTIFIER); // consume function name
        consume(TokenKind.OPEN_PAREN); // consume '('

        java.util.List<Expression> arguments = parseFunctionArguments(); // parse argument expressions

        consume(TokenKind.CLOSE_PAREN); // consume ')'

        return new FunctionCallNode(functionNameToken.getLexeme(), arguments);
    }

    private Statement parseVariableReassignment() {
        System.out.println("before parsing variable reassignment, current token: " + peek());
        String varName = peek().getLexeme();
        consume(TokenKind.IDENTIFIER); // consume the variable name
        if (!match(TokenKind.EQUAL)) {
            throw new ErrorHandler(
                    "parsing",
                    peek().getLine(),
                    "Unexpected token: " + peek().getLexeme(),
                    "Expected '=' for variable reassignment."
            );
        }
        if (match(TokenKind.EQUAL)) {
            throw new ErrorHandler(
                    "parsing",
                    peek().getLine(),
                    "Unexpected token: " + peek().getLexeme(),
                    "Cannot use == when assigning value to variable."
            );
        }
        Expression newValue = parseExpression();
        consume(TokenKind.SEMICOLON); // consume the semicolon
        return new VariableReassignmentNode(varName, newValue);
    }

//    private Expression parseGraph() {  // this has to look back to get the direction and weight info
//        System.out.println("Parsing graph declaration...");
//        boolean isDirectedGraph = false;
//        boolean isWeightedGraph = false;
//        HashMap<String, Node> nodeMap = new HashMap<>();  // use this to keep track of nodes by name
//        HashMap<String, Edge> edgeMap = new HashMap<>();  // keep track of edges by name
//        do {
//            if (match(TokenKind.NODE_TYPE)) {
//                parseNodeDeclaration(nodeMap);
//            } else if (match(TokenKind.EDGE_TYPE)) {
//                parseEdgeDeclaration(nodeMap, edgeMap);
//            } else {
//                throw new ErrorHandler(
//                        "parsing",
//                        peek().getLine(),
//                        "Unexpected token inside graph body: " + peek(),
//                        "Expected 'node' or 'edge' declaration inside graph."
//                );
//            }
//        } while (!check(TokenKind.CLOSE_BRACE) && !isAtEnd());
//        consume(TokenKind.CLOSE_BRACE);
//        System.out.println("Finished parsing graph with name. Nodes: " + nodeMap.size() + ", Edges: " + edgeMap.values().size());
//        // return new Graph(nodeMap, edgeMap, isDirectedGraph, isWeightedGraph, mapDeclarationToDatatype.get(currentDataType));
//        return new Graph(nodeMap, edgeMap, isDirectedGraph, isWeightedGraph, null);
//    }

//    private void parseEdgeDeclaration(HashMap<String, Node> nodeMap, HashMap<String, Edge> edgeMap) {
//        System.out.println("current token at parseEdgeDeclaration: " + peek());
//        String from = peek().getLexeme();  // now we can actually pass it Node types
//        Node fromNode = nodeMap.get(from);
//        consume(TokenKind.IDENTIFIER); // consume the 'from' node name
//        if (this.isDirectedGraph && !match(TokenKind.ARROW)) {
//            throw new ErrorHandler(
//                    "parsing",
//                    peek().getLine(),
//                    "Expected '->' for directed edge.",
//                    "Directed edges must use '->' to indicate direction."
//            );
//        } else if (!this.isDirectedGraph && !match(TokenKind.MINUS)) {
//            throw new ErrorHandler(
//                    "parsing",
//                    peek().getLine(),
//                    "Expected '-' for undirected edge.",
//                    "Undirected edges must use '-' to indicate no direction."
//            );
//        }
//        String to = peek().getLexeme();
//        Node toNode = nodeMap.get(to);
//        consume(TokenKind.IDENTIFIER); // consume the 'to' node name
//        System.out.println("Edge from: " + from + " to: " + to);
//        edgeMap.put(from + to, null); // just to keep track of edges by name
//        fromNode.addNeighbor(to, toNode); // add neighbor relationship
//        toNode.addNeighbor(from, fromNode); // add neighbor relationship (undirected graph)
//        // if the user does not specify a weight for the edge
//        if (match(TokenKind.SEMICOLON)) {
//            Expression weight = this.isWeightedGraph ? new Scalar(1) : null;
//            Edge edge = new Edge(fromNode, toNode, weight, this.isDirectedGraph);
//            String edgeName = from + to;
//            fromNode.addEdge(edgeName, edge);
//            if (!this.isDirectedGraph) {
//                toNode.addEdge(edgeName, edge);
//            }
//            edgeMap.put(edgeName, edge);
//        }
//        else if (peek().getKind() == TokenKind.EQUAL) {
//            if (!this.isWeightedGraph) {
//                throw new ErrorHandler(
//                        "parsing",
//                        peek().getLine(),
//                        "Unexpected weight assignment in unweighted graph for edge: " + from + (this.isDirectedGraph ? "->" : "-") + to,
//                        "Cannot assign weights to edges in an unweighted graph."
//                );
//            }
//            System.out.println("Parsing edge weight expression...");
//            consume(TokenKind.EQUAL);
//            Expression weight = parseExpression();
//            consume(TokenKind.SEMICOLON);
//            Edge edge = new Edge(fromNode, toNode, weight, this.isDirectedGraph);
//            String edgeName = from + to;
//            fromNode.addEdge(edgeName, edge);
//            if (!this.isDirectedGraph) {
//                toNode.addEdge(edgeName, edge);
//            }
//            edgeMap.put(edgeName, edge);
//        }
//        else {
//            throw new ErrorHandler(
//                    "parsing",
//                    peek().getLine(),
//                    "Unexpected token in edge declaration: " + peek(),
//                    "Expected ';' or '=' followed by weight expression."
//            );
//        }
//    }

//    private void parseNodeDeclaration(HashMap<String, Node> nodeMap) {
//        System.out.println("current token at parseNodeDeclaration: " + peek());
//        String nodeName = peek().getLexeme();
//        consume(TokenKind.IDENTIFIER);  // consume the node name
//        System.out.println("Node name: " + nodeName);
//        if (match(TokenKind.SEMICOLON)) {
//            Expression weight = this.isWeightedGraph ? new Scalar(1) : null;
//            Node node = new Node(weight, nodeName);
//            nodeMap.put(nodeName, node);
//        }
//        else if (peek().getKind() == TokenKind.EQUAL) {
//            if (!this.isWeightedGraph) {
//                throw new ErrorHandler(
//                        "parsing",
//                        peek().getLine(),
//                        "Unexpected weight assignment in unweighted graph for node: " + nodeName,
//                        "Cannot assign weights to nodes in an unweighted graph."
//                );
//            }
//            consume(TokenKind.EQUAL);
//            Expression weight = parseExpression();
//            consume(TokenKind.SEMICOLON);
//            Node node = new Node(weight, nodeName);
//            nodeMap.put(nodeName, node);
//        }
//        else {
//            throw new ErrorHandler(
//                    "parsing",
//                    peek().getLine(),
//                    "Unexpected token in node declaration: " + peek(),
//                    "Expected ';' or '=' followed by weight expression."
//            );
//        }
//    }

    private IfNode parseConditionalBranch() {
        if (!check(TokenKind.IF)) {
            throw new ErrorHandler("parsing", peek().getLine(), "Expected IF", "");
        }
        advance(); // consume IF (works for both if and else if)
        consume(TokenKind.OPEN_PAREN);
        Expression condition = parseExpression();
        consume(TokenKind.CLOSE_PAREN);
        consume(TokenKind.OPEN_BRACE);
        List<Statement> thenBranch = new ArrayList<>();
        while (!check(TokenKind.CLOSE_BRACE)) {
            Statement stmt = parseStatement();
            if (stmt != null) thenBranch.add(stmt);
        }
        consume(TokenKind.CLOSE_BRACE);
        List<Statement> elseBranch = null;
        if (match(TokenKind.ELSE)) {
            if (check(TokenKind.IF)) { // don't consume yet
                elseBranch = new ArrayList<>();
                elseBranch.add(parseConditionalBranch()); // recursive call will consume IF token itself
            } else { // else
                consume(TokenKind.OPEN_BRACE);
                elseBranch = new ArrayList<>();
                while (!check(TokenKind.CLOSE_BRACE)) {
                    Statement stmt = parseStatement();
                    if (stmt != null) elseBranch.add(stmt);
                }
                consume(TokenKind.CLOSE_BRACE);
            }
        }
        return new IfNode(condition, thenBranch, elseBranch);
    }

    private WhileNode parseWhileLoop() {
        consume(TokenKind.WHILE);
        consume(TokenKind.OPEN_PAREN);
        Expression condition = parseExpression();
        consume(TokenKind.CLOSE_PAREN);
        consume(TokenKind.OPEN_BRACE);
        // environment.pushScope();
        List<Statement> body = new ArrayList<>();
        while (!check(TokenKind.CLOSE_BRACE)) {
            Statement statement = parseStatement();
            if (statement != null) body.add(statement);
        }
        // environment.popScope();
        consume(TokenKind.CLOSE_BRACE);
        return new WhileNode(condition, body);
    }

    private boolean match(TokenKind... expectedKinds) {
        for (TokenKind expectedKind : expectedKinds) {
            if (check(expectedKind)) {
                advance();
                return true;
            }
        }
        return false;
    }

    private boolean check(TokenKind expectedKind) {
        if (!isAtEnd()) {
            return peek().getKind() == expectedKind;
        }
        return false;
    }

    private boolean check(TokenKind... expectedKinds) {
        for (TokenKind expectedKind : expectedKinds) {
            if (check(expectedKind)) {
                return true;
            }
        }
        return false;
    }

    private boolean check(Set<TokenKind> expectedKinds) {
        for (TokenKind expectedKind : expectedKinds) {
            if (check(expectedKind)) {
                return true;
            }
        }
        return false;
    }

    private Token consume(TokenKind... kinds) {
        for (TokenKind expectedKind : kinds) {
            if (check(expectedKind)) {
                return advance();
            }
        }
        throw new Error("No match for " + peek().getKind() + " at line " + peek().getLine()
                + ". Expected one of: " + Arrays.toString(kinds));
    }

    private Token consume(Set<TokenKind> tokenKinds) {
        for (TokenKind expectedKind : tokenKinds) {
            if (check(expectedKind)) {
                return advance();
            }
        }
        throw new Error("No match for " + peek().getKind() + " at line " + peek().getLine()
                + ". Expected one of: " + tokenKinds);
    }

    private Token advance() {
        if (!isAtEnd()) {
            tokenPos++;
        }
        return previous();
    }

    private Token peek() {
        return tokens.get(tokenPos);
    }

    private Token lookBack(int n) {
        if (tokenPos - n >= 0) {
            return tokens.get(tokenPos - n);
        }
        return tokens.getFirst(); // return first token if out of bounds
    }

    private Token previous() {
        return tokens.get(tokenPos - 1);
    }

    private Token lookAhead(int n) {
        if (tokenPos + n < tokens.size()) {
            return tokens.get(tokenPos + n);
        }
        return tokens.getLast(); // return EOF token if out of bounds
    }

    private boolean isAtEnd() {
        return peek().getKind() == TokenKind.EOF;
    }

    private boolean isDeclarationStart() {
        return check(typeKeywords);
    }

    // will check if a user-defined function IS BEING DECLARED. calls will happen later
    private boolean isFunctionDeclarationStart() {
        return check(TokenKind.FUNC);
    }

    private boolean isFunctionCall() {
        if (!check(TokenKind.IDENTIFIER)) { // if you don't check this, even a variable will be treated as a function call
            return false; // if the next token is not an identifier, it can't be a function call
        }
        advance();
        return peek().getKind() == TokenKind.OPEN_PAREN;
    }

    private boolean isModuleImport() {
        return check(TokenKind.INCLUDE); // we can add more keywords for imports later
    }

    private boolean isConditionalBranch() {
        return check(TokenKind.IF);
    }

    // in future add support for all types
    private static final Set<TokenKind> typeKeywords = Set.of(
            TokenKind.SCALAR_TYPE,
            TokenKind.BOOLEAN_TYPE,
            TokenKind.MATRIX_TYPE,
            TokenKind.MATH_TYPE,
            TokenKind.STRING_TYPE,
            TokenKind.VOID_TYPE,
            TokenKind.GRAPH_TYPE,
            TokenKind.NODE_TYPE,
            TokenKind.EDGE_TYPE,
            TokenKind.LIST_TYPE,
            TokenKind.VECTOR_TYPE
    );

//    private static final Map<TokenKind, TokenKind> mapDeclarationToDatatype = Map.ofEntries(
//            Map.entry(TokenKind.SCALAR_TYPE, TokenKind.SCALAR),
//            // Map.entry(TokenKind.FLOAT_TYPE, TokenKind.FLOAT),
//            Map.entry(TokenKind.BOOLEAN_TYPE, TokenKind.BOOLEAN),
//            Map.entry(TokenKind.MATRIX_TYPE, TokenKind.MATRIX),
//            Map.entry(TokenKind.MATH_TYPE, TokenKind.MATH),
//            Map.entry(TokenKind.STRING_TYPE, TokenKind.STRING),
//            Map.entry(TokenKind.VOID_TYPE, TokenKind.VOID),
//            Map.entry(TokenKind.ARRAY_TYPE, TokenKind.ARRAY),
//            Map.entry(TokenKind.GRAPH_TYPE, TokenKind.GRAPH),
//            Map.entry(TokenKind.NODE_TYPE, TokenKind.NODE),
//            Map.entry(TokenKind.EDGE_TYPE, TokenKind.EDGE)
//    );

//    private static final Set<TokenKind> LinearAlgebraOperators = Set.of(
//            TokenKind.MATRIX
//    );

    // probably we need some more operators here later on
    // also for the linear algebra either we handle it through Add etc., or we make new nodes
//    private BinaryNode inferBinaryNodeFromOperator(TokenKind operator, Expression lhs, Expression rhs) {
//        System.out.println("inferBinaryNodeFromOperator called with operator: " + operator);
//        System.out.println("lhs type: " + lhs.getType(environment) + ", rhs type: " + rhs.getType(environment));
//        switch (operator) {
//            case PLUS -> {
//                return new Add(lhs, rhs);
//            }
//            case MINUS -> {
//                return new Sub(lhs, rhs);
//            }
//            case MUL -> {
//                return new Mul(lhs, rhs);
//            }
//            case DIV -> {
//                return new Div(lhs, rhs);
//            }
//            case MOD -> {
//                return new Mod(lhs, rhs);
//            }
//        }
//        throw new ErrorHandler(
//                "parsing",
//                peek().getLine(),
//                "Unsupported operator: " + operator,
//                "Expected a valid arithmetic operator (+, -, *, /)."
//        );
//    }
}
