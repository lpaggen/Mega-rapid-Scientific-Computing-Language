package Lexer;

import Util.ErrorHandler;

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
        char c = peek(); // Peek at the current character

        System.out.println("Scanning character: " + c + " at line " + line); // Debug print

        switch (c) {
            case '(': addToken(TokenKind.OPEN_PAREN, "("); advance(); break;
            case ')': addToken(TokenKind.CLOSE_PAREN, ")"); advance(); break;
            case '{': addToken(TokenKind.OPEN_BRACE, "{"); advance(); break;
            case '}': addToken(TokenKind.CLOSE_BRACE, "}"); advance(); break;
            case '+':
                if (match('+')) { // check for '++' (increment operator)
                    addToken(TokenKind.INCREMENT, "++");
                } else {
                    addToken(TokenKind.PLUS, "+");
                }
                advance();
                break;
            case '-':
                if (match('>')) { // check for '->' (arrow operator)
                    addToken(TokenKind.ARROW, "->");
                } else if (match('-')) { // check for '--' (decrement operator)
                    addToken(TokenKind.DECREMENT, "--");
                } else {
                    addToken(TokenKind.MINUS, "-");
                }
                advance();
                break;
            case '*': addToken(TokenKind.MUL, "*"); advance(); break;
            case '/': addToken(TokenKind.DIV, "/"); advance(); break;
            case '^': addToken(TokenKind.POWER, "**"); advance(); break;
            case ';': addToken(TokenKind.SEMICOLON, ";"); advance(); break;
            case ',': addToken(TokenKind.COMMA, ","); advance(); break;
            case '%': addToken(TokenKind.MOD, "%"); advance(); break;
            case '@': addToken(TokenKind.AT, "@"); advance(); break;
            case '!':
                if (match('=')) {
                    addToken(TokenKind.NOT_EQUAL, "!=");
                } else {
                    addToken(TokenKind.NOT, "!");
                }
                advance(); // Advance after processing '!'
                break;
            case '<':
                if (match('=')) {
                    addToken(TokenKind.LESS_EQUAL, "<=");
                } else {
                    addToken(TokenKind.LESS, "<");
                }
                advance(); // Advance after processing '<'
                break;
            case '>':
                if (match('=')) {
                    addToken(TokenKind.GREATER_EQUAL, ">=");
                } else {
                    addToken(TokenKind.GREATER, ">");
                }
                advance(); // Advance after processing '>'
                break;
            case '=':
                if (match('=')) {
                    addToken(TokenKind.EQUAL_EQUAL, "==");
                    advance(); // Advance for the second '='
                } else {
                    addToken(TokenKind.EQUAL, "="); // we can hard code it, or we can set up a char.pos(i) something
                    advance(); // Advance for the single '='
                }
                break;
            case '#':
                while (peek() != '\n' && pos < input.length()) advance();
                break;
            case '$':
                addToken(TokenKind.RAW, "$"); advance(); break;
            case '[':
                addToken(TokenKind.OPEN_BRACKET, "["); advance(); break;
            case ']':
                addToken(TokenKind.CLOSE_BRACKET, "]"); advance(); break;
            case ':' :
                addToken(TokenKind.COLON, ":"); advance(); break;
            case '.':
                addToken(TokenKind.DOT, "."); advance(); break;
            case '\n':
                line++;
                advance();
                break;
            case '\r':
            case '\t':
            case ' ':
                advance(); // Ignore whitespace
                break;
            case '"':
                tokenizeString(); // tokenizeString handles its own advancing
                break;
            default:
                if (isDigit(c)) {
                    tokenizeNumber(); // tokenizeNumber handles its own advancing
                    break;
                } else if (isAlphaNumeric(c)) {
                    tokenizeKeyword(); // tokenizeKeyword handles its own advancing
                    break;
                }
                //throw new RuntimeException("Unexpected character: " + c + " at line " + line);
                throw new ErrorHandler("tokenization", line, "Unexpected character: '" + c + "'",
                        "Did you mean to use a different character?.");
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
        put("mat", TokenKind.MATRIX_TYPE);
        put("sym", TokenKind.MATH_TYPE);
        put("break", TokenKind.BREAK);
        put("continue", TokenKind.CONTINUE);
        put("wrt", TokenKind.WRT);
        put("null", TokenKind.NULL);
        put("bool", TokenKind.BOOLEAN_TYPE);
        put("str", TokenKind.STRING_TYPE);
        put("->", TokenKind.ARROW); // both for functions and for edges in graphs
        put("void", TokenKind.VOID_TYPE);
        put("load", TokenKind.INCLUDE);
        put("read", TokenKind.READ);
        put("write", TokenKind.WRITE);
        put("arr", TokenKind.ARRAY_TYPE);
        put("graph", TokenKind.GRAPH_TYPE);
        put("node", TokenKind.NODE_TYPE);
        put("edge", TokenKind.EDGE_TYPE);
        put("@", TokenKind.AT);
        put("list", TokenKind.LIST_TYPE);
        put("fn", TokenKind.FUNC_TYPE);
        put("lambda", TokenKind.LAMBDA);
        put("map", TokenKind.MAP);
    }};

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private void tokenizeNumber() {
        StringBuilder lexeme = new StringBuilder();
        boolean isDecimal = false;

        while (pos < input.length() && (isDigit(peek()) || peek() == '.')) {
            char currentChar = peek();
            if (currentChar == '.') {
                if (isDecimal) {
                    throw new ErrorHandler("tokenization", line, "Multiple decimal points in number.", "Caused by -> " + lexeme);
                }
                isDecimal = true;
            }
            lexeme.append(advance());
        }
        String numberStr = lexeme.toString();
        Object literal;
        if (isDecimal) { // we cannot use a ternary branch, it casts everything to Double for some reason
            literal = Double.parseDouble(numberStr);
        } else {
            literal = Integer.parseInt(numberStr);
        }
        TokenKind kind = isDecimal ? TokenKind.FLOAT : TokenKind.INTEGER; // determine if it's a float or an integer
        tokens.add(new Token(kind, lexeme.toString(), literal, line));
    }

    private void tokenizeKeyword() {
        StringBuilder lexeme = new StringBuilder();
        while (isAlphaNumeric(peek())) {
            lexeme.append(advance());
        }
        String keyword = lexeme.toString();
        TokenKind kind = keywords.get(keyword);
        if (kind == null) {
            kind = TokenKind.IDENTIFIER;
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
            throw new ErrorHandler("tokenization", line, "Unterminated string literal.",
                    "Did you forget to close the string with a double quote? Current lexeme: \"" + lexeme + "\"");
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

    private void addToken(TokenKind kind, String lexeme) {
        addToken(kind, lexeme, null); // made small change here, less confusing when debugging
    }

    // lexeme is the String representation of the token
    // literal is the actual value of the token
    // we need to find a way to fix this
    private void addToken(TokenKind kind, String lexeme, Object literal) {
        tokens.add(new Token(kind, lexeme, literal, line)); // have to figure out how to do this "lexeme" thing
    }

    private boolean match(char expected) {
        if (pos + 1 < input.length() && input.charAt(pos + 1) == expected) {
            advance(); // Consume the matching second character
            return true;
        }
        return false;
    }

    private char peek() {
        if (pos >= input.length()) return '\0';
        return input.charAt(pos);
    }
}
