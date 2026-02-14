package Parser;

import AST.*;
import AST.BinaryNode;
import AST.FunctionCallNode;
import AST.ImportNode;
import AST.LambdaFunctionNode;
import AST.MapFunctionNode;
import AST.Metadata.Containers.*;
import AST.Metadata.Functions.*;
import AST.BraceLiteralNode;
import AST.BracketLiteralNode;
import AST.RecordLiteralNode;
import AST.GraphNodeLiteralNode;
import AST.MatrixLiteralNode;
import AST.IfNode;
import Util.ErrorHandler;
import Lexer.TokenKind;
import Lexer.Token;

import java.util.*;

// IMPORTANT NOTE
// the parser is ONLY responsible for parsing the code and building the AST
// it does NOT check for semantic errors or type mismatches
// that is the job of the interpreter or a separate semantic analyzer
// the parser will throw runtime exceptions if it encounters unexpected tokens
// TODO the parser ONLY cares about SYNTAX AND GRAMMAR
// -> so "int x = 5.7;" is perfectly fine for the parser, but not for the interpreter
public class Parser {
    private final List<Token> tokens;
    private int tokenPos = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    // AST is just an array of statements
    public List<Statement> parseProgram() {  // this method builds the AST, and must therefore return it
        List<Statement> programBody = new ArrayList<>();
        while (!isAtEnd()) {
            Statement statement = parseStatement();
            if (statement != null) {
                programBody.add(statement);
            } else {
                advance();
            }
        }
        // warningHandler.printWarnings();
        return programBody;
    }

    private Statement parseStatement() {
        if (isAtEnd()) {
            return null;
        }
        if (check(TokenKind.RETURN)) {
            return parseReturnStatement();
        }
        if (check(TokenKind.CLAIM)) {
            return parseClaimStatement();
        }
        if (isDeclarationStart()) {
            return parseDeclaration();
        }
        if (isFunctionDeclarationStart()) {
            return parseFunctionDeclaration();
        }
        if (check(TokenKind.IF)) {
            return parseConditionalBranch();
        }
        if (check(TokenKind.INCLUDE)) {
            advance(); // consume INCLUDE
            Token moduleName = consume(TokenKind.IDENTIFIER);
            String alias = null;
            if (match(TokenKind.AS)) {  // parsing with alias
                alias = consume(TokenKind.IDENTIFIER).getLexeme();
            }
            consume(TokenKind.SEMICOLON);
            return new ImportNode(moduleName.getLexeme(), null);
        }
        if (peek().getKind() == TokenKind.IDENTIFIER && lookAhead(1).getKind() == TokenKind.EQUAL) {
            return parseVariableReassignment();
        }
        if (peek().getKind() == TokenKind.WHILE) {
            return parseWhileLoop();
        }
        else {
            Expression expr = parseExpression(); // will handle identifiers, function calls, parenthesis, etc.
            consume(TokenKind.SEMICOLON);
            return new ExpressionStatementNode(expr);
        }
    }

    private ClaimStatementNode parseClaimStatement() {
        advance();  // consume CLAIM
        Expression expr = parseExpression();
        consume(TokenKind.SEMICOLON);
        return new ClaimStatementNode(expr);
    }

    private ReturnStatementNode parseReturnStatement() {
        consume(TokenKind.RETURN);
        Expression value = null;
        if (!check(TokenKind.SEMICOLON)) {
            value = parseExpression();  // return; is allowed
        }
        consume(TokenKind.SEMICOLON);
        return new ReturnStatementNode(value);
    }

    private Expression parseExpression() {
        return parseAssignment();
    }

    private Expression parseAssignment() {
        Expression expression = parseEquality();
        if (match(TokenKind.EQUAL)) {
            Token equals = previous();
            Expression value = parseAssignment();
            if (expression instanceof VariableNode variable) {
                return new AssignmentNode(variable.getName(), value);
            }
            throw new RuntimeException("Invalid assignment target.");
        }
        return expression;
    }

    private Expression parseEquality() {
        Expression expression = parseComparison();
        while (match(TokenKind.EQUAL_EQUAL, TokenKind.NOT_EQUAL)) {
            Token operator = previous();
            Expression rhs = parseComparison();
            expression = new BinaryNode(expression, tokenToOp(operator.getKind()), rhs);
        }
        return expression;
    }

    private Expression parseComparison() {
        Expression expression = parseTerm();
        while (match(TokenKind.GREATER, TokenKind.LESS, TokenKind.GREATER_EQUAL, TokenKind.LESS_EQUAL)) {
            Token operator = previous(); // because match() consumes the token (advances position)
            Expression rhs = parseTerm(); // here we are also consuming the next token, which ensures the while loop actually works
            expression = new BinaryNode(expression, tokenToOp(operator.getKind()), rhs);
        }
        return expression;
    }

    private Expression parseTerm() {
        Expression expression = parseFactor();
        while (match(TokenKind.PLUS, TokenKind.MINUS)) {
            Token operator = previous();
            Expression rhs = parseFactor();
            expression = new BinaryNode(expression, tokenToOp(operator.getKind()), rhs);
        }
        return expression;
    }

    private Expression parseFactor() {
        Expression expression = parseUnary();
        while (match(TokenKind.MUL, TokenKind.DIV)) {
            Token operator = previous();
            Expression rhs = parseUnary();
            expression = new BinaryNode(expression, tokenToOp(operator.getKind()), rhs);
        }
        return expression;
    }

    private Expression parseUnary() {
        if (match(TokenKind.NOT, TokenKind.MINUS, TokenKind.PLUS)) { // handle both ! and negation
            Token operator = previous();
            Expression rhs = parseUnary();
            return new UnaryNode(operator, rhs);
        }
        return parsePrimary();
    }

    private Expression parsePrimary() {
        Expression expr;
        if (match(TokenKind.INTEGER)) {
            expr = new IntegerLiteralNode((Integer) previous().getLiteral());
        } else if (match(TokenKind.FLOAT)) {
            expr = new FloatLiteralNode((Double) previous().getLiteral());
        } else if (match(TokenKind.STRING)) {
            expr = new StringLiteralNode((String) previous().getLiteral());
        } else if (match(TokenKind.BOOLEAN)) {
            expr = new BooleanLiteralNode((Boolean) previous().getLiteral());
        } else if (match(TokenKind.IDENTIFIER)) {
            expr = new VariableNode(previous().getLexeme());
        } else if (match(TokenKind.OPEN_PAREN)) {
            expr = new GroupingNode(parseExpression());
            consume(TokenKind.CLOSE_PAREN);
        } else if (match(TokenKind.OPEN_BRACKET)) {
            expr = parseBracketLiteral();
        } else {
            throw new ErrorHandler(
                    "parsing",
                    peek().getLine(),
                    "Unexpected token: " + peek().getLexeme(),
                    "Expected a primary expression."
            );
        }
        while (true) {
            if (match(TokenKind.OPEN_PAREN)) {
                expr = parseFunctionCall(expr);
            } else if (match(TokenKind.OPEN_BRACKET)) {
                Expression index = parseExpression();
                consume(TokenKind.CLOSE_BRACKET);
                expr = new ListAccessNode(expr, index);  // supports nested list access like a[0][1]
            } else if (match(TokenKind.DOT) && peek().getKind() == TokenKind.MAP) {
                expr = parseMapFunction();
            } else if (match(TokenKind.DOT)) {
                String memberName = consume(TokenKind.IDENTIFIER).getLexeme();
                expr = new MemberAccessNode(expr, memberName);
            } else {
                break;
            }
        }
        return expr;
    }

    private Expression parseMapFunction() {
        consume(TokenKind.MAP);
        consume(TokenKind.OPEN_PAREN);
        consume(TokenKind.IDENTIFIER);
        consume(TokenKind.ARROW);
        Expression body = parseExpression();
        consume(TokenKind.CLOSE_PAREN);
        return new MapFunctionNode(body);
    }

    private Expression parseLambdaFunction() {
        consume(TokenKind.MAP);
        consume(TokenKind.OPEN_PAREN);
        List<ParamNode> parameters = new ArrayList<>();
        if (!check(TokenKind.CLOSE_PAREN)) {
            do {
                String paramName = consume(TokenKind.IDENTIFIER).getLexeme();
                consume(TokenKind.COLON);
                Type paramType = parseType();
                parameters.add(new ParamNode(paramName, paramType));
            } while (match(TokenKind.COMMA));
        }
        consume(TokenKind.CLOSE_PAREN);
        consume(TokenKind.ARROW);
        Expression body = parseExpression();
        return new LambdaFunctionNode(parameters, body);
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

    private BraceLiteralNode parseBlock() {  // differs from parseField
        List<Statement> statements = new ArrayList<>();
        while (!check(TokenKind.CLOSE_BRACE) && !isAtEnd()) {
            statements.add(parseStatement());
        }
        consume(TokenKind.CLOSE_BRACE);
        return new BraceLiteralNode(statements);
    }

    private Type parseType() {
        TokenKind type = peek().getKind();
        return switch (type) {
            case INTEGER_TYPE, FLOAT_TYPE -> {
                advance();
                yield new ScalarTypeNode();
            }
            case MATRIX_TYPE -> {
                advance();
                yield parseMatrixType();
            }
            case GRAPH_TYPE -> {
                advance();
                yield parseGraphType();
            }
            case NODE_TYPE -> {
                advance();
                yield new NodeTypeNode();
            }
            case LIST_TYPE -> {
                advance();
                yield parseListType();
            }
            case BOOLEAN_TYPE -> {
                advance();
                yield new BooleanTypeNode();
            }
            case MATH_TYPE -> {
                advance();
                yield new MathTypeNode();
            }
//            case FUNC_TYPE -> {  // TODO return functions
//                advance();
//                yield parseFunctionType();
//            }
            default -> throw new RuntimeException("Expected a type, got " + type);
        };
    }

    private Type parseListType() {
        consume(TokenKind.LESS);
        Type innerType = parseType();
        consume(TokenKind.GREATER);
        return new ListTypeNode(innerType);
    }

    // using Dimension to prevent user from calling functions etc. as dimensions, gets hard to solve
    private Type parseMatrixType() {  // have to advance tokens etc
        Dimension A = null;
        Dimension B = null;
        consume(TokenKind.LESS);
        Type innerType = parseType();
        if (match(TokenKind.COMMA)) {  // user specifies a dimension already
            MatrixShape dimensions = parseDimension();
            A = dimensions.rows();
            B = dimensions.cols();
        }
        consume(TokenKind.GREATER);
        return new MatrixTypeNode(innerType, A, B);
    }

    private MatrixShape parseDimension() {  // this can be used for symbols too maybe? if we extend it to accept functions etc
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
            left = new BinaryDimension(left, right, tokenToOp(op.getKind()));
        }
        return left;
    }

    private Dimension parseDimMulDiv() {
        Dimension left = parseDimAtom();
        while (match(TokenKind.MUL, TokenKind.DIV)) {
            Token op = previous();
            Dimension right = parseDimAtom();
            left = new BinaryDimension(left, right, tokenToOp(op.getKind()));
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

    // will fix this to use an Enum to be safer
    private Type parseGraphType() {
        consume(TokenKind.LESS);
        boolean isDirected = GraphAttributes.get(consume(TokenKind.IDENTIFIER).getLexeme()); // directed or undirected
        consume(TokenKind.COMMA);
        boolean isWeighted = GraphAttributes.get(consume(TokenKind.IDENTIFIER).getLexeme()); // weighted or unweighted
        if (peek().getKind() == TokenKind.GREATER) {
            consume(TokenKind.GREATER);
            return new GraphTypeNode(null, isWeighted, isDirected);
        }
        consume(TokenKind.COMMA);
        Type dataType = parseType(); // data type of the graph nodes/edges
        consume(TokenKind.GREATER);
        return new GraphTypeNode(dataType, isWeighted, isDirected);
    }

    // this method handles variable declarations, i will add more error checks at some stage
    // for now i just want to be able to recognize variables and declare them into the env
    // atm we can declare with or without a value
    // !! type mismatch errors happen at another stage of the interpreter
    private VariableDeclarationNode parseDeclaration() {
        boolean isMutable = match(TokenKind.MUTABLE);  // optional mutable keyword, more to come
        Type type = parseType();  // ex, mat<num>
        Token identifier = consume(TokenKind.IDENTIFIER);
        Expression initializer = null;  // can be anything
        if (match(TokenKind.EQUAL)) {  // allow for null init if no = provided
            initializer = switch (type) {
                case GraphTypeNode _ -> parseRecordLiteral();
                case MatrixTypeNode _ -> parseMatrixLiteral();
                case NodeTypeNode _ -> parseNodeLiteral();
                case ListTypeNode _ -> parseListLiteral();
                case null, default -> parseExpression();
            };
        }
        consume(TokenKind.SEMICOLON);
        return new VariableDeclarationNode(type, identifier.getLexeme(), initializer, isMutable, identifier.getLine());
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

    private GraphNodeLiteralNode parseNodeLiteral() {
        return null;
    }

    private RecordLiteralNode parseRecordLiteral() {
        consume(TokenKind.OPEN_BRACE);
        Map<String, Expression> content = new LinkedHashMap<>();
        if (!check(TokenKind.CLOSE_BRACE)) {
            do {
                String key = consume(TokenKind.IDENTIFIER).getLexeme();
                consume(TokenKind.COLON);
                Expression value = parseExpression();
                if (content.containsKey(key)) {
                    throw new RuntimeException(
                            "Duplicate key '" + key + "' in record literal"
                    );
                }
                content.put(key, value);
            } while (match(TokenKind.COMMA));
        }
        consume(TokenKind.CLOSE_BRACE);
        return new RecordLiteralNode(content);
    }


    private ListLiteralNode parseListLiteral() {
        consume(TokenKind.OPEN_BRACKET);
        List<Expression> body = new ArrayList<>();
        do {
            body.add(parseExpression());
        } while (match(TokenKind.COMMA));
        consume(TokenKind.CLOSE_BRACKET);
        return new ListLiteralNode(body);
    }

    private HashMap<String, Expression> parseField() {  // used for stuff like graph nodes, edges, more to come later
        HashMap<String, Expression> map = new HashMap<>();
        String name = consume(TokenKind.IDENTIFIER).getLexeme();
        consume(TokenKind.COLON);
        Expression value = parseExpression();
        consume(TokenKind.SEMICOLON);
        map.put(name, value) ;
        return map;
    }

    private FunctionDeclarationNode parseFunctionDeclaration() { // we should build the logic to allow users to define a function
        advance();
        Token identifier = consume(TokenKind.IDENTIFIER);
        consume(TokenKind.OPEN_PAREN);
        List<ParamNode> paramList = new ArrayList<>();
        do {
            paramList.add(parseFunctionParam());
        } while (match(TokenKind.COMMA));
        consume(TokenKind.CLOSE_PAREN);
        consume(TokenKind.ARROW);
        Type returnType = parseType();
        consume(TokenKind.OPEN_BRACE);
        BraceLiteralNode functionBody = parseBlock();
        return new FunctionDeclarationNode(identifier.getLexeme(), paramList, returnType, functionBody, identifier.getLine());
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
    private ParamNode parseFunctionParam() {
        String paramName = consume(TokenKind.IDENTIFIER).getLexeme();
        consume(TokenKind.COLON);
        Type paramType = parseType();
        return new ParamNode(paramName, paramType);
    }

    private Expression parseFunctionCall(Expression callee) {
        List<Expression> arguments = new ArrayList<>();
        if (!check(TokenKind.CLOSE_PAREN)) {
            do {
                arguments.add(parseExpression());
            } while (match(TokenKind.COMMA));
        }
        consume(TokenKind.CLOSE_PAREN);
        return new FunctionCallNode(callee, arguments);
    }


    private Statement parseVariableReassignment() {
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
        return check(typeKeywords) || check(modifierKeywords);
    }

    // will check if a user-defined function IS BEING DECLARED. calls will happen later
    private boolean isFunctionDeclarationStart() {
        return check(TokenKind.FUNC_TYPE);
    }

    private boolean isFunctionCall() {
        if (!check(TokenKind.IDENTIFIER)) { // if you don't check this, even a variable will be treated as a function call
            return false; // if the next token is not an identifier, it can't be a function call
        }
        advance();
        return peek().getKind() == TokenKind.OPEN_PAREN;
    }

    // in future add support for all types
    private static final Set<TokenKind> typeKeywords = Set.of(
            TokenKind.INTEGER_TYPE,
            TokenKind.FLOAT_TYPE,
            TokenKind.BOOLEAN_TYPE,
            TokenKind.MATRIX_TYPE,
            TokenKind.MATH_TYPE,
            TokenKind.STRING_TYPE,
            TokenKind.VOID_TYPE,
            TokenKind.GRAPH_TYPE,
            TokenKind.NODE_TYPE,
            TokenKind.EDGE_TYPE,
            TokenKind.LIST_TYPE,
            TokenKind.VECTOR_TYPE,
            TokenKind.SYMBOL_TYPE
    );

    private static final Set<TokenKind> modifierKeywords = Set.of(
            TokenKind.MUTABLE
    );

    private Operators tokenToOp(TokenKind kind) {
        return switch (kind) {
            case PLUS -> Operators.ADD;
            case MINUS -> Operators.SUB;
            case MUL -> Operators.MUL;
            case DIV -> Operators.DIV;
            case MOD -> Operators.MOD;
            case EQUAL_EQUAL -> Operators.EQ;
            case NOT_EQUAL -> Operators.NEQ;
            case GREATER -> Operators.GT;
            case LESS -> Operators.LT;
            case GREATER_EQUAL -> Operators.GTE;
            case LESS_EQUAL -> Operators.LTE;
            case AND -> Operators.AND;
            case OR -> Operators.OR;
            default -> throw new Error("Unrecognized operator " + kind);
        };
    }
}
