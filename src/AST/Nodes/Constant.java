package AST.Nodes;

import Interpreter.Runtime.Environment;

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
}
