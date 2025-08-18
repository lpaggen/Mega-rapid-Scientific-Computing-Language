package AST.Nodes;

import Util.Environment;

// the MathSymbol datatype is unique to this language (afaik) - also Sympy uses it
// it allows for algebraic mathematical operations
// as such, 2a + a will return 3a
// !! symbol will handle int and float instead of being a separate data type
public class MathSymbol extends Expression {
    private String name; // name of a symbol can only be

    public MathSymbol(String name) {
        this.name = name;
    }

    private void validateName(String name) { // doesn't need to return anything
        if (!name.matches("[a-z]")) { // check regex for alphabet
            throw new IllegalArgumentException("MathSymbol name must be a single lowercase alphabetical character.");
        }
    }

    @Override
    public Expression evaluate(Environment env) {
        return null;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        validateName(name);
        this.name = name;
    }
}
