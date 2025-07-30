package AST.Nodes;

import Util.Environment;

public class Constant extends MathExpression {
    public double value;

    public Constant(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public Object evaluate(Environment env) {
        return value;
    }

    @Override
    public String toString() {
        return value + "";
    }

    @Override
    public MathExpression derive(String var) {
        return new Constant(0);
    }

    @Override
    public MathExpression substitute(String... args) {
        throw new UnsupportedOperationException("Cannot substitute constants.");
    }
}
