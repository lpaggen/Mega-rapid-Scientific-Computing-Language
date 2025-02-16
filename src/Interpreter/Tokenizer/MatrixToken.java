package Interpreter.Tokenizer;

import java.util.List;

// this is a necessary workaround for matrices
// storing tokens in such a list is useful because we're dealing with numbers and symbols
// this essentially avoids us using a second number inference method, it's already done in the tokenizer after all
public class MatrixToken extends Token {
    private final List<Token> entries;
    public MatrixToken(TokenKind kind, String lexeme, Object literal, int line, List<Token> entries) { // the matrix token should be able to hold a list of tokens
        super(kind, lexeme, literal, line);
        this.entries = entries;
    }

    public List<Token> getEntries() {
        return entries;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
