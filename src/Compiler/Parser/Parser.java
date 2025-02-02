package Compiler.Parser;

import AST.Expressions.BinaryOperationNode;
import AST.Expressions.ConstantNode;
import AST.Expressions.FunctionNode;
import AST.Expressions.VariableNode;
import AST.Nodes.ASTNode;
import Compiler.Tokenizer.TokenKind;
import Compiler.Tokenizer.Token;
import DataStructures.Matrix;
import Util.IntegerValue;
import Util.LookupTable;
import Util.Value;

import java.util.List;

// parsing going quite well so far, we need to add some error checking
// stuff like = signs not being followed by anything should crash the program
// all the trig functions need to be parsed correctly
// something very hard will now be to keep track of variables, we need a STACK to handle it somehow

public class Parser {

    private final List<Token> tokens;
    private int tokenPos = 0;
    public LookupTable<String, Value, TokenKind> lookUpTable = new LookupTable<>();

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public ASTNode interpretCode() {
        return parseExpression();
    }

    private ASTNode parseExpression() {
        // here we need to handle all EXPRESSIONS -> +, -, so all binary nodes
        ASTNode left = parseTerm();

        while (tokenPos < tokens.size()) {
            Token nextToken = tokens.get(tokenPos); // advance to next token
            if (nextToken.getKind() == TokenKind.PLUS || nextToken.getKind() == TokenKind.MINUS) {
                tokenPos++;
                ASTNode right = parseTerm(); // update left underneath
                left = new BinaryOperationNode(left, right, nextToken); // should work as intended
            } else {
                break; // when no more tokens to parse
            }
        }
        return left;
    }

    private ASTNode parseTerm() {
        ASTNode left = parseFactor();

        while (tokenPos < tokens.size()) {
            Token nextToken = tokens.get(tokenPos);
            if (nextToken.getKind() == TokenKind.MUL || nextToken.getKind() == TokenKind.DIV) {
                tokenPos++;
                ASTNode right = parseFactor();
                left = new BinaryOperationNode(left, right, nextToken);
            } else {
                break;
            }
        }
        return left;
    }

    private ASTNode parseFactor() {
        Token token = tokens.get(tokenPos);
        if (token.getKind() == TokenKind.INTEGER) {
            tokenPos++;
            return new ConstantNode(Integer.parseInt(token.getValue()));

        } else if (token.getKind() == TokenKind.FLOAT) {
            tokenPos++;
            return new ConstantNode(Float.parseFloat(token.getValue()));

        } else if (token.getKind() == TokenKind.VARIABLE) { // might be more complicated here
            tokenPos++;
            return new VariableNode(token.getValue(), TokenKind.VARIABLE);

//        } else if (token.getKind() == TokenKind.SYMBOL_TYPE) { // what i will end up doing is probably rename this to math_variable or whatever
//            tokenPos++;
//            token = tokens.get(tokenPos);
//            if (token.getKind() == TokenKind.SYMBOL) {
//                tokenPos++;
//                return new VariableNode(token.toString(), TokenKind.SYMBOL);
//            } else {
//                throw new RuntimeException("Expected variable after declaring symbol type");
//            }

        } else if (token.getKind() == TokenKind.FLOAT_TYPE) { // some modifications could be made, as in we don't need to know it's a float after a FLOAT_TYPE
            tokenPos++;
            token = tokens.get(tokenPos);
            declareVariable(tokenPos);
            isValidNumberName(token.getValue()); // this does not work yet
            if (tokens.get(tokenPos + 1).getKind() != TokenKind.SEMICOLON) { // if we hit a semicolon, we declare with no value
                setValueToAssignedVariable(tokenPos);
            }
            return new VariableNode(token.toString(), TokenKind.FLOAT);

        } else if (token.getKind() == TokenKind.INTEGER_TYPE) {
            tokenPos++;
            token = tokens.get(tokenPos);
            declareVariable(tokenPos); // these operations are now modular
            if (tokens.get(tokenPos + 1).getKind() != TokenKind.SEMICOLON) { // if we hit a semicolon, we declare with no value
                setValueToAssignedVariable(tokenPos); // in this case, the user wants to assign a value to the variable
            }
            return new VariableNode(token.toString(), TokenKind.INTEGER);

        } else if (token.getKind() == TokenKind.MATRIX_TYPE) {
            tokenPos++;
            token = tokens.get(tokenPos);
            declareVariable(tokenPos);
            if (tokens.get(tokenPos + 1).getKind() != TokenKind.SEMICOLON) {
                setValueToAssignedVariable(tokenPos);
            }
            return new VariableNode(token.toString(), TokenKind.MATRIX);

        } else if (token.getKind() == TokenKind.COS || token.getKind() == TokenKind.SIN || token.getKind() == TokenKind.TAN
                || token.getKind() == TokenKind.COT || token.getKind() == TokenKind.SEC || token.getKind() == TokenKind.CSC) {
            Token functionType = token; // unsure if this is needed
            tokenPos++;
            token = tokens.get(tokenPos);
            if (token.getKind() != TokenKind.OPEN_PAREN) {
                throw new RuntimeException("Expected open parenthesis after " + token);
            }
            System.out.println(token);
            tokenPos++; // consume "("
            // token = tokens.get(tokenPos);
            ASTNode inParenContent = parseExpression();
            if (tokenPos < tokens.size() && tokens.get(tokenPos).getKind() == TokenKind.CLOSE_PAREN) {
                tokenPos++;
            } else {
                throw new RuntimeException("Expected matching closing parenthesis");
            }
            return getFunctionNode(functionType, inParenContent); // moved the switch to its own function, more readable

        } else if (token.getKind() == TokenKind.OPEN_PAREN) {
            tokenPos++; // consume '('
            ASTNode inParenNode = parseExpression(); // call back to parse parentheses content
            if (tokenPos < tokens.size() && tokens.get(tokenPos).getKind() == TokenKind.CLOSE_PAREN) {
                tokenPos++; // here consume ')' closing paren
            } else {
                throw new RuntimeException("Expected matching closing parenthesis");
            }
            return inParenNode;
        }
        // return if no token makes sense
        throw new RuntimeException("Unexpected token: " + token.getValue() + " (type -> " + token.getKind() + ")");
    }

    // this is more readable than inside the parseFactor method imo, could move back into it at a later stage
    private FunctionNode getFunctionNode(Token functionType, ASTNode inParenContent) {
        FunctionNode functionNode; // init empty
        switch(functionType.getKind()) {
            case TokenKind.COS -> functionNode = new FunctionNode("cos", inParenContent);
            case TokenKind.SIN -> functionNode = new FunctionNode("sin", inParenContent);
            case TokenKind.TAN -> functionNode = new FunctionNode("tan", inParenContent);
            case TokenKind.COT -> functionNode = new FunctionNode("cot", inParenContent);
            case TokenKind.SEC -> functionNode = new FunctionNode("sec", inParenContent);
            case TokenKind.CSC -> functionNode = new FunctionNode("csc", inParenContent);

            default -> throw new RuntimeException("Unexpected token type");
        }
        return functionNode;
    }

    private void declareVariable(Integer tokenPos) {
        Token token = tokens.get(tokenPos);
        if (token.getKind() != TokenKind.VARIABLE) {
            throw new RuntimeException("Expected VARIABLE after declaring " + token.getKind());
        }
        String variableName = token.getValue();
        TokenKind variableType = parseDataType(tokens.getFirst().getKind()); // this helper method infers the type
        lookUpTable.assignValueToLookupTable(variableName, null, variableType); // default integer need to change
    }

    // i need to streamline this to make it way cleaner, right now it's hard to read
    private void setValueToAssignedVariable(Integer tokenPos) {
        tokenPos++;
        Token token = tokens.get(tokenPos);
        // this line is problematic for matrix declaration, as the matrix won't have a value
        Object tokenValue = parseValue(tokens.get(tokenPos + 1).getKind(), tokens.get(tokenPos + 1)); // might clean later
        if (token.getKind() != TokenKind.EQUAL && token.getKind() != TokenKind.SEMICOLON) {
            throw new RuntimeException("Expected SEMICOLON or EQUAL after declaring variable");
        }
        if (isValidAssignment(tokenPos) && tokens.get(tokenPos + 1).getKind() == TokenKind.INTEGER) {
            lookUpTable.assignValueToLookupTable(tokens.get(tokenPos - 1).getValue(), tokenValue, TokenKind.INTEGER);
        } else if (isValidAssignment(tokenPos) && tokens.get(tokenPos + 1).getKind() == TokenKind.FLOAT) {
            lookUpTable.assignValueToLookupTable(tokens.get(tokenPos - 1).getValue(), tokenValue, TokenKind.FLOAT);
        } else if (isValidAssignment(tokenPos) && tokens.get(tokenPos + 1).getKind() == TokenKind.MATRIX) {
            System.out.println("Matrix assignment");
            isValidMatrixAssignment(tokenPos);
        } else {
            throw new RuntimeException("Value " + tokenValue + " (" + tokens.get(tokenPos + 1).getKind() + ")" + " cannot be assigned to " + tokens.getFirst());
        }
    }

    // this currently does not support matrices
    // either i add support here, or i separate it into some "parseMatrix" function
    private Number parseValue(TokenKind type, Token token) {
        return switch (type) {
            case INTEGER -> Integer.parseInt(token.getValue());
            case FLOAT -> Float.parseFloat(token.getValue());
            default -> throw new RuntimeException("Unsupported type: " + type);
        };
    }

    private TokenKind parseDataType(TokenKind firstToken) {
        return switch (firstToken) {
            case FLOAT_TYPE -> TokenKind.FLOAT;
            case INTEGER_TYPE -> TokenKind.INTEGER;
            case MATRIX_TYPE -> TokenKind.MATRIX;
            default -> throw new RuntimeException("Unsupported type: " + firstToken);
        };
    }

    private boolean isValidAssignment(Integer tokenPos) { // i can also use a token, it doesn't matter
        return tokens.get(tokenPos).getKind() == TokenKind.EQUAL
                && tokenPos + 1 < tokens.size()
                && tokens.get(tokenPos + 1).getKind() == parseDataType(tokens.getFirst().getKind())
                && tokens.get(tokenPos + 2).getKind() == TokenKind.SEMICOLON;
    }

    private void isValidNumberName(String variableName) {
        if (variableName == null || variableName.length() != 1 || !variableName.matches("[a-z]")) {
            throw new IllegalArgumentException("Integer/Float name must be a single lowercase alphabetical character");
        }
    }

    private void isValidMatrixAssignment(Integer tokenPos) {
        System.out.println(tokens.get(tokenPos));
    }
}
