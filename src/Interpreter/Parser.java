package Interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

// huge bug fix needed, parsing in parentheses is very hard

public class Parser {

    private final List<Token> tokens;
    // public final ASTNode out;
    private int pos = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    // this is inherently wrong, should be a while loop until done really...
    // or something similar in purpose, parsing each line separately until I throw in loops
    public String parseCode(List<Token> tokens) {
        while (pos < tokens.size()) {
            Token token = tokens.get(pos);
            if (token.getKind() == TokenKind.SIN || token.getKind() == TokenKind.COS || token.getKind() == TokenKind.TAN || token.getKind() == TokenKind.COT ||
                    token.getKind() == TokenKind.CSC || token.getKind() == TokenKind.EXP || token.getKind() == TokenKind.LOG) {
                FunctionNode functionNode = functionHandler(tokens);
            }
        }

        ASTNode out = parseExpression(tokens);
        return out.toString();
    }

    private FunctionNode functionHandler(List<Token> tokens) {
        int pos = 0;
        while (pos < tokens.size()) {
            Token token = tokens.get(pos);
            String functionName = token.getValue();
            if (tokens.get(pos++).getKind() != TokenKind.OPEN_PAREN) { // check if parenthesis opens, eg cos(x) != cosx
                throw new RuntimeException("Expected open parenthesis after function declaration");
            }
            List<Token> inParenTokens = new ArrayList<>();
            while (tokens.get(pos).getKind() != TokenKind.CLOSE_PAREN) {
                token = tokens.get(pos++);
                inParenTokens.add(token);
            }
            pos++; // consume CLOSE_PAREN
            ASTNode inParenNodes = functionHandler(inParenTokens);
            return new FunctionNode(functionName, inParenNodes);
        }
        return null;
    }
}
