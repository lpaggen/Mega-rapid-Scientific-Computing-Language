package Compiler.Tokenizer;

import java.util.Objects;

public class Token { // can convert to record, unsure if i want to do that just yet

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

    public boolean equals(Token left, Token right) {
        return Objects.equals(left.getValue(), right.getValue());
    }

    public String toString() {
        return switch (kind) {
            case EOF -> "eof";
            case FLOAT -> "float";
            case FLOAT_TYPE -> "float_type";
            case INTEGER -> "integer";
            case INTEGER_TYPE -> "integer_type";
            case DERIVE -> "derive";
            // case SYMBOL -> "symbol"; // this is the symbol type
            // case SYMBOL_TYPE -> "symbol_type"; // this is the symbol type declarator
            case COS -> "cos";
            case COT -> "cot";
            case CSC -> "csc";
            case DIV -> "div";
            case EXP -> "exp";
            case LOG -> "log";
            case MUL -> "*";
            case PLUS -> "+";
            case MINUS -> "-";
            case EQUAL -> "=";
            case POWER -> "**";
            case SEC -> "sec";
            case SIN -> "sin";
            case TAN -> "tan";
            case WRT -> "wrt";
            case OPEN_PAREN -> "open_paren";
            case CLOSE_PAREN -> "close_paren";
            case WHITESPACE -> "whitespace";
            case VARIABLE -> "variable";
            case SEMICOLON -> "semicolon";
            case MATRIX_TYPE -> "matrix_type";
            case MATRIX -> "matrix";

            default -> throw new IllegalStateException("Unexpected value: " + kind);
        };
    }
}
