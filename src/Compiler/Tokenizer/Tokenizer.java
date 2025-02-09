package Compiler.Tokenizer;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

    private final String input;
    private int pos = 0; // !!! this gets updated at each character in the input file !!!
    private int tokenPos = 0; // !!! this only gets updated for each COMPLETE token !!!
    private final List<Token> tokens = new ArrayList<>();

    public Tokenizer(String input) {
        this.input = input;
    }

    // always takes a String, we want to parse
    // arguably we could write this entire thing in Regex, it is more scalable, although slower if poorly written
    // maybe in a future version
    public List<Token> tokenize() {

        while (pos < input.length()) {
            char c = input.charAt(pos);

            // we don't need toString for Character, can just equate to '' -> change in future release
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
            } else if (Character.toString(c).equals("[")) {
                tokenizeMatrix(tokens); // tokenizing matrices will happen in a helper function
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

            // case "SYMBOL" -> {
            //     isPreviousTypeDeclaration = true;
            //     previousTypeDeclaration = TokenKind.SYMBOL_TYPE;
            //     yield new Token(TokenKind.SYMBOL_TYPE, "SYMBOL_TYPE"); // handle type declarations here
            // }
            case "INTEGER" -> new Token(TokenKind.INTEGER_TYPE, "INTEGER_TYPE");
            case "FLOAT" -> new Token(TokenKind.FLOAT_TYPE, "FLOAT_TYPE");
            case "MATRIX" -> new Token(TokenKind.MATRIX_TYPE, "MATRIX_TYPE");

            // these non-standard types should be handled in the parser instead
            default -> new Token(TokenKind.VARIABLE, funcName);
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

    // i could also have this return some "MATRIX" token with a Value consisting of the tokens...
    // i will need to think about what makes the most sense for my application, we have to loop over all tokens twice anyway (parser + tokenizer)
    // also i could definitely change the way things are tokenized here, i could use some prefix for dimensions, avoiding brackets entirely
//    private void tokenizeMatrix(List<Token> tokens) {
//        int openBrackets = 0; // this should be incremented as long as there are open brackets, we then match on closing brackets
//        int closeBrackets = 0;
//        while (pos < input.length() - 1) {
//            char c = input.charAt(pos);
//            if (Character.toString(c).equals("[")) {
//                tokens.add(new Token(TokenKind.OPEN_BRACKET, "["));
//                openBrackets++;
//                pos++;
//            } else if (Character.toString(c).equals("]")) {
//                tokens.add(new Token(TokenKind.CLOSE_BRACKET, "]"));
//                closeBrackets++;
//                pos++;
//            } else if (Character.isDigit(c)) {
//                tokens.add(tokenizeNumber()); // !!! pos is incremented in this function too, might change at future stage
//                if (pos < input.length() && Character.isLetter(input.charAt(pos))) { // some more checks will be needed here however
//                    tokens.add(new Token(TokenKind.MUL, "*")); // this is useful when there are coefficients involved
//                }
//            } else if (Character.isWhitespace(c)) {
//                pos++;
//            }
//        }
//        if (openBrackets != closeBrackets) {
//            throw new RuntimeException("Syntax error: brackets mismatch in Matrix");
//        }
//    }

    // i don't see how to avoid nested loops to check correctness of the matrix + determine number of columns
    // maybe one day i will write a program that avoids nested loops
    // Feb 08 25 is not that day
    // hope this works
    // right now this constructs a String, which definitely is not ideal... -> i fixed this by using the MatrixToken class
    private void tokenizeMatrix(List<Token> tokens) { // TO DO: handle errors regarding dimensions of matrices
        int lengthMatrix = -1; // the -1 accounts for the semicolon
        int openBrackets = 0; // this should be incremented as long as there are open brackets, we then match on closing brackets
        int closeBrackets = 0;
        int numCols = getNumCols(pos);
        int numRows = getNumRows(pos);
        StringBuilder matrixContent = new StringBuilder();
        List<Token> matrixTokens = new ArrayList<>(); // this is where all the actual tokens are stored for the matrix entries
        while (pos < input.length() - 1) {
            char c = input.charAt(pos);
            if (c == '[') {
                openBrackets++;
                pos++;
                lengthMatrix++;
            } else if (c == ']') {
                closeBrackets++;
                pos++;
                lengthMatrix++;
            } else if (Character.isDigit(c)) { // when we encounter a digit, we need to start counting cols + keep track of validity in dim
                Token Digit = tokenizeNumber();
                String valueOfNumber = Digit.getValue();
                TokenKind kindOfNumber = Digit.getKind();
                matrixContent.append(valueOfNumber); // !!! pos is incremented in this function too, might change at future stage
                matrixContent.append(' ');
                matrixTokens.add(new Token(kindOfNumber, valueOfNumber));
                if (pos < input.length() && Character.isLetter(input.charAt(pos))) { // some more checks will be needed here however
                    matrixContent.append(new Token(TokenKind.MUL, "*")); // this is useful when there are coefficients involved
                }
                lengthMatrix++;
            } else if (Character.isWhitespace(c)) {
                pos++;
            }
        }
        if (openBrackets != closeBrackets) {
            throw new RuntimeException("Syntax error: brackets mismatch in Matrix");
        } else if ((numCols * numRows) != (lengthMatrix - openBrackets - closeBrackets + 1))
            throw new RuntimeException("Syntax error: matrix dimensions do not match"); // i might rename this later
        matrixContent.insert(0, numCols + " ");
        matrixContent.insert(0, numRows + " ");
        tokens.add(new MatrixToken(TokenKind.MATRIX, matrixContent.toString(), matrixTokens)); // token is then implicit
        System.out.println(matrixContent);
    }

    private int getNumCols(int pos) { // doesn't work atm, why ?
        int numCols = 0;
        while (pos < input.length() && input.charAt(pos) != ']') {
            if (Character.isDigit(input.charAt(pos))) {
                numCols++;
            }
            pos++;
        }
        return numCols;
    }

    private int getNumRows(int pos) {
        int numRows = 0;
        boolean lastWasBracket = false;
        while (pos < input.length()) {
            char current = input.charAt(pos);
            if (current == ']' && !lastWasBracket) {
                numRows++;
                lastWasBracket = true; // flag when we encounter bracket
            } else if (current != ']') {
                lastWasBracket = false;  // Reset flag if we encounter another character
            }
            pos++;
        }
        return numRows;
    }
}
