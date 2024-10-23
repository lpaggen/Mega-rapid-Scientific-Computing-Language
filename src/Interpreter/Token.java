package Interpreter;

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

    public String toString() {
        return switch (kind) {
            case EOF -> "eof";
            case FLOAT -> "float";
            case INTEGER -> "integer";
            case DERIVE -> "derive";
            case SYMBOL -> "symbol";
            case COS -> "cos";
            case COT -> "cot";
            case CSC -> "csc";
            case DIV -> "div";
            case EXP -> "exp";
            case LET -> "let";
            case LOG -> "log";
            case MUL -> "mul";
            case PLUS -> "plus";
            case MINUS -> "minus";
            case EQUAL -> "equal";
            case POWER -> "power";
            case SEC -> "sec";
            case SIN -> "sin";
            case TAN -> "tan";
            case WRT -> "wrt";
            case OPEN_PAREN -> "open_paren";
            case CLOSE_PAREN -> "close_paren";
            case WHITESPACE -> "whitespace";
            case VARIABLE -> "variable";
            case SEMICOLON -> "semicolon";

            default -> throw new IllegalStateException("Unexpected value: " + kind);
        };
    }
}
