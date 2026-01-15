package AST.Literals;

import AST.Expressions.Expression;

public final class ScalarLiteralNode extends Expression {  // all this is just a wrapper around Number
    private final Number value;

    public ScalarLiteralNode(Number value) {
        this.value = value;
    }

    public Number getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
