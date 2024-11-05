package Compiler;

import java.util.ArrayList;
import java.util.List;

// here the goal is to write a tokenizer which is supposed to cast all tokens to their respective Expressions
// eg if i write "cos(x)", we want: new Cosine(x)
public class Tokenizer {

    private final String input;
    private int pos = 0; // !!! this gets updated at each character in the input file !!!
    private int tokenPos = 0; // !!! this only gets updated for each COMPLETE token !!!
    private final List<Token> tokens = new ArrayList<Token>();
    private boolean isPreviousTypeDeclaration = false;

    public Tokenizer(String input) {
        this.input = input;
    }

    // always takes a String, we want to parse
    // arguably we could write this entire thing in Regex, it is more scalable, although slower if poorly written
    // maybe in a future version
    public List<Token> tokenize() {

        // List<Token> tokens = new ArrayList<Token>();

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
            } else if (Character.toString(c).equals("=")) {
                tokens.add(new Token(TokenKind.EQUAL, "="));
                pos++;
            } else if (Character.toString(c).equals(";")) {
                tokens.add(new Token(TokenKind.SEMICOLON, ";")); // my interpreter will use semicolons for line separation
                pos++;
            } else if (Character.toString(c).equals(",")) {
                tokens.add(new Token(TokenKind.COMMA, ","));
                pos++;
            } else if (Character.isDigit(c)) {
                // need to handle decimals, but also implicit multiplication, also pos++ handled elsewhere
                tokens.add(tokenizeNumber()); // need to handle all cases where we have more than "9" for example
                if (pos < input.length() && Character.isLetter(input.charAt(pos))) {
                    tokens.add(new Token(TokenKind.MUL, "*"));
                }
            } else if (Character.isLetter(c)) {
                tokens.add(tokenizeFunctionOrVariable()); // have to handle this separately for all sin, cos etc.
            } else if (Character.isWhitespace(c)) {
                pos++; // unsure if I need to increment tokenPos here already or not
            }
        }
        tokens.add(new Token(TokenKind.EOF, null));
        tokenPos = tokens.size();
        return tokens;
    }

    private Token tokenizeFunctionOrVariable() {
        StringBuilder name = new StringBuilder();
        while (pos < input.length() && Character.isLetter(input.charAt(pos))) { // stop when encounter "(" for example
            name.append(input.charAt(pos));
            pos++;
        }
        String funcName = name.toString();
        if (isPreviousTypeDeclaration) { // handle type declaration with a boolean
            return tokenizeAccordingToPrevious();
        } else {
            return switch (funcName) {
                case "sin" -> new Token(TokenKind.SIN, "sin");
                case "cos" -> new Token(TokenKind.COS, "cos");
                case "tan" -> new Token(TokenKind.TAN, "tan");
                case "sec" -> new Token(TokenKind.SEC, "sec");
                case "log" -> new Token(TokenKind.LOG, "log");
                case "exp" -> new Token(TokenKind.EXP, "exp");
                case "cot" -> new Token(TokenKind.COT, "cot");
                case "csc" -> new Token(TokenKind.CSC, "csc");

                case "FUNC" -> new Token(TokenKind.FUNCTION, "FUNCTION_DECL"); // actually more complex than this

                case "DERIVE" -> new Token(TokenKind.DERIVE, "DERIVE");
                case "WRT" -> new Token(TokenKind.WRT, "WRT");

                case "SYMBOL" -> {
                    isPreviousTypeDeclaration = true;
                    yield new Token(TokenKind.SYMBOL_TYPE, "SYMBOL_TYPE"); // handle type declarations here
                }
                case "INTEGER" -> {
                    isPreviousTypeDeclaration = true;
                    yield new Token(TokenKind.INTEGER_TYPE, "INTEGER_TYPE");
                }
                case "FLOAT" -> {
                    isPreviousTypeDeclaration = true;
                    yield new Token(TokenKind.FLOAT_TYPE, "FLOAT_TYPE");
                }

                default -> new Token(TokenKind.VARIABLE, funcName);
            };
        }
    }

    private Token tokenizeAccordingToPrevious() { // or according to previous declaration or something, doesn't matter
        String varName = tokens.get(tokenPos).toString();
        isPreviousTypeDeclaration = false;
        return switch (varName) {
            case "symbol_type" -> new Token(TokenKind.SYMBOL, varName); // here it's going to be lowercase, tokenizer has already lowercased it
            case "integer_type" -> new Token(TokenKind.INTEGER, varName);
            case "float_type" -> new Token(TokenKind.FLOAT, varName);

            default -> throw new RuntimeException("Unexpected token: " + varName); // something is going wrong here
        };
    }

    // can handle floats and integers here
    private Token tokenizeNumber() {
        StringBuilder number = new StringBuilder();
        boolean isDecimal = false;
        while (pos < input.length() && (Character.isDigit(input.charAt(pos)) || input.charAt(pos) == '.')) {
            if (input.charAt(pos) == '.') {
                if (isDecimal) { // double decimal
                    throw new RuntimeException("Multiple decimal points in single float, check for a potential mistake...");
                }
                isDecimal = true;
            }
            number.append(input.charAt(pos));
            pos++;
        }
        tokenPos++;
        if (isDecimal) {return new Token(TokenKind.FLOAT, number.toString());} else {return new Token(TokenKind.INTEGER, number.toString());}
    }
}
