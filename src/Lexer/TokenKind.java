package Lexer;

// this is where all tokens are defined,
// so we can also (and should) define concepts like "derive", "newton", etc. for all optimization techniques we want to implement
public enum TokenKind {
    EOF,
    NUMBER,

    DERIVE,
    LET, // we might need a few traditional language operators
    WRT, // say "with respect to ..."

    OPEN_PAREN,
    CLOSE_PAREN,

    PLUS,
    MINUS,
    DIV,
    MUL,
    POWER,
    EQUAL,
    WHITESPACE,

    COS,
    SIN,
    TAN,
    EXP,
    LOG,
    SEC,
    CSC,
    COT,
}
