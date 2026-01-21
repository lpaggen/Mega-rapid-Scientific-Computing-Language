package Lexer;

// this is where all tokens are defined,
// so we can also (and should) define concepts like "derive", "newton", etc. for all optimization techniques we want to implement
public enum TokenKind {
    EOF,
    BOOLEAN,
    SCALAR,
    INTEGER,
    FLOAT,
    LIST,
    GRAPH,
    NODE,
    EDGE,
    GRAPH_TYPE,
    EDGE_TYPE,
    NODE_TYPE,
    LIST_TYPE,
    VECTOR,
    ARRAY,
    SYMBOL,
    NUM,
    STRING,
    NULL,
    VOID, // this is a special type that means "no return value" or "no value at all"
    IDENTIFIER,
    MATH, // for mathematical expressions
    AT,

    // some data structure tokens
    MATRIX,
    OPEN_BRACKET,
    CLOSE_BRACKET,

    FUNC,
    FUNC_TYPE,

    MATH_TYPE,
    INTEGER_TYPE,
    FLOAT_TYPE,
    MATRIX_TYPE,
    SYMBOL_TYPE,
    BOOLEAN_TYPE,
    STRING_TYPE,
    VECTOR_TYPE,
    ARRAY_TYPE,

    DERIVE,
    WRT, // say "with respect to ..."

    OPEN_PAREN,
    CLOSE_PAREN,
    OPEN_BRACE, // for blocks of code
    CLOSE_BRACE, // for blocks of code
    COMMA, // for multiple function arguments
    RAW, // $ symbol for raw numeric types (raw types CANNOT be used with Expression)

    PLUS,
    MINUS,
    DIV,
    MUL,
    MOD, // modulo operator
    POWER,
    INCREMENT,
    DECREMENT,

    EQUAL,
    GREATER,
    GREATER_EQUAL,
    LESS,
    LESS_EQUAL,
    NOT_EQUAL,
    NOT,
    EQUAL_EQUAL,

    WHITESPACE,
    SEMICOLON,
    VARIABLE,
    COLON,

    COS,
    SIN,
    TAN,
    EXP,
    LOG,
    SEC,
    CSC,
    COT,

    OR,
    AND,
    TRUE,
    FALSE,
    IF,
    ELSE,
    FOR,
    WHILE,
    BREAK,
    CONTINUE,
    RETURN, ARROW, VOID_TYPE,
    // PRINT,
    INCLUDE,
    READ,
    WRITE, ELSE_IF, DOT, SCALAR_TYPE, LAMBDA, MAP,
}
