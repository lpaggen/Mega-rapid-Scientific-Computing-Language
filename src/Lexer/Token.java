package Lexer;

public class Token { // can convert to record, unsure if i want to do that just yet

    private final TokenKind kind;
    private final Object literal;
    private final String lexeme;
    private final int line;

    // i adapted the Token class to support info for debugging, also this makes parsing easier
    public Token(TokenKind kind, String lexeme, Object literal, int line) {
        this.kind = kind;
        this.literal = literal;
        this.line = line;
        this.lexeme = lexeme;
    }

    public int getLine() {
        return line;
    }

    public String toString() {
        return kind + " " + lexeme + " " + literal;
    }

    public TokenKind getKind() {
        return kind;
    }

    public Object getLiteral() {
        return literal;
    }

    public String getLexeme() {
        return lexeme;
    }
}
