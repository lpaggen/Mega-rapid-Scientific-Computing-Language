package Interpreter;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    private final List<Token> tokens;
    private int pos = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public String parseCode(List<Token> tokens) {
        ASTNode out = parseExpression(tokens);
        return out.toString();
    }

    // this one is just a helper method to get everything inside a parenthesis
    private ASTNode parseParenContent() {
        List<Token> tokensInParen = new ArrayList<>();
        Token token = tokens.get(pos);
        while (token.getKind() != TokenKind.CLOSE_PAREN && pos < tokens.size()) { // go over all tokens until encounter )
            if (token.getKind() != TokenKind.WHITESPACE) {
                tokensInParen.add(token);
                pos++;
                token = tokens.get(pos);
            }
        }
        pos++; // consume )
        return parseExpression(tokensInParen);
    }

    private ASTNode parseExpression(List<Token> tokens) { // this name is bad
        Token token = tokens.get(pos);
        if (token.getKind() == TokenKind.SIN || token.getKind() == TokenKind.COS || token.getKind() == TokenKind.TAN || token.getKind() == TokenKind.COT ||
            token.getKind() == TokenKind.CSC || token.getKind() == TokenKind.EXP || token.getKind() == TokenKind.LOG) {
            pos++;
            String functionName = token.getValue();
            if (token.getKind() == TokenKind.OPEN_PAREN) {
                pos++; // consume (
                ASTNode argument = parseParenContent(); // this is a helper method, seems complex but really isn't
                return new FunctionNode(functionName, argument); // create the AST node for a function
            } else {
                throw new RuntimeException("Expected open parenthesis after function declaration");
            }
        } else if (token.getKind() == TokenKind.INTEGER || token.getKind() == TokenKind.FLOAT ||token.getKind() == TokenKind.VARIABLE) { // handle other tokens maybe
            pos++;
            return new VariableNode(token.getValue()); // might want to rename to something else, we will see
        }
        throw new RuntimeException("Unexpected token: " + token.getKind());
    }
}
