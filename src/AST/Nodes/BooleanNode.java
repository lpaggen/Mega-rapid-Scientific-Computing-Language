package AST.Nodes;

import Util.Environment;

public class BooleanNode extends Expression {
    private final boolean value;

    public BooleanNode(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public Expression evaluate(Environment env) {
        return this;
    }

    @Override
    public String toString() {
        return value ? "true" : "false";
    }

    public Constant toNumeric() {
        return new Constant(value ? 1.0 : 0.0);
    }
}
