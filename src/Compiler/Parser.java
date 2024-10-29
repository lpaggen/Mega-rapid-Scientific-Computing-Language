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
    private int nextTokenPos = 0; // clearer than say pos++

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    // we need 3 main methods along with a couple of helper methods to help parse our code
    // parseE, parseT, parseF

    // 1st implementation -> recursive descent LL(1)
    // try LR later ?
    private ASTNode parseExpression(List<Token> tokens) {
        // here we need to handle all EXPRESSIONS -> +, -, so all binary nodes
        // currently we hava a strange way of handling the negations, need to address the issue
        while (true) {
            Token nextToken = parseTerm(tokens.get(nextTokenPos));

            // handle all the cases of expressions now
            if (nextToken.getKind() == TokenKind.PLUS) { // handle additions
                return new BinaryOperationNode(left, TokenKind.PLUS, right); // we need to fix these types
            } else if (nextToken.getKind() == TokenKind.MINUS) {
                return new BinaryOperationNode(left, TokenKind.MINUS, right); // also fix here
            }
        }
    }

    private ASTNode parseFactor(List<Token> tokens) {

    }

    private Token parseTerm(Token token) {

    }
}
