package Compiler;

import AST.Constant;

import java.nio.BufferUnderflowException;
import java.util.ArrayList;
import java.util.List;

// huge bug fix needed, parsing in parentheses is very hard

public class Parser {

    private final List<Token> tokens;
    // public final ASTNode out;
    private int tokenPos = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public ASTNode interpretCode() {
        return parseExpression();
    }

    // we need 3 main methods along with a couple of helper methods to help parse our code
    // parseE, parseT, parseF

    // 1st implementation -> recursive descent LL(1)
    // try LR later ?

    // what we do is we let each function call the other recursively, it's complex but works
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
                ASTNode right = parseTerm();
                left = new BinaryOperationNode(left, right, nextToken);
            } else {
                break;
            }
        }
        return left;
    }

    private ASTNode parseFactor() { // this function makes the recursive calls to other handlers
        Token token = tokens.get(tokenPos);
        if (token.getKind() == TokenKind.INTEGER) {
            tokenPos++;
            return new ConstantNode(Integer.parseInt(token.getValue()));
        } else if (token.getKind() == TokenKind.FLOAT) {
            tokenPos++;
            return new ConstantNode(Float.parseFloat(token.getValue()));
        } else if (token.getKind() == TokenKind.SYMBOL_TYPE) { // check declaration type
            tokenPos++;
            token = tokens.get(tokenPos);
            if (token.getKind() == TokenKind.SYMBOL) {
                tokenPos++;
                return new VariableNode(token.toString(), TokenKind.SYMBOL);
            } else {
                throw new RuntimeException("Expected symbol after declaring symbol type");
            }
        } else if (token.getKind() == TokenKind.OPEN_PAREN) {
            tokenPos++; // consume (
            ASTNode inParenNode = parseExpression();
            if (token.getKind() == TokenKind.CLOSE_PAREN) { // REMEMBER tokenPos MOVES IN OTHER FUNCTIONS TOO
                tokenPos++;
            } else {
                throw new RuntimeException("Expected matching closed parentheses after opening parentheses");
            }
            return inParenNode;
        }
        throw new RuntimeException("Unexpected token: " + token.getValue());
    }

}
