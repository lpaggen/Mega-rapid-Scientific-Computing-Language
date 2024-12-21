package Compiler.Parser;

import AST.Expressions.BinaryOperationNode;
import AST.Expressions.ConstantNode;
import AST.Expressions.FunctionNode;
import AST.Expressions.VariableNode;
import AST.Nodes.ASTNode;
import Compiler.Tokenizer.TokenKind;
import Compiler.Tokenizer.Token;
import Util.LookupTable;

import java.util.List;

// parsing going quite well so far, we need to add some error checking
// stuff like = signs not being followed by anything should crash the program
// all the trig functions need to be parsed correctly
// something very hard will now be to keep track of variables, we need a STACK to handle it somehow

public class Parser {

    private final List<Token> tokens;
    private int tokenPos = 0;
    private LookupTable<String, String, String> table = new LookupTable();

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

        } else if (token.getKind() == TokenKind.SYMBOL_TYPE) {
            tokenPos++;
            token = tokens.get(tokenPos);
            if (token.getKind() == TokenKind.SYMBOL) {
                tokenPos++;
                return new VariableNode(token.toString(), TokenKind.SYMBOL);
            } else {
                throw new RuntimeException("Expected variable after declaring symbol type");
            }

        } else if (token.getKind() == TokenKind.FLOAT_TYPE) {
            tokenPos++;
            token = tokens.get(tokenPos);
            if (token.getKind() == TokenKind.FLOAT) {
                tokenPos++;
                return new VariableNode(token.toString(), TokenKind.FLOAT);
            } else {
                throw new RuntimeException("Expected variable after declaring float type");
            }

        } else if (token.getKind() == TokenKind.INTEGER_TYPE) {
            tokenPos++;
            token = tokens.get(tokenPos);
            if (token.getKind() == TokenKind.INTEGER) {
                tokenPos++;
                return new VariableNode(token.toString(), TokenKind.INTEGER);
            } else {
                throw new RuntimeException("Expected variable after declaring integer type");
            }

        } else if (token.getKind() == TokenKind.COS || token.getKind() == TokenKind.SIN || token.getKind() == TokenKind.TAN
                || token.getKind() == TokenKind.COT || token.getKind() == TokenKind.SEC || token.getKind() == TokenKind.CSC) {
            Token functionType = token; // unsure if this is needed
            tokenPos++;
            token = tokens.get(tokenPos);
            if (token.getKind() != TokenKind.OPEN_PAREN) {
                throw new RuntimeException("Expected open parenthesis after declaring cos");
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
            tokenPos++; // Consume '('
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

    private void assignValueToLookupTable() { // this helper method assigns (and will handle errors) variables to values
        System.out.println("hi");
    }

// end of file
}
