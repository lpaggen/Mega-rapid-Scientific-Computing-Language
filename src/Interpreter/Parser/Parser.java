package Interpreter.Parser;

import AST.Nodes.*;
import AST.Nodes.BinaryOperations.Linalg.LinalgAdd;
import AST.Nodes.BinaryOperations.Linalg.LinalgDiv;
import AST.Nodes.BinaryOperations.Linalg.LinalgMul;
import AST.Nodes.BinaryOperations.Linalg.LinalgSub;
import AST.Nodes.BinaryOperations.Scalar.Add;
import AST.Nodes.BinaryOperations.BinaryNode;
import AST.Nodes.BinaryOperations.Scalar.Div;
import AST.Nodes.BinaryOperations.Scalar.Mul;
import AST.Nodes.BinaryOperations.Scalar.Sub;
import AST.Nodes.DataStructures.Matrix;
import AST.Nodes.DataTypes.FloatConstant;
import AST.Nodes.DataTypes.IntegerConstant;
import AST.Nodes.Functions.FunctionCallNode;
import AST.Nodes.Functions.FunctionDeclarationNode;
import AST.Nodes.Functions.BuiltIns.ImportNode;
import AST.Nodes.Conditional.*;
import Util.ErrorHandler;
import Interpreter.Tokenizer.TokenKind;
import Interpreter.Tokenizer.Token;
import Interpreter.Runtime.Environment;
import Util.WarningLogger;

import java.util.*;

// IMPORTANT NOTE
// the parser is ONLY responsible for parsing the code and building the AST
// it does NOT check for semantic errors or type mismatches
// that is the job of the interpreter or a separate semantic analyzer
// the parser will throw runtime exceptions if it encounters unexpected tokens
// the parser ONLY cares about SYNTAX AND GRAMMAR
// -> so "int x = 5.7;" is perfectly fine for the parser, but not for the interpreter
public class Parser {
    private final java.util.List<Token> tokens;
    private int tokenPos = 0;
    public Environment environment = new Environment(); // remember this initializes a global scope BY DEFAULT
    private TokenKind currentFunctionReturnType = null; // to keep track of the current function's return type during parsing
    private TokenKind currentLoopType = null; // to keep track of the current loop type (for break/continue statements)
    private TokenKind currentDataType = null; // to keep track of the current data type being processed

    public Parser(java.util.List<Token> tokens) {
        this.tokens = tokens;
    }

    public void interpretCode() {
        WarningLogger warningHandler = new WarningLogger();
        while (!isAtEnd()) {
            Statement statement = parseStatement();
            if (statement == null) {
                // if we reach here, it means we have an empty statement or an unrecognized token
                advance(); // just consume the token and continue -- will have to fix this in a future build
                continue;
            }
            statement.execute(environment);
        }
        warningHandler.printWarnings();
    }

    // everything should really start from the Statement, since we have to declare variables, functions, etc.
    private Statement parseStatement() {
        System.out.println("current token at parseStatement: " + peek());
        if (isDeclarationStart()) {
            System.out.println("Parsing variable declaration...");
            return parseDeclaration();
        } else if (isFunctionCall()) { // this is where we can return the ExpressionStatementNode wrapper
            System.out.println("Parsing function call...");
            Expression functionCall = parseFunctionCall();
            return new ExpressionStatementNode(functionCall);
        } else if (isFunctionDeclarationStart()) {
            System.out.println("Parsing function declaration...");
            return parseFunctionDeclaration();
        } else if (Objects.requireNonNull(peek().getKind()) == TokenKind.INCLUDE) {
            System.out.println("Parsing module import statement: " + peek());
            advance(); // consume the INCLUDE token
            if (!check(TokenKind.IDENTIFIER)) {
                throw new ErrorHandler(
                        "parsing",
                        peek().getLine(),
                        "Unexpected token: " + peek().getLexeme(),
                        "Expected a module name after 'include' keyword."
                );
            }
            Token moduleName = consume(TokenKind.IDENTIFIER); // consume the module name
            consume(TokenKind.SEMICOLON); // consume the semicolon
            return new ImportNode(moduleName.getLexeme(), null); // no alias for now
        } else if (peek().getKind() == TokenKind.IDENTIFIER) { // handle both i++ and i--
            Expression expr = parseExpression();
            return new ExpressionStatementNode(expr);
        }
        return null;
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
            expression = new ComparisonNode(expression, TokenKind.EQUAL, rhs);
        }
        return expression;
    }

    private Expression parseComparison() {
        Expression expression = parseTerm();
        while (match(TokenKind.GREATER, TokenKind.LESS, TokenKind.GREATER_EQUAL, TokenKind.LESS_EQUAL)) {
            Token operator = previous(); // because match() consumes the token (advances position)
            Expression rhs = parseTerm(); // here we are also consuming the next token, which ensures the while loop actually works
            expression = new ComparisonNode(expression, operator.getKind(), rhs);
        }
        return expression;
    }

    private Expression parseTerm() {
        Expression expression = parseFactor();
        while (match(TokenKind.PLUS, TokenKind.MINUS)) {
            System.out.println("current token at parseTerm: " + peek());
            Token operator = previous();
            Expression rhs = parseFactor();
            expression = inferBinaryNodeFromOperator(operator.getKind(), expression, rhs);
        }
        return expression;
    }

    private Expression parseFactor() {
        Expression expression = parseUnary();
        while (match(TokenKind.MUL, TokenKind.DIV)) {
            System.out.println("current token at parseFactor: " + peek());
            Token operator = previous();
            Expression rhs = parseUnary();
//            if (LinearAlgebraOperators.contains(expression.getType(environment)) || LinearAlgebraOperators.contains(rhs.getType(environment))) {
//                expression = new LinalgMul(expression, rhs);
//                continue;
//            }
            expression = inferBinaryNodeFromOperator(operator.getKind(), expression, rhs);
        }
        return expression;
    }

    private Expression parseUnary() {
        if (match(TokenKind.NOT, TokenKind.MINUS)) { // handle both ! and negation
            // with match() we skip the token and advance the position
            Token operator = previous();
            Expression rhs = parseUnary();
            System.out.println("class of rhs: " + rhs.getClass());
            System.out.println("Parsing unary operator: " + operator.getLexeme() + " with rhs: " + rhs);
            return new UnaryNode(operator, rhs);
        }
        System.out.println("current token at parseUnary: " + peek());
        return parsePrimary();
    }

    private Expression parsePrimary() {
        System.out.println("current token at parsePrimary: " + peek());
        if (match(TokenKind.FALSE)) return new BooleanNode(false); // this will return a PrimaryNode with a BooleanNode inside
        if (match(TokenKind.TRUE)) return new BooleanNode(true); // this will return a PrimaryNode with a BooleanNode inside
        if (match(TokenKind.NULL)) return new PrimaryNode(null);
        if (match(TokenKind.STRING)) {
            System.out.println("Parsing string literal: " + previous().getLiteral());
            return new StringNode(previous().getLexeme()); // this will return a Constant node with the literal value
        }
        if (match(TokenKind.INTEGER)) {
            System.out.println("Parsing integer literal: " + previous().getLiteral());
            boolean isRawType = match(TokenKind.RAW); // check if the next token is a RAW type indicator
            return new IntegerConstant(Integer.parseInt(previous().getLexeme()), isRawType); // this will return a Constant node with the numeric value
        }
        if (match(TokenKind.FLOAT)) {
            System.out.println("Parsing float literal: " + Float.parseFloat(previous().getLexeme()));
            boolean isRawType = match(TokenKind.RAW); // check if the next token is a RAW type indicator
            return new FloatConstant(Float.parseFloat(previous().getLexeme()), isRawType); // this will return a Constant node with the numeric value
        }
        if (match(TokenKind.OPEN_PAREN)) {
            Expression expr = parseExpression();
            consume(TokenKind.CLOSE_PAREN);
            return new GroupingNode(expr);
        }
        if (match(TokenKind.OPEN_BRACKET)) {
            return parseMatrix();
        }
        // this is really not good and not safe, and it's a dumb check. but it works until i figure something better out
        // debug to see if we are parsing identifier i
        if (match(TokenKind.IDENTIFIER)) {
            Token name = previous();
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
            return new VariableNode(name.getLexeme()); // simple variable
        }
        throw new ErrorHandler(
                "parsing",
                peek().getLine(),
                "ParsePrimary Unexpected token: " + peek().getLexeme(),
                "Expected an expression, variable, or literal value."
        );
    }

    // need support for ndVector ? or separate parser for each
    // really this should work fine, because we can get nested arrays
//    private Expression parseVector() {
//        ArrayList<Expression> elements = new ArrayList<>();
//        if (!check(TokenKind.CLOSE_BRACKET)) {  // if it's empty array
//            do {
//                Expression element = parseExpression();
//                elements.add(element);
//            } while (match(TokenKind.COMMA)); // comma separate elements of the 1D array
//        }
//        consume(TokenKind.CLOSE_BRACKET); // consume the closing ]
//        Expression[] arrayElementsArray = elements.toArray(new Expression[0]);
//        return new Vector(arrayElementsArray, currentDataType);
//    }

    // somehow we should still allow for vector notation, matrix is otherwise very annoying
    // parser is also going to allow for vector notation, no need for double brackets everytime
    // TODO will resolve this in a later build
    private Expression parseMatrix() {
        List<List<Expression>> rows = new ArrayList<>();
        if (!check(TokenKind.CLOSE_BRACKET)) {  // if it's empty matrix
            do {
                List<Expression> row = new ArrayList<>();
                if (match(TokenKind.OPEN_BRACKET) && !check(TokenKind.CLOSE_BRACKET)) { // each row starts with an opening bracket
                    do {
                        Expression element = parseExpression();
                        row.add(element);
                    } while (match(TokenKind.COMMA)); // comma separate elements of the row
                    consume(TokenKind.CLOSE_BRACKET); // consume the closing bracket of the row
                    rows.add(row);
                } else {
                    throw new ErrorHandler(
                            "parsing",
                            peek().getLine(),
                            "Unexpected token: " + peek().getLexeme(),
                            "Expected '[' to start a new row in the matrix."
                    );
                }
            } while (match(TokenKind.COMMA)); // comma separate rows of the matrix
        }
        consume(TokenKind.CLOSE_BRACKET); // consume the closing ] of the matrix
        // convert List<List<Expression>> to Expression[][]
        int numRows = rows.size();
        int numCols = numRows > 0 ? rows.get(0).size() : 0;
        Expression[][] matrixElements = new Expression[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            List<Expression> row = rows.get(i);
            if (row.size() != numCols) {
                throw new ErrorHandler(
                        "parsing",
                        peek().getLine(),
                        "Inconsistent row sizes in matrix.",
                        "All rows in a matrix must have the same number of elements."
                );
            }
            for (int j = 0; j < numCols; j++) {
                matrixElements[i][j] = row.get(j);
            }
        }
        return new Matrix(matrixElements, currentDataType);
    }

    // this method handles variable declarations, i will add more error checks at some stage
    // for now i just want to be able to recognize variables and declare them into the env
    // atm we can declare with or without a value
    // !! type mismatch errors happen at another stage of the interpreter
    private VariableDeclarationNode parseDeclaration() {

        // this surely can't be optimal, but it will work until i figure something better out
        // the idea is to just fetch the type from the declaration
        Map<TokenKind, TokenKind> mapDeclarationToDatatype = Map.of(
                TokenKind.INTEGER_TYPE, TokenKind.INTEGER,
                TokenKind.FLOAT_TYPE, TokenKind.FLOAT,
                TokenKind.BOOLEAN_TYPE, TokenKind.BOOLEAN,
                TokenKind.MATRIX_TYPE, TokenKind.MATRIX,
                TokenKind.SYMBOL_TYPE, TokenKind.SYMBOL,
                TokenKind.STRING_TYPE, TokenKind.STRING,
                TokenKind.VOID_TYPE, TokenKind.VOID,
                TokenKind.ARRAY_TYPE, TokenKind.ARRAY
        );
        if (!typeKeywords.contains(peek().getKind())) {
            throw new ErrorHandler(
                    "parsing",
                    peek().getLine(),
                    "Unexpected token: " + peek().getLexeme(),
                    "Expected a type keyword (int, float, bool, matrix, symbol)."
            );
            //throw new RuntimeException(peek() + " Expected type keyword.");
        }
        System.out.println("Parsing variable declaration: " + peek().getLexeme());
        System.out.println("Current token at parseDeclaration: " + peek());
        Token typeToken = advance(); // consume the type token
        System.out.println("Type token: " + typeToken.getLexeme());
        if (typeToken.getKind() == TokenKind.MATRIX_TYPE) { // this will be something else for all types requiring this step
            if (!match(TokenKind.LESS)) {
                throw new ErrorHandler(
                        "parsing",
                        peek().getLine(),
                        "Unexpected token: " + peek().getLexeme(),
                        "Expected '<' after 'Matrix' type declaration."
                );
                //throw new RuntimeException(peek() + " Expected '<' after 'vector' type declaration.");
            }
            if (!typeKeywords.contains(peek().getKind())) {
                throw new ErrorHandler(
                        "parsing",
                        peek().getLine(),
                        "Unexpected token: " + peek().getLexeme(),
                        "Expected a type keyword (int, float, bool, matrix, symbol) inside matrix declaration."
                );
                //throw new RuntimeException(peek() + " Expected type keyword inside vector declaration.");
            }
            Token innerTypeToken = advance(); // consume the inner type token
            System.out.println("Inner type token for matrix: " + innerTypeToken.getLexeme());
            if (!match(TokenKind.GREATER)) {
                throw new ErrorHandler(
                        "parsing",
                        peek().getLine(),
                        "Unexpected token: " + peek().getLexeme(),
                        "Expected '>' after inner type in matrix declaration."
                );
                //throw new RuntimeException(peek() + " Expected '>' after inner type in vector declaration.");
            }
            currentDataType = mapDeclarationToDatatype.get(innerTypeToken.getKind());
        }
        if (!match(TokenKind.IDENTIFIER)) {
            throw new ErrorHandler(
                    "parsing",
                    peek().getLine(),
                    "Unexpected token: " + peek().getLexeme(),
                    "Expected a variable name after type declaration."
            );
            //throw new RuntimeException(peek() + " Expected variable name after type.");
        }
        Token name = previous();
        Expression initializer = null;
        if (match(TokenKind.EQUAL)) { // now two cases, either Expression, or it's a function call
            initializer = parseExpression();
        }
        consume(TokenKind.SEMICOLON);
        TokenKind dataType = mapDeclarationToDatatype.get(typeToken.getKind());
        currentDataType = dataType; // set the current data type being processed
        return new VariableDeclarationNode(new Token(dataType, typeToken.getLexeme(), null, typeToken.getLine()), name, initializer);
    }

    private FunctionDeclarationNode parseFunctionDeclaration() { // we should build the logic to allow users to define a function
        advance(); // consume the FUNC token
        if (!match(TokenKind.IDENTIFIER)) { // should make sure you're not defining a function with a reserved keyword or literal
            throw new ErrorHandler(
                    "parsing",
                    peek().getLine(),
                    "Unexpected token: " + peek().getLexeme(),
                    "Expected a function name after 'func' keyword."
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
        consume(TokenKind.RETURN); // consume the closing brace, we will handle the body later

        java.util.List<Statement> functionBody = null; // for now, we will just return null, since we don't have a body yet
        consume(TokenKind.CLOSE_BRACE); // consume the closing brace, we will handle the body later

        return new FunctionDeclarationNode( // does FunctionNode need its environment passed as well? unsure, yes and no
                functionName.getLexeme(),
                returnType,
                parameters,
                functionBody
        );
    }

    // still have no idea how i will even apply these things, but it will happen at some point
    // for now all i can really do is just gather the params, but i can't do anything with them quite yet
    private java.util.List<VariableSymbol> parseFunctionParameters() {
        java.util.List<VariableSymbol> parameters = new ArrayList<>();
        if (!check(TokenKind.CLOSE_PAREN)) {
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
    private java.util.List<Expression> parseFunctionArguments() {
        java.util.List<Expression> arguments = new ArrayList<>();
        if (!check(TokenKind.CLOSE_PAREN)) {
            System.out.println("token at parseFunctionArguments: " + peek());
            if (isFunctionCall()) { // just check if it's a function call in the arg (recursive allowed)
                arguments.add(parseFunctionCall()); // if the first argument is a function call, we parse it
                return arguments; // we return immediately, since we can't have more than one function call as an argument
            }
            do {
                Expression arg = parseExpression(); // STRING NODE for now
                System.out.println("Parsing argument: " + (arg != null ? arg.toString() : "null"));
                arguments.add(arg);
            } while (match(TokenKind.COMMA));
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

    private boolean check (Set<TokenKind> expectedKinds) {
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

    private Token previous() {
        return tokens.get(tokenPos - 1);
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
        // a function call starts with a variable name followed by an open parenthesis
        // return check(TokenKind.VARIABLE) && peek().getKind() == TokenKind.OPEN_PAREN;
        // we could just check the environment
        // sure, makes sense, but check if it's a FUNCTION...
        if (!check(TokenKind.IDENTIFIER)) { // if you don't check this, even a variable will be treated as a function call
            return false; // if the next token is not an identifier, it can't be a function call
        }
        if (!(environment.lookup(peek().getLexeme()) instanceof FunctionSymbol)) {
            return false; // if it's not a variable, it can't be a function call
        }
        return environment.isDeclared(peek().getLexeme()); // the logic here should be that a built-in would always be loaded already
    }

    private boolean isModuleImport() {
        return check(TokenKind.INCLUDE); // we can add more keywords for imports later
    }

    // in future add support for all types
    private static final Set<TokenKind> typeKeywords = Set.of(
            TokenKind.INTEGER_TYPE,
            TokenKind.FLOAT_TYPE,
            TokenKind.BOOLEAN_TYPE,
            TokenKind.MATRIX_TYPE,
            TokenKind.SYMBOL_TYPE,
            TokenKind.STRING_TYPE,
            TokenKind.VOID_TYPE
    );

    private static final Set<TokenKind> LinearAlgebraOperators = Set.of(
            TokenKind.MATRIX
    );

    // probably we need some more operators here later on
    // also for the linear algebra either we handle it through Add etc., or we make new nodes
    private BinaryNode inferBinaryNodeFromOperator(TokenKind operator, Expression lhs, Expression rhs) {
        System.out.println("inferBinaryNodeFromOperator called with operator: " + operator);
        System.out.println("lhs type: " + lhs.getType(environment) + ", rhs type: " + rhs.getType(environment));
        if (LinearAlgebraOperators.contains(lhs.getType(environment)) || LinearAlgebraOperators.contains(rhs.getType(environment))) {
            System.out.println("Creating Linalg binary node for operator: " + operator);
            switch (operator) {
                case PLUS -> {
                    return new LinalgAdd(lhs, rhs);
                }
                case MUL -> {
                    return new LinalgMul(lhs, rhs);
                }
                case DIV -> {
                    return new LinalgDiv(lhs, rhs);
                }
                case MINUS -> {
                    return new LinalgSub(lhs, rhs);
                }
            }
        }
        switch (operator) {
            case PLUS -> {
                return new Add(lhs, rhs);
            }
            case MINUS -> {
                return new Sub(lhs, rhs);
            }
            case MUL -> {
                return new Mul(lhs, rhs);
            }
            case DIV -> {
                return new Div(lhs, rhs);
            }
            case MOD -> {
                return new Mod(lhs, rhs);
            }
        }
        throw new ErrorHandler(
                "parsing",
                peek().getLine(),
                "Unsupported operator: " + operator,
                "Expected a valid arithmetic operator (+, -, *, /)."
        );
    }
}
