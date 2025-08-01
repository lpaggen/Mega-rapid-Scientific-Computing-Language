package Interpreter.Parser;

import AST.Nodes.*;
import AST.Nodes.BuiltIns.BuiltIns;
import AST.Nodes.BuiltIns.BuiltIns.*;
import AST.Nodes.BuiltIns.PrintFunction;
import Interpreter.ErrorHandler;
import Interpreter.Tokenizer.TokenKind;
import Interpreter.Tokenizer.Token;
import Util.Environment;
import Util.FunctionSymbol;
import Util.VariableSymbol;

import java.util.*;

// IMPORTANT NOTE
// the parser is ONLY responsible for parsing the code and building the AST
// it does NOT check for semantic errors or type mismatches
// that is the job of the interpreter or a separate semantic analyzer
// the parser will throw runtime exceptions if it encounters unexpected tokens
// the parser ONLY cares about SYNTAX AND GRAMMAR
public class Parser {

    private final List<Token> tokens;
    private int tokenPos = 0;
    public Environment environment = new Environment(); // remember this initializes a global scope BY DEFAULT

    // BuiltIns.initializeBuiltIns(environment); // initialize built-in functions in the environment

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public void interpretCode() {
        while (!isAtEnd()) {
            Statement statement = parseStatement();
            statement.execute(environment);
        }
    }

    // everything should really start from the Statement, since we have to declare variables, functions, etc.
    private Statement parseStatement() {
        System.out.println("current token at parseStatement: " + peek());
        if (isDeclarationStart()) {
            return parseDeclaration();
        } else if (isFunction()) {
            System.out.println("here");
            // here i guess the function for calling functions, declaring functions happens
            // so maybe we need two nodes, FunctionNode and FunctionDeclarationNode ? will see
            return parseFunctionDeclaration();
        }
        return null;
    }

    private Expression parseExpression() {
        System.out.println("current token at parseExpression: " + peek());
        return parseEquality();
    }

    private Expression parseEquality() {
        Expression expression = parseComparison();
        while (match(TokenKind.EQUAL_EQUAL, TokenKind.NOT_EQUAL)) {
            Token operator = previous();
            Expression rhs = parseComparison();
            expression = new LogicalBinaryNode(expression, operator, rhs);
        }
        return expression;
    }

    private Expression parseComparison() {
        Expression expression = parseTerm();
        while (match(TokenKind.GREATER, TokenKind.LESS, TokenKind.GREATER_EQUAL, TokenKind.LESS_EQUAL)) {
            Token operator = previous(); // because match() consumes the token (advances position)
            Expression rhs = parseTerm(); // here we are also consuming the next token, which ensures the while loop actually works
            expression = new LogicalBinaryNode(expression, operator, rhs);
        }
        return expression;
    }

    private Expression parseTerm() {
        Expression expression = parseFactor();
        while (match(TokenKind.PLUS, TokenKind.MINUS)) {
            Token operator = previous();
            Expression rhs = parseFactor();
            expression = new BinaryNode(expression, operator, rhs);
        }
        return expression;
    }

    private Expression parseFactor() {
        Expression expression = parseUnary();
        while (match(TokenKind.MUL, TokenKind.DIV)) {
            Token operator = previous();
            Expression rhs = parseUnary();
            expression = new BinaryNode(expression, operator, rhs);
        }
        return expression;
    }

    private Expression parseUnary() {
        if (match(TokenKind.NOT, TokenKind.MINUS)) { // handle both ! and negation
            Token operator = previous();
            Expression rhs = parseUnary();
            return new unaryNode(operator, rhs);
        }
        return parsePrimary();
    }

    private Expression parsePrimary() {
        if (match(TokenKind.FALSE)) return new primaryNode(false);
        if (match(TokenKind.TRUE)) return new primaryNode(true);
        if (match(TokenKind.NULL)) return new primaryNode(null);
        if (match(TokenKind.INTEGER, TokenKind.FLOAT, TokenKind.STRING)) {
            return new primaryNode(previous().getLiteral());
        }
        if (match(TokenKind.OPEN_PAREN)) {
            Expression expression = parseExpression(); // TO FIX: for some odd reason, everything becomes NULL
            consume(TokenKind.CLOSE_PAREN); // this is like match, we're looking for a closing parenthesis
            return new groupingNode(expression);
        } // atm we can't really execute any computations, will check what to do in later build
        if (match(TokenKind.VARIABLE)) {
            Token variableToken = previous();
            if (!environment.isDeclared(variableToken.getLexeme())) {
                throw new ErrorHandler(
                        "parsing",
                        variableToken.getLine(),
                        "Variable not declared: " + variableToken.getLexeme(),
                        "Please declare the variable before using it."
                );
                //throw new RuntimeException("Variable not declared: " + variableToken.getLexeme() + " at line " + variableToken.getLine());
            } // so now, we should maybe do a recursive call to parsePrimary(), or just take the value directly and out a primaryNode
            //return new VariableNode(variableToken);
            VariableSymbol var = (VariableSymbol) environment.lookup(variableToken.getLexeme());
            Object value = var.getValue();
            return new primaryNode(value); // this will return the value of the variable, not the variable itself
        }
        throw new ErrorHandler(
                "parsing",
                peek().getLine(),
                "Unexpected token: " + peek().getLexeme(),
                "Expected an expression, variable, or literal value."
        );
        //throw new RuntimeException(peek() + " Expected expression.");
    }

    // this method handles variable declarations, i will add more error checks at some stage
    // for now i just want to be able to recognize variables and declare them into the env
    // atm we can declare with or without a value
    // !! type mismatch errors happen at another stage of the interpreter
    private DeclarationNode parseDeclaration() {

        // this surely can't be optimal, but it will work until i figure something better out
        // the idea is to just fetch the type from the declaration
        Map<TokenKind, TokenKind> mapDeclarationToDatatype = Map.of(
                TokenKind.INTEGER_TYPE, TokenKind.INTEGER,
                TokenKind.FLOAT_TYPE, TokenKind.FLOAT,
                TokenKind.BOOLEAN_TYPE, TokenKind.BOOLEAN,
                TokenKind.MATRIX_TYPE, TokenKind.MATRIX,
                TokenKind.SYMBOL_TYPE, TokenKind.SYMBOL,
                TokenKind.STRING_TYPE, TokenKind.STRING
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
        Token typeToken = advance();
        if (!match(TokenKind.VARIABLE)) {
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
        if (match(TokenKind.EQUAL)) {
            initializer = parseExpression();
        }
        consume(TokenKind.SEMICOLON);
        TokenKind dataType = mapDeclarationToDatatype.get(typeToken.getKind());
        System.out.println("Declaring variable: " + name.getLexeme() + " of type: " + dataType);
        // VariableSymbol variableSymbol = new VariableSymbol(name.getLexeme(), dataType, initializer);
        // environment.declareVariable(name.getLexeme(), variableSymbol);

        // environment.declareVariable(name.getLexeme(), new Token(dataType, name.getLexeme(), null, typeToken.getLine()));
        return new DeclarationNode(new Token(dataType, typeToken.getLexeme(), null, typeToken.getLine()), name, initializer);
    }

    private FunctionNode parseFunctionDeclaration() { // we should build the logic to allow users to define a function
        if (BuiltIns.isBuiltInFunction(peek().getLexeme())) { // if it's a built-in function, we handle it differently
            String builtInFunctionName = peek().getLexeme();
            System.out.println("Parsing built-in function: " + builtInFunctionName);
            // hardcode for print right now, just gotta test it to see if my design works or not
            if (builtInFunctionName.equals("print")) {
                advance(); // consume the FUNC token
                consume(TokenKind.OPEN_PAREN); // consume the opening parenthesis
                List<Statement> parameters = parseFunctionParameters(); // this will parse the parameters of the function
                advance(); // advance to the next token, this should really be done in the parseArguments
                consume(TokenKind.CLOSE_PAREN); // consume the closing parenthesis
                consume(TokenKind.SEMICOLON); // consume the semicolon at the end of the print function call
                return new PrintFunction(environment); // return a PrintNode with the parameters and environment
            }
            //return BuiltIns.getBuiltInFunction(builtInFunctionName); // this will return a FunctionNode for the built-in function
        }
        advance(); // consume the FUNC token
        if (!match(TokenKind.VARIABLE)) { // should make sure you're not defining a function with a reserved keyword or literal
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
        List<Statement> parameters = parseFunctionParameters(); // this will parse the parameters of the function
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
        TokenKind returnType = consume(TokenKind.VOID_TYPE,
                                        TokenKind.INTEGER_TYPE,
                                        TokenKind.FLOAT_TYPE,
                                        TokenKind.BOOLEAN_TYPE,
                                        TokenKind.MATRIX_TYPE,
                                        TokenKind.SYMBOL_TYPE,
                                        TokenKind.STRING_TYPE).getKind();
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
        System.out.println("current: " + peek()); // sitting at return

        // somehow the body has to be parsed before return
        consume(TokenKind.RETURN); // consume the closing brace, we will handle the body later

        List<Statement> functionBody = null; // for now, we will just return null, since we don't have a body yet
        consume(TokenKind.CLOSE_BRACE); // consume the closing brace, we will handle the body later

        return new FunctionNode( // does FunctionNode need its environment passed as well? unsure, yes and no
                functionName.getLexeme(),
                returnType,
                parameters,
                functionBody,
                environment
        );
    }

    // i don't think i want to specify the return type of the built-in functions
    // since they're built in, they should have a fixed return type in a way?
//    private Expression parseFunctionCall() {
//        environment.pushScope();
//        if (BuiltIns.isBuiltInFunction(peek().getLexeme())) { // first we handle built-in functions (in library)
//            FunctionNode builtInFunction = BuiltIns.getBuiltInFunction(peek().getLexeme());
//            System.out.println("Parsing built-in function: " + builtInFunction.getName());
//            TokenKind returnType = builtInFunction.getReturnType(); // get the return type of the built-in function
//            advance(); // consume the function name
//            consume(TokenKind.OPEN_PAREN); // consume the opening parenthesis
//            List<Expression> parameters = parseFunctionParameters();
//            consume(TokenKind.CLOSE_PAREN); // consume the closing parenthesis
//            // for a built-in function, we don't have a body, so we can just return a FunctionNode with null body
//            return new FunctionNode(builtInFunction.getName(), returnType, parameters, environment);
//        } else {
//            throw new ErrorHandler(
//                    "parsing",
//                    peek().getLine(),
//                    "Unexpected token: " + peek().getLexeme(),
//                    "Expected a built-in function call."
//            );
//        }
//    }

    // still have no idea how i will even apply these things, but it will happen at some point
    // for now all i can really do is just gather the params, but i can't do anything with them quite yet
    private List<Statement> parseFunctionParameters() {
        List<Statement> parameters = new java.util.ArrayList<>();
        if (!check(TokenKind.CLOSE_PAREN)) { // if we don't have a closing parenthesis, we have parameters
            do {
                parameters.add(parseStatement());
            } while (match(TokenKind.COMMA)); // allow multiple parameters separated by commas
        }
        return parameters;
    }

//    private Statement parseFunctionCall() {
//        if (BuiltIns.isBuiltInFunction(peek().getLexeme())) { // first we handle built-in functions (in library)
//            FunctionNode builtInFunction = BuiltIns.getBuiltInFunction(peek().getLexeme());
//            advance(); // consume the function name
//            consume(TokenKind.OPEN_PAREN); // consume the opening parenthesis
//            List<Expression> parameters = parseFunctionParameters();
//            consume(TokenKind.CLOSE_PAREN); // consume the closing parenthesis
//            return new FunctionNode(builtInFunction, parameters);
//        } else {
//            throw new ErrorHandler(
//                    "parsing",
//                    peek().getLine(),
//                    "Unexpected token: " + peek().getLexeme(),
//                    "Expected a built-in function call."
//            );
//        }
//    }

    // here we need a way to do things correctly
//    private Statement parseFunctionCall() {
//        if (BuiltIns.isBuiltInFunction(peek().getLexeme())) { // first we handle built-in functions (in library) ? why ?
//            FunctionNode builtInFunction = BuiltIns.getBuiltInFunction(peek().getLexeme());
//            advance(); // consume the function name
//            consume(TokenKind.OPEN_PAREN); // consume the opening parenthesis
//            List<Expression> parameters = parseFunctionParameters();
//            consume(TokenKind.CLOSE_PAREN); // consume the closing parenthesis
//            return new FunctionNode(builtInFunction, parameters);
//        } else {
//            // handle user-defined functions
//            Token functionName = consume(TokenKind.VARIABLE);
//            consume(TokenKind.OPEN_PAREN);
//            List<Expression> parameters = parseFunctionParameters();
//            consume(TokenKind.CLOSE_PAREN);
//            consume(TokenKind.OPEN_BRACE);
//            List<Statement> body = parseFunctionBody();
//            consume(TokenKind.CLOSE_BRACE);
//            return new FunctionNode(functionName.getLexeme(), parameters, body);
//        }
//    }

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

    private Token previous() {
        return tokens.get(tokenPos - 1);
    }

    private boolean isAtEnd() {
        return peek().getKind() == TokenKind.EOF;
    }

    boolean isDeclarationStart() {
        return check(TokenKind.INTEGER_TYPE, TokenKind.FLOAT_TYPE, TokenKind.BOOLEAN_TYPE, TokenKind.MATRIX_TYPE, TokenKind.SYMBOL_TYPE, TokenKind.STRING_TYPE);
    }

    // will check both if it's a built-in function or a user-defined function
    boolean isFunction() {
        return BuiltIns.isBuiltInFunction(peek().getLexeme()) || check(TokenKind.FUNC);
    }

    // in future add support for all types
    private static final Set<TokenKind> typeKeywords = Set.of(
            TokenKind.INTEGER_TYPE,
            TokenKind.FLOAT_TYPE,
            TokenKind.BOOLEAN_TYPE,
            TokenKind.MATRIX_TYPE,
            TokenKind.SYMBOL_TYPE,
            TokenKind.STRING_TYPE
    );
}

// BUGS detected that need fixing
// can't declare variables with null values, this goes against my original ideas
// when declaring a variable, wrong Error is returned in case of eg: int x == 5; ???? why ??
