package Compiler.Parser;

import AST.Expressions.BinaryOperationNode;
import AST.Expressions.ConstantNode;
import AST.Expressions.FunctionNode;
import AST.Expressions.VariableNode;
import AST.Nodes.ASTNode;
import Compiler.Tokenizer.TokenKind;
import Compiler.Tokenizer.Token;
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

    // i might throw everything into a switch, seems there are a lot of if else
    // declarations of anything other than a symbol must come with value assigment, so into the lookup table immediately
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

            // i might put this into a function later on, the code is pretty similar for all type declarations
        } else if (token.getKind() == TokenKind.FLOAT_TYPE) { // some modifications could be made, as in we don't need to know it's a float after a FLOAT_TYPE
            tokenPos++;
            token = tokens.get(tokenPos);
            declareVariable(tokenPos);
            setValueToAssignedVariable(tokenPos);
            return new VariableNode(token.toString(), TokenKind.FLOAT);

        } else if (token.getKind() == TokenKind.INTEGER_TYPE) {
            tokenPos++;
            token = tokens.get(tokenPos);
            declareVariable(tokenPos); // these operations are now modular
            setValueToAssignedVariable(tokenPos);
            return new VariableNode(token.toString(), TokenKind.INTEGER);

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

    private void declareVariableToTable(String key, Object value, TokenKind type) { // this helper method assigns (and will handle errors) variables to values
        lookUpTable.assignValueToLookupTable(key, value, type);
    }

    private void declareVariable(Integer tokenPos) {
        Token token = tokens.get(tokenPos);
        if (token.getKind() != TokenKind.VARIABLE) {
            throw new RuntimeException("Expected blablabla need to fix this");
        }
        String variableName = token.getValue();
        lookUpTable.assignValueToLookupTable(variableName, null, TokenKind.INTEGER);
    }

    private void setValueToAssignedVariable(Integer tokenPos) {
        tokenPos++;
        Token token = tokens.get(tokenPos);
        Object tokenValue = parseValue(tokens.get(tokenPos + 1).getKind(), tokens.get(tokenPos + 1)); // might clean later
        System.out.println(tokenValue);
        if (token.getKind() != TokenKind.EQUAL && token.getKind() != TokenKind.SEMICOLON) {
            throw new RuntimeException("Expected blablabla will fix this too");
        }
        if (token.getKind() == TokenKind.EQUAL && tokenPos + 1 < tokens.size() && tokens.get(tokenPos + 1).getKind() == TokenKind.INTEGER && tokens.get(tokenPos + 2).getKind() == TokenKind.SEMICOLON) {
            // here implement the logic to check for if it's a integer, etc, and assign value to variable in the table
            // i will definitely be refactoring this at a later stage
            lookUpTable.assignValueToLookupTable(tokens.get(tokenPos - 1).getValue(), Integer.parseInt(tokens.get(tokenPos + 1).getValue()), TokenKind.INTEGER);
        } else {
            throw new RuntimeException("Expected b value after declaring u type");
        }
    }

    private Object parseValue(TokenKind type, Token token) { // i might use this to declare variables easily
        Object out;
        switch (type) {
            case INTEGER -> out = Integer.parseInt(token.getValue());
            case FLOAT -> out = Float.parseFloat(token.getValue());

            default -> throw new RuntimeException("Unsupported type: " + type);
        }
        return out;
    }
}
