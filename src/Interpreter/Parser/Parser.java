package Interpreter.Parser;

import AST.Expressions.BinaryOperationNode;
import AST.Expressions.ConstantNode;
import AST.Expressions.FunctionNode;
import AST.Expressions.VariableNode;
import AST.Nodes.*;
import Interpreter.Tokenizer.MatrixToken;
import Interpreter.Tokenizer.TokenKind;
import Interpreter.Tokenizer.Token;
import DataStructures.Matrix;
import DataTypes.Computable;
import DataTypes.NumericValue;
import Util.LookupTable;
import DataTypes.Value;

import java.util.List;

// parsing going quite well so far, we need to add some error checking
// stuff like = signs not being followed by anything should crash the program
// all the trig functions need to be parsed correctly
// something very hard will now be to keep track of variables, we need a STACK to handle it somehow

public class Parser {

    private final List<Token> tokens;
    private int tokenPos = 0;
    public LookupTable<String, Expression, TokenKind> lookUpTable = new LookupTable<>();

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public Expression interpretCode() {
        return parseExpression();
    }


//    private ASTNode parseFactorOLD() {
//        Token token = tokens.get(tokenPos);
//        if (token.getKind() == TokenKind.INTEGER) {
//            tokenPos++;
//            return new ConstantNode(Integer.parseInt(token.getValue()));
//
//        } else if (token.getKind() == TokenKind.FLOAT) {
//            tokenPos++;
//            return new ConstantNode(Float.parseFloat(token.getValue()));
//
//        } else if (token.getKind() == TokenKind.VARIABLE) { // might be more complicated here
//            tokenPos++;
//            return new VariableNode(token.getValue(), TokenKind.VARIABLE);
//
//        } else if (token.getKind() == TokenKind.SYMBOL_TYPE) { // what i will end up doing is probably rename this to math_variable or whatever
//            tokenPos++;
//            token = tokens.get(tokenPos);
//            if (token.getKind() == TokenKind.SYMBOL) {
//                tokenPos++;
//                return new VariableNode(token.toString(), TokenKind.SYMBOL);
//            } else {
//                throw new RuntimeException("Expected variable after declaring symbol type");
//            }
//
//        } else if (token.getKind() == TokenKind.FLOAT_TYPE) { // some modifications could be made, as in we don't need to know it's a float after a FLOAT_TYPE
//            tokenPos++;
//            token = tokens.get(tokenPos);
//            declareVariable(tokenPos);
//            isValidNumberName(token.getValue()); // this does not work yet
//            if (tokens.get(tokenPos + 1).getKind() != TokenKind.SEMICOLON) { // if we hit a semicolon, we declare with no value
//                setValueToAssignedVariable(tokenPos);
//            }
//            return new VariableNode(token.toString(), TokenKind.FLOAT);
//
//        } else if (token.getKind() == TokenKind.INTEGER_TYPE) {
//            tokenPos++;
//            token = tokens.get(tokenPos);
//            declareVariable(tokenPos); // these operations are now modular
//            if (tokens.get(tokenPos + 1).getKind() != TokenKind.SEMICOLON) { // if we hit a semicolon, we declare with no value
//                setValueToAssignedVariable(tokenPos); // in this case, the user wants to assign a value to the variable
//            }
//            return new VariableNode(token.toString(), TokenKind.INTEGER);
//
//        } else if (token.getKind() == TokenKind.MATRIX_TYPE) {
//            tokenPos++;
//            token = tokens.get(tokenPos);
//            declareVariable(tokenPos);
//            if (tokens.get(tokenPos + 1).getKind() != TokenKind.SEMICOLON) {
//                setValueToAssignedVariable(tokenPos);
//            }
//            return new VariableNode(token.toString(), TokenKind.MATRIX);
//
//        } else if (token.getKind() == TokenKind.COS || token.getKind() == TokenKind.SIN || token.getKind() == TokenKind.TAN
//                || token.getKind() == TokenKind.COT || token.getKind() == TokenKind.SEC || token.getKind() == TokenKind.CSC) {
//            Token functionType = token; // unsure if this is needed
//            tokenPos++;
//            token = tokens.get(tokenPos);
//            if (token.getKind() != TokenKind.OPEN_PAREN) {
//                throw new RuntimeException("Expected open parenthesis after " + token);
//            }
//            System.out.println(token);
//            tokenPos++; // consume "("
//            // token = tokens.get(tokenPos);
//            ASTNode inParenContent = parseExpression();
//            if (tokenPos < tokens.size() && tokens.get(tokenPos).getKind() == TokenKind.CLOSE_PAREN) {
//                tokenPos++;
//            } else {
//                throw new RuntimeException("Expected matching closing parenthesis");
//            }
//            return getFunctionNode(functionType, inParenContent); // moved the switch to its own function, more readable
//
//        } else if (token.getKind() == TokenKind.OPEN_PAREN) {
//            tokenPos++; // consume '('
//            ASTNode inParenNode = parseExpression(); // call back to parse parentheses content
//            if (tokenPos < tokens.size() && tokens.get(tokenPos).getKind() == TokenKind.CLOSE_PAREN) {
//                tokenPos++; // here consume ')' closing paren
//            } else {
//                throw new RuntimeException("Expected matching closing parenthesis");
//            }
//            return inParenNode;
//        }
//        // return if no token makes sense
//        throw new RuntimeException("Unexpected token: " + token.getValue() + " (type -> " + token.getKind() + ")");
//    }
//
//    // this is more readable than inside the parseFactor method imo, could move back into it at a later stage
//    private FunctionNode getFunctionNode(Token functionType, ASTNode inParenContent) {
//        FunctionNode functionNode; // init empty
//        switch(functionType.getKind()) {
//            case TokenKind.COS -> functionNode = new FunctionNode("cos", inParenContent);
//            case TokenKind.SIN -> functionNode = new FunctionNode("sin", inParenContent);
//            case TokenKind.TAN -> functionNode = new FunctionNode("tan", inParenContent);
//            case TokenKind.COT -> functionNode = new FunctionNode("cot", inParenContent);
//            case TokenKind.SEC -> functionNode = new FunctionNode("sec", inParenContent);
//            case TokenKind.CSC -> functionNode = new FunctionNode("csc", inParenContent);
//
//            default -> throw new RuntimeException("Unexpected token type");
//        }
//        return functionNode;
//    }
//
//    private void declareVariable(Integer tokenPos) {
//        Token token = tokens.get(tokenPos);
//        if (token.getKind() != TokenKind.VARIABLE) {
//            throw new RuntimeException("Expected VARIABLE after declaring " + token.getKind());
//        }
//        String variableName = token.getValue();
//        TokenKind variableType = parseDataType(tokens.getFirst().getKind()); // this helper method infers the type
//        lookUpTable.assignValueToLookupTable(variableName, null, variableType); // default integer need to change
//    }
//
//    // i need to streamline this to make it way cleaner, right now it's hard to read
//    private void setValueToAssignedVariable(Integer tokenPos) {
//        tokenPos++;
//        Token token = tokens.get(tokenPos); // this is going to be a semicolon or a equal sign
//        System.out.println(tokens.get(tokenPos + 1));
//        TokenKind variableKind = parseDataType(tokens.getFirst().getKind()); // not sure if doing this is optimal, but it will work for now
//        Token variableToken = tokens.get(tokenPos + 1); // this is where we are now, we need to do a switch or something to check for matrix or not and assign to table
//        String variableName = tokens.get(tokenPos - 1).getValue(); // this is where we are now, we need to do a switch or something to check for matrix or not and assign to table
//        if (variableToken.getKind() == TokenKind.MATRIX) {
//            MatrixToken matrixToken = (MatrixToken) variableToken;
//            System.out.println("testing");
//            System.out.println(matrixToken.getValue());
//            System.out.println("end testing");
//            Matrix<Computable> matrix = parseMatrix(Integer.parseInt(String.valueOf(matrixToken.getValue().charAt(0))), Integer.parseInt(String.valueOf(matrixToken.getValue().charAt(2))), matrixToken.getEntries());
//            lookUpTable.assignValueToLookupTable(variableName, matrix, variableKind);
//        }
//        // this line is problematic for matrix declaration, as the matrix won't have a value -- it now does, but it's still a WIP, as it won't support expressions (only single values for now)
//        Computable tokenValue = parseValue(tokens.get(tokenPos + 1).getKind(), tokens.get(tokenPos + 1)); // might clean later, it's a number atm but this could also change to Object to support symbols
//        if (token.getKind() != TokenKind.EQUAL && token.getKind() != TokenKind.SEMICOLON) {
//            throw new RuntimeException("Expected SEMICOLON or EQUAL after declaring variable");
//        }
//        if (isValidAssignment(tokenPos) && tokens.get(tokenPos + 1).getKind() == TokenKind.INTEGER) {
//            lookUpTable.assignValueToLookupTable(tokens.get(tokenPos - 1).getValue(), tokenValue, TokenKind.INTEGER);
//        } else if (isValidAssignment(tokenPos) && tokens.get(tokenPos + 1).getKind() == TokenKind.FLOAT) {
//            lookUpTable.assignValueToLookupTable(tokens.get(tokenPos - 1).getValue(), tokenValue, TokenKind.FLOAT);
//        } else if (isValidAssignment(tokenPos) && tokens.get(tokenPos + 1).getKind() == TokenKind.MATRIX) {
//            System.out.println("Matrix assignment");
//            isValidMatrixAssignment(tokenPos);
//        } else {
//            throw new RuntimeException("Value " + tokenValue + " (" + tokens.get(tokenPos + 1).getKind() + ")" + " cannot be assigned to " + tokens.getFirst());
//        }
//    }
//
//    private NumericValue parseValue(TokenKind type, Token token) {
//        return switch (type) {
//            case INTEGER -> new NumericValue(Integer.parseInt(token.getValue()));
//            case FLOAT -> new NumericValue(Float.parseFloat(token.getValue()));
//            default -> throw new RuntimeException("Unsupported type: " + type);
//        };
//    }
//
//    private TokenKind parseDataType(TokenKind firstToken) {
//        return switch (firstToken) {
//            case FLOAT_TYPE -> TokenKind.FLOAT;
//            case INTEGER_TYPE -> TokenKind.INTEGER;
//            case MATRIX_TYPE -> TokenKind.MATRIX;
//            default -> throw new RuntimeException("Unsupported type: " + firstToken);
//        };
//    }
//
//    private boolean isValidAssignment(Integer tokenPos) { // i can also use a token, it doesn't matter
//        return tokens.get(tokenPos).getKind() == TokenKind.EQUAL
//                && tokenPos + 1 < tokens.size()
//                && tokens.get(tokenPos + 1).getKind() == parseDataType(tokens.getFirst().getKind())
//                && tokens.get(tokenPos + 2).getKind() == TokenKind.SEMICOLON;
//    }
//
//    private void isValidNumberName(String variableName) {
//        if (variableName == null || variableName.length() != 1 || !variableName.matches("[a-z]")) {
//            throw new IllegalArgumentException("Integer/Float name must be a single lowercase alphabetical character");
//        }
//    }
//
//    private void isValidMatrixName(String variableName) { // check if uppercase letter
//        if (!variableName.matches("[A-Z]")) { // check regex for alphabet
//            throw new IllegalArgumentException("Matrix name must be a single uppercase alphabetical character.");
//        }
//    }
//
//    // this might not be super optimized quite yet, i'll write something better at some stage
//    private Matrix<Computable> parseMatrix(int numRows, int numCols, List<Token> matrixEntries) {
//        System.out.println("Matrix assignment");
//        System.out.println(matrixEntries);
//        Matrix<Computable> matrix = new Matrix<>(numRows, numCols);
//        // the following was chatGPT because i could not find a solution
//        // it is called "looping through matrixEntries in a row-major order" -> no idea how this works, avoids a nested for loop
//        for (int i = 0; i < matrixEntries.size(); i++) {
//            int row = i / numCols;  // row index
//            int col = i % numCols;  // column index
//            Token entry = matrixEntries.get(i);
//            Computable value = parseValue(entry.getKind(), entry);
//            matrix.set(row, col, value);
//        }
//        System.out.println(matrix);
//        return matrix;
//    }
//
//    private void isValidMatrixAssignment(Integer tokenPos) {
//        System.out.println(tokens.get(tokenPos)); // this has to be completed eventually
//    }

    private Expression parseExpression() {
        return parseEquality();
    }

    private Expression parseEquality() {
        Expression expression = parseComparison();
        while (match(TokenKind.EQUAL_EQUAL, TokenKind.NOT_EQUAL)) {
            Token operator = previous();
            Expression rhs = parseComparison();
            expression = new binaryNode(expression, operator, rhs);
        }
        return expression;
    }

    private Expression parseTerm() {
        Expression expression = parseFactor();
        while (match(TokenKind.PLUS, TokenKind.MINUS)) {
            Token operator = previous();
            Expression rhs = parseFactor();
            expression = new binaryNode(expression, operator, rhs);
        }
        return expression;
    }

    private Expression parseFactor() {
        Expression expression = parseUnary();
        while (match(TokenKind.MUL, TokenKind.DIV)) {
            Token operator = previous();
            Expression rhs = parseUnary();
            expression = new binaryNode(expression, operator, rhs);
        }
        return expression;
    }

    private Expression parseComparison() {
        Expression expression = parseTerm();
        while (match(TokenKind.GREATER, TokenKind.LESS, TokenKind.GREATER_EQUAL, TokenKind.LESS_EQUAL)) {
            Token operator = previous(); // because match() consumes the token (advances position)
            Expression rhs = parseTerm(); // here we are also consuming the next token, which ensures the while loop actually works
            expression = new binaryNode(expression, operator, rhs); {
            }
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
        if (match(TokenKind.NUM, TokenKind.STRING)) {
            return new primaryNode(previous().getLiteral()); // remember match() consumes a token !!
        }
        if (match(TokenKind.OPEN_PAREN)) {
            Expression expression = parseExpression();
            consumeClosingParen(TokenKind.CLOSE_PAREN); // this is like match, we're looking for a closing parenthesis
            return new groupingNode(expression);
        }
        throw new Error("Can't parse expression :" + peek());
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

    private Token consumeClosingParen(TokenKind kind) {
        if (check(kind)) return advance();
        else throw new Error("Expecting closing paren at line");
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
}
