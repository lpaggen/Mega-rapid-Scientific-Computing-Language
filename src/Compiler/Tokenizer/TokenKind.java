package Compiler.Tokenizer;

// this is where all tokens are defined,
// so we can also (and should) define concepts like "derive", "newton", etc. for all optimization techniques we want to implement
public enum TokenKind {
    EOF,
    CONST,
    INTEGER,
    FLOAT,
    // SYMBOL,

    // some data structure tokens
    MATRIX,
    OPEN_BRACKET,
    CLOSE_BRACKET,

    FUNCTION,

    // separate all type declarations
    // SYMBOL_TYPE,
    INTEGER_TYPE,
    FLOAT_TYPE,
    MATRIX_TYPE,

    DERIVE,
    WRT, // say "with respect to ..."

    OPEN_PAREN,
    CLOSE_PAREN,
    COMMA, // for multiple function arguments

    PLUS,
    MINUS,
    DIV,
    MUL,
    POWER,
    EQUAL,
    WHITESPACE,
    SEMICOLON,
    VARIABLE,

    COS,
    SIN,
    TAN,
    EXP,
    LOG,
    SEC,
    CSC,
    COT,
}
