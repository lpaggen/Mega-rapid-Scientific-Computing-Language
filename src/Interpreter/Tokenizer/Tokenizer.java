package Interpreter.Tokenizer;

import java.util.*;

public class Tokenizer {

    private final String input;
    private int pos = 0; // !!! this gets updated at each character in the input file !!!
    private int line = 0; // this counts the lines in the input file, useful for reporting errors
    private final List<Token> tokens = new ArrayList<>();

    public Tokenizer(String input) {
        this.input = input;
    }

    public List<Token> tokenize() {
        while (pos < input.length()) {
            scanToken();
        }
        tokens.add(new Token(TokenKind.EOF, "", null, line));
        return tokens;
    }

    private void scanToken() {
        char c = advance();
        switch (c) {
            case '(':
                addToken(TokenKind.OPEN_PAREN); break;
            case ')':
                addToken(TokenKind.CLOSE_PAREN); break;
            case '+':
                addToken(TokenKind.PLUS); break;
            case '-':
                addToken(TokenKind.MINUS); break;
            case '*':
                addToken(TokenKind.MUL); break;
            case '/':
                addToken(TokenKind.DIV); break; // our comment would be #
            case '^':
                addToken(TokenKind.POWER); break;
            case ';':
                addToken(TokenKind.SEMICOLON); break;
            case ',':
                addToken(TokenKind.COMMA); break;
            case '!':
                addToken(match('=') ? TokenKind.NOT_EQUAL : TokenKind.NOT); break;
            case '<':
                addToken(match('=') ? TokenKind.LESS_EQUAL : TokenKind.LESS); break;
            case '>':
                addToken(match('=') ? TokenKind.GREATER_EQUAL : TokenKind.GREATER); break;
            case '=':
                addToken(match('=') ? TokenKind.EQUAL_EQUAL : TokenKind.EQUAL); break;
            case '#': // this is the comment character, so consider all as whitespace
                while (peek() != '\n' && pos < input.length()) advance(); break;
            case '[':
                tokenizeMatrix(); break;
            case '\n':
                line++; break;
            case '\r':
            case '\t':
            case ' ':
                break; // ignore whitespace
            case '"':
                tokenizeString(); break;
            default:
                if (isDigit(c)) {
                    tokenizeNumber();
                    break;
                } else if (isAlphaNumeric(c)) {
                    tokenizeKeyword();
                    break;
                }
                throw new RuntimeException("Unexpected character: " + c + " at line " + line);
        }
    }

    private final Map<String, TokenKind> keywords = new HashMap<>() {{
        put("sin", TokenKind.SIN);
        put("cos", TokenKind.COS);
        put("tan", TokenKind.TAN);
        put("sec", TokenKind.SEC);
        put("log", TokenKind.LOG);
        put("exp", TokenKind.EXP);
        put("cot", TokenKind.COT);
        put("csc", TokenKind.CSC);
        put("drv", TokenKind.DERIVE);
        put("fn", TokenKind.FUNC);
        put("or", TokenKind.OR);
        put("and", TokenKind.AND);
        put("true", TokenKind.TRUE);
        put("false", TokenKind.FALSE);
        put("if", TokenKind.IF);
        put("for", TokenKind.FOR);
        put("else", TokenKind.ELSE);
        put("while", TokenKind.WHILE);
        put("return", TokenKind.RETURN);
        put("int", TokenKind.INTEGER_TYPE);
        put("float", TokenKind.FLOAT_TYPE);
        put("matrix", TokenKind.MATRIX_TYPE);
        put("print", TokenKind.PRINT);
        put("break", TokenKind.BREAK);
        put("continue", TokenKind.CONTINUE);
        put("wrt", TokenKind.WRT);
        put("symbol", TokenKind.SYMBOL_TYPE);
        put("null", TokenKind.NULL);
    }};

    // using Character.isDigit() would allow for all kinds of weird characters, so i'm using this instead
    private boolean isDigit(char c) { // this does work, except apparently it also allows for all kinds of weird characters
        return c >= '0' && c <= '9';
    }

    private void tokenizeNumber() { // i scrapped the idea of using integers and floats here, rather just use NumericValue
        StringBuilder lexeme = new StringBuilder();
        boolean isDecimal = false;
        while (isDigit(peek())) {
            if (current() == '.') {
                if (isDecimal) {
                    throw new RuntimeException("Multiple decimal points in single float, check for a potential mistake...");
                }
                isDecimal = true;
            }
            lexeme.append(current());
            advance();
        }
        addToken(TokenKind.NUM, lexeme.toString(), Double.parseDouble(lexeme.toString()));
    }

    // this method does not add to the tokens list, it just returns the token
    private Token tokenizeNumberReturnDouble() { // i scrapped the idea of using integers and floats here, rather just use NumericValue
        StringBuilder lexeme = new StringBuilder();
        boolean isDecimal = false;
        while (isDigit(peek())) {
            if (current() == '.') {
                if (isDecimal) {
                    throw new RuntimeException("Multiple decimal points in single float, check for a potential mistake...");
                }
                isDecimal = true;
            }
            lexeme.append(current());
            advance();
        } // so it simply returns a token with the value of the number as .literal
        return new Token(TokenKind.NUM, lexeme.toString(), Double.parseDouble(lexeme.toString()), line);
    }

    private void tokenizeKeyword() {
        StringBuilder lexeme = new StringBuilder();
        while (isAlphaNumeric(peek())) {
            lexeme.append(advance());
        }
        String keyword = lexeme.toString();
        TokenKind kind = keywords.get(keyword);
        if (kind == null) {
            kind = TokenKind.VARIABLE;
        }
        addToken(kind);
    }

    private void tokenizeString() {
        StringBuilder lexeme = new StringBuilder();
        while (peek() != '"' && pos < input.length()) {
            lexeme.append(advance());
        }
        advance(); // consume the closing quote
        addToken(TokenKind.STRING, lexeme.toString(), lexeme.toString()); // unsure if this works
    }

    private boolean isAlphaNumeric(char c) {
        return (c >= 'a' && c <= 'z') || c == '_' || (c >= 'A' && c <= 'Z') || isDigit(c);
    }

    private char advance() {
        pos++;
        return input.charAt(pos);
    }

    private char current() {
        return input.charAt(pos);
    }

    private void addToken(TokenKind kind) {
        addToken(kind, kind.toString(), null);
    }

    // lexeme is the String representation of the token
    // literal is the actual value of the token
    // we need to find a way to fix this
    private void addToken(TokenKind kind, String lexeme, Object literal) {
        tokens.add(new Token(kind, lexeme, literal, line)); // have to figure out how to do this "lexeme" thing
    }

    // this method is for use within the matrix tokenization method only !!!
    private void addToken(TokenKind kind, String lexeme, Object literal, List<Token> whichList) {
        whichList.add(new Token(kind, lexeme, literal, line)); // have to figure out how to do this "lexeme" thing
    }

    private boolean match(char expected) {
        if (pos >= input.length()) return false;
        if (input.charAt(pos) != expected) return false;
        pos++;
        return true;
    }

    private char peek() {
        if (pos >= input.length()) return '\0';
        return input.charAt(pos);
    }

    // i don't see how to avoid nested loops to check correctness of the matrix + determine number of columns
    // maybe one day i will write a program that avoids nested loops
    // Feb 08 25 is not that day
    // hope this works
    // right now this constructs a String, which definitely is not ideal... -> i fixed this by using the MatrixToken class
    private void tokenizeMatrix() { // TO DO: handle errors regarding dimensions of matrices
        int lengthMatrix = -1; // the -1 accounts for the semicolon
        int openBrackets = 0; // this should be incremented as long as there are open brackets, we then match on closing brackets
        int closeBrackets = 0;
        int numCols = getNumCols(pos);
        int numRows = getNumRows(pos);
        StringBuilder matrixLexeme = new StringBuilder();
        List<Token> literal = new ArrayList<>(); // this is where all the actual tokens are stored for the matrix entries
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
            } else if (isDigit(c)) {
                Token Digit = tokenizeNumberReturnDouble();
                String lexemeOfNumber = Digit.getLexeme(); // digit.lexeme
                TokenKind kindOfNumber = Digit.getKind(); // digit.kind
                matrixLexeme.append(lexemeOfNumber); // !!! pos is incremented in this function too, might change at future stage
                matrixLexeme.append(' ');
                literal.add(new Token(kindOfNumber, lexemeOfNumber, Digit.getLiteral(), line));
//                if (pos < input.length() && Character.isLetter(input.charAt(pos))) { // some more checks will be needed here however
//                    literal.add(new Token(TokenKind.MUL)); // this is useful when there are coefficients involved
//                }
                lengthMatrix++;
            } else if (Character.isWhitespace(c)) {
                pos++;
            }
        }
        if (openBrackets != closeBrackets) {
            throw new RuntimeException("Syntax error: brackets mismatch in Matrix");
        } else if ((numCols * numRows) != (lengthMatrix - openBrackets - closeBrackets + 1))
            throw new RuntimeException("Syntax error: matrix dimensions do not match"); // i might rename this later
        matrixLexeme.insert(0, numCols + " ");
        matrixLexeme.insert(0, numRows + " ");
        tokens.add(new MatrixToken(TokenKind.MATRIX, matrixLexeme.toString(), null, line, literal)); // token is then implicit
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
