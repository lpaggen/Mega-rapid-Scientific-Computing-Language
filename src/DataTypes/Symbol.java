package DataTypes;

import java.util.Objects;

// the Symbol datatype is unique to this language (afaik)
// it allows for algebraic mathematical operations
// as such, 2a + a will return 3a
// symbols do not have a value, i will implement this at a later stage for evaluation purposes
public class Symbol {
    private final String name;

    // will consider adding vectors and matrices to the data structures
    public Symbol(String name) {
        validateName(name); // moved outside constructor
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    // a Symbol must be defined by a single, lowercase letter (really can't imagine why you would need more than 26 symbols)
    // we do this because a Vector and a Matrix (both Matrix) are to be defined by capital letters
    private void validateName(String name) { // doesn't need to return anything
        if (!name.matches("[a-z]")) { // check regex for alphabet
            throw new IllegalArgumentException("Symbol name must be a single lowercase alphabetical character.");
        }
    }
}
