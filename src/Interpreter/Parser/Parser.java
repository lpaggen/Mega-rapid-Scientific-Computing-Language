package Interpreter.Parser;

import AST.Nodes.*;
import AST.Nodes.BuiltIns.BuiltIns;
import Interpreter.ErrorHandler;
import Interpreter.Tokenizer.TokenKind;
import Interpreter.Tokenizer.Token;
import Util.EnvReWrite;
import Util.VariableSymbol;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

// IMPORTANT NOTE
// the parser is ONLY responsible for parsing the code and building the AST
// it does NOT check for semantic errors or type mismatches
// that is the job of the interpreter or a separate semantic analyzer
// the parser will throw runtime exceptions if it encounters unexpected tokens
// the parser ONLY cares about SYNTAX AND GRAMMAR
public class Parser {

    private final List<Token> tokens;
    private int tokenPos = 0;
    public EnvReWrite environment = new EnvReWrite(); // remember this initializes a global scope BY DEFAULT

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
            //return parseFunction();
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

    private Statement parseFunctionDeclaration() { // we should build the logic to allow users to define a function
        return null;
        // we will handle this later, for now we just want to parse the function call (handle builtins)
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

    private Token consume(TokenKind kind) {
        if (check(kind)) return advance();
        else throw new Error("No match for " + kind + " at line " + peek().getLine() + ". Expected: " + kind);
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

    boolean isFunction() {
        return BuiltIns.isBuiltInFunction(peek().getLexeme());
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
