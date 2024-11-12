package Compiler.Parser;

import AST.Expressions.BinaryOperationNode;
import AST.Expressions.ConstantNode;
import AST.Expressions.FunctionNode;
import AST.Expressions.VariableNode;
import AST.Nodes.ASTNode;
import Compiler.Tokenizer.TokenKind;
import Compiler.Tokenizer.Token;

import java.util.List;

// parsing going quite well so far, we need to add some error checking
// stuff like = signs not being followed by anything should crash the program
// all the trig functions need to be parsed correctly
// something very hard will now be to keep track of variables, we need a STACK to handle it somehow

public class Parser {

    private final List<Token> tokens;
    private int tokenPos = 0;

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
            return new VariableNode(token.toString(), TokenKind.VARIABLE);

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
                return new VariableNode(token.toString(), TokenKind.SYMBOL);
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

            // the below could be processed at once with a switch ? readability might not be the best
        } else if (token.getKind() == TokenKind.COS) { // also process all the rest
            tokenPos++;
            token = tokens.get(tokenPos);
            if (token.getKind() != TokenKind.OPEN_PAREN) {
                throw new RuntimeException("Expected open parenthesis after declaring cos");
            }
            System.out.println(token);
            tokenPos++; // consume "("
            token = tokens.get(tokenPos);
            ASTNode inParenContent = parseExpression();
            if (tokenPos < tokens.size() && tokens.get(tokenPos).getKind() == TokenKind.CLOSE_PAREN) {
                tokenPos++;
            } else {
                throw new RuntimeException("Expected matching closing parenthesis");
            }
            return new FunctionNode("cos", inParenContent); // this should return correct paren content

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

// end of file
}
