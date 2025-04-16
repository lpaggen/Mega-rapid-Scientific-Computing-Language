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
        if (pos >= input.length()) {
            return;
        }
        char c = peek();

        switch (c) {
            case '(':
                addToken(TokenKind.OPEN_PAREN);
                advance();
                break;
            case ')':
                addToken(TokenKind.CLOSE_PAREN);
                advance();
                break;
            case '+':
                addToken(TokenKind.PLUS);
                advance();
                break;
            case '-':
                addToken(TokenKind.MINUS);
                advance();
                break;
            case '*':
                addToken(TokenKind.MUL);
                advance();
                break;
            case '/':
                addToken(TokenKind.DIV); // our comment would be #
                advance();
                break;
            case '^':
                addToken(TokenKind.POWER);
                advance();
                break;
            case ';':
                addToken(TokenKind.SEMICOLON);
                advance();
                break;
            case ',':
                addToken(TokenKind.COMMA);
                advance();
                break;
            case '!':
                addToken(match('=') ? TokenKind.NOT_EQUAL : TokenKind.NOT);
                // advance();
                break;
            case '<':
                addToken(match('=') ? TokenKind.LESS_EQUAL : TokenKind.LESS);
                // advance();
                break;
            case '>':
                addToken(match('=') ? TokenKind.GREATER_EQUAL : TokenKind.GREATER);
                // advance();
                break;
            case '=':
                if (match('=')) { // somehow match is seeing a "=" it should not be seeing
                    addToken(TokenKind.EQUAL_EQUAL);
                } else {
                    addToken(TokenKind.EQUAL);
                }
                break;
            case '#': // this is the comment character, so consider all as whitespace
                while (peek() != '\n' && pos < input.length()) advance();
                break;
            case '[':
                tokenizeMatrix();
                break;
            case '\n':
                line++;
                break;
            case '\r':
            case '\t':
            case ' ':
                advance();
                break; // ignore whitespace
            case '"':
                tokenizeString();
                break;
            default:
                if (isDigit(c)) { // Check the current character 'c'
                    tokenizeNumber();
                    advance();
                    break;
                } else if (isAlphaNumeric(c)) {
                    tokenizeKeyword();
                    advance();
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
        put("bool", TokenKind.BOOLEAN_TYPE);
    }};

    // using Character.isDigit() would allow for all kinds of weird characters, so i'm using this instead
    private boolean isDigit(char c) { // this does work, except apparently it also allows for all kinds of weird characters
        return c >= '0' && c <= '9';
    }

    private void tokenizeNumber() {
        StringBuilder lexeme = new StringBuilder();
        boolean isDecimal = false;

        while (pos < input.length() && (isDigit(peek()) || peek() == '.')) {
            char currentChar = peek();
            if (currentChar == '.') {
                if (isDecimal) {
                    throw new RuntimeException("Multiple decimal points in single float, check for a potential mistake...");
                }
                isDecimal = true;
            }
            lexeme.append(advance());
        }
        tokens.add(new Token(TokenKind.NUM, lexeme.toString(), Double.parseDouble(lexeme.toString()), line));
    }

    // this method does not add to the tokens list, it just returns the token
    private Token tokenizeNumberReturnDouble() { // i scrapped the idea of using integers and floats here, rather just use NumericValue
        StringBuilder lexeme = new StringBuilder();
        boolean isDecimal = false;
        while (isDigit(peek()) || peek() == '.') {
            if (peek() == '.') {
                if (isDecimal) {
                    throw new RuntimeException("Multiple decimal points in single float, check for a potential mistake...");
                }
                isDecimal = true;
            }
            lexeme.append(advance());
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
        addToken(kind, keyword, null);
    }

    private void tokenizeString() {
        StringBuilder lexeme = new StringBuilder();
        advance(); // consume the opening quote
        while (peek() != '"' && pos < input.length()) {
            lexeme.append(advance());
        }
        if (peek() == '"') {
            advance(); // consume the closing quote
            addToken(TokenKind.STRING, lexeme.toString(), lexeme.toString()); // unsure if this works
        } else {
            throw new RuntimeException("Unterminated string at line " + line);
        }
    }

    private boolean isAlphaNumeric(char c) {
        return (c >= 'a' && c <= 'z') || c == '_' || (c >= 'A' && c <= 'Z') || isDigit(c);
    }

    private char advance() {
        if (pos >= input.length()) {
            // This should ideally not be reached if the while loop condition in tokenize() is correct
            throw new RuntimeException("Tried to advance beyond the end of the input.");
        }
        char c = input.charAt(pos);
        pos++;
        return c;
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
        pos++; // found the issue! was updating this position incorrectly
        if (pos >= input.length()) return false;
        if (input.charAt(pos) != expected) return false;
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
        System.out.println("Matrix tokenization...");
        int startPos = pos;
        int openBrackets = 0;
        int closeBrackets = 0;
        List<List<Token>> rows = new ArrayList<>();
        List<Token> currentRow = new ArrayList<>();

        // First pass: Extract matrix elements and check bracket balance
        while (pos < input.length()) {
            char c = peek();
            if (c == '[') {
                openBrackets++;
                advance();
            } else if (c == ']') {
                closeBrackets++;
                advance();
                rows.add(new ArrayList<>(currentRow)); // End of a row
                currentRow.clear();
            } else if (isDigit(c) || c == '.') {
                Token numberToken = tokenizeNumberReturnDouble();
                currentRow.add(numberToken);
            } else if (Character.isWhitespace(c)) {
                advance();
            } else if (c == ';') {
                advance(); // Move to the next element in the current row
            } else {
                throw new RuntimeException("Unexpected character in matrix: " + c + " at line " + line);
            }
            if (openBrackets > 0 && openBrackets == closeBrackets) {
                break; // End of the matrix
            }
            if (pos >= input.length() && openBrackets != closeBrackets) {
                throw new RuntimeException("Syntax error: Unclosed matrix at line " + line);
            }
        }

        if (openBrackets == 0 || closeBrackets == 0) {
            throw new RuntimeException("Syntax error: Malformed matrix, missing brackets at line " + line);
        }

        if (openBrackets != closeBrackets) {
            throw new RuntimeException("Syntax error: Mismatched brackets in matrix at line " + line);
        }

        if (rows.isEmpty()) {
            tokens.add(new MatrixToken(TokenKind.MATRIX, "0 0", null, line, new ArrayList<>()));
            return;
        }

        // Determine the number of columns and check for consistency
        int numRows = rows.size();
        int numCols = rows.get(0).size();
        for (List<Token> row : rows) {
            if (row.size() != numCols) {
                throw new RuntimeException("Syntax error: Inconsistent number of columns in matrix at line " + line);
            }
        }

        // Construct the matrix lexeme and literal
        StringBuilder matrixLexeme = new StringBuilder();
        matrixLexeme.append(numRows).append(" ").append(numCols);
        List<Token> matrixLiteralTokens = new ArrayList<>();
        for (List<Token> row : rows) {
            for (Token token : row) {
                matrixLexeme.append(" ").append(token.getLexeme());
                matrixLiteralTokens.add(token);
            }
        }

        tokens.add(new MatrixToken(TokenKind.MATRIX, matrixLexeme.toString(), null, line, matrixLiteralTokens));
    }
}