package Lexer;

public class Token {

    private final TokenKind kind;
    private final String value;

    public Token(TokenKind kind, String value) {
        this.kind = kind;
        this.value = value;
    }

    public TokenKind getKind() {
        return kind;
    }

    public String getValue() {
        return value;
    }

}
