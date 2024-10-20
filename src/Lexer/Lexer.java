package Lexer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// here the goal is to write a tokenizer which is supposed to cast all tokens to their respective Expressions
// eg if i write "cos(x)", we want: new Cosine(x)
public class Lexer {

    private final String input;
    private int pos = 0;

    public Lexer(String input) {
        this.input = input;
    }

    // always takes a String, we want to parse
    public List<Token> tokenize() {

        List<Token> tokens = new ArrayList<Token>();

        while (pos < input.length()) {
            char c = input.charAt(pos);

            if (Character.toString(c).equals("(")) {
                tokens.add(new Token(TokenKind.OPEN_PAREN, "("));
                pos++;
            } else if (Character.toString(c).equals(")")) {
                tokens.add(new Token(TokenKind.CLOSE_PAREN, ")"));
                pos++;
            } else if (Character.toString(c).equals("+")) {
                tokens.add(new Token(TokenKind.PLUS, "+"));
                pos++;
            } else if (Character.toString(c).equals("-")) {
                tokens.add(new Token(TokenKind.MINUS, "-"));
                pos++;
            } else if (Character.toString(c).equals("*")) {
                tokens.add(new Token(TokenKind.MUL, "*"));
                pos++;
            } else if (Character.toString(c).equals("/")) {
                tokens.add(new Token(TokenKind.DIV, "/"));
                pos++;
            } else if (Character.toString(c).equals("^")) {
                tokens.add(new Token(TokenKind.POWER, "^"));
                pos++;
            } else if (Character.isDigit(c)) {
                tokens.add(tokenizeNumber()); // need to handle all cases where we have more than "9" for example
                pos++;
            } else if (Character.isLetter(c)) {
                tokens.add(tokenizeFunctionOrVariable()); // have to handle this separately for all sin, cos etc.
                pos++;
            }
        }

        tokens.add(new Token(TokenKind.EOF, null));
        return tokens;

    }

    private Token tokenizeFunctionOrVariable() {
        StringBuilder name = new StringBuilder();
        while (pos < input.length() && Character.isLetter(input.charAt(pos))) { // stop when encounter "(" for example
            name.append(input.charAt(pos));
            pos++;
        }
        String funcName = name.toString();
        return switch (funcName) {
            case "sin" -> new Token(TokenKind.SIN, "sin");
            case "cos" -> new Token(TokenKind.COS, "cos");
            case "tan" -> new Token(TokenKind.TAN, "tan");
            case "sec" -> new Token(TokenKind.SEC, "sec");
            case "log" -> new Token(TokenKind.LOG, "log");
            case "exp" -> new Token(TokenKind.EXP, "exp");
            case "cot" -> new Token(TokenKind.COT, "cot");
            case "csc" -> new Token(TokenKind.CSC, "csc");

            case "DERIVE" -> new Token(TokenKind.DERIVE, "DERIVE");
            case "WRT" -> new Token(TokenKind.WRT, "WRT");
            case "LET" -> new Token(TokenKind.LET, "LET");

            default -> throw new IllegalArgumentException("Unknown function: " + funcName);
        };
    }

    // not sure if this will actually work as intended
    private Token tokenizeNumber() {
        StringBuilder number = new StringBuilder();
        while (pos < input.length() && Character.isDigit(input.charAt(pos))) {
            number.append(input.charAt(pos));
            pos++;
        }
        return new Token(TokenKind.NUMBER, number.toString());
    }
}
