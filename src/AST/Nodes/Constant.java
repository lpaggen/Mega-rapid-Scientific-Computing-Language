package AST.Nodes;

import Util.Environment;
import Util.WarningHandler;

public class Constant extends Expression {
    public double value;

    public Constant(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public Expression evaluate(Environment env) {
        throw new UnsupportedOperationException("Constants cannot be evaluated in the environment.");
    }

    @Override
    public String toString() {
        return STR."\{value}";
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
