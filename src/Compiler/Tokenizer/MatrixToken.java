package Compiler.Tokenizer;

import java.util.List;

// this is a necessary workaround for matrices
// storing tokens in such a list is useful because we're dealing with numbers and symbols
// this essentially avoids us using a second number inference method, it's already done in the tokenizer after all
public class MatrixToken extends Token {
    private final List<Token> entries;
    public MatrixToken(TokenKind kind, String value, List<Token> entries) { // the matrix token should be able to hold a list of tokens
        super(kind, value);
        this.entries = entries;
    }

    public List<Token> getEntries() {
        return entries;
    }
}
