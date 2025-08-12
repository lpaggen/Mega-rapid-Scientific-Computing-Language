package AST.Nodes;

import Util.Environment;

public class NumericNode extends Expression {
    private final double value;

    public NumericNode(double value) {
        this.value = value;
    }

    public Expression simplify() {
        return null;
    }

    public Expression derive(String variable) {
        // constant evaluates to 0
        return new NumericNode(0);
    }

    public Expression substitute(String variable) {
        throw new UnsupportedOperationException("Cannot substitute a number for another number");
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }

    // this isn't really ideal, maybe this should just be an expression
    @Override
    public Expression evaluate(Environment env) {
        return value;
    }
}
