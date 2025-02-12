package DataTypes;

import Compiler.Tokenizer.TokenKind;

import java.util.Objects;

// the Symbol datatype is unique to this language (afaik) - also Sympy uses it
// it allows for algebraic mathematical operations
// as such, 2a + a will return 3a
// !! symbol will handle int and float instead of being a separate data type
public class Symbol {
    private final String name; // name of a symbol can only be
    private Number value;
    private final TokenKind kind;

    // will consider adding vectors and matrices to the data structures
    public Symbol(String name, Number value, TokenKind kind) {
        validateName(name);
        this.name = name;
        this.value = value;
        this.kind = kind;
    }

    public String getName() {
        return name;
    }

    public Number getValue() {
        return value;
    }

    public TokenKind getKind() {
        return kind;
    }

    public void setValue(Number value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.name;
    } // probably unused

    // a Symbol must be defined by a single, lowercase letter (really can't imagine why you would need more than 26 symbols)
    // we do this because a Vector and a Matrix (both Matrix) are to be defined by capital letters
    // a Symbol must also be part of an expression, otherwise we are bound to run into issues
    // TO DO -> find a way to automatically cast into Term, then Expression
    private void validateName(String name) { // doesn't need to return anything
        if (!name.matches("[a-z]")) { // check regex for alphabet
            throw new IllegalArgumentException("Symbol name must be a single lowercase alphabetical character.");
        }
    }
}
