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
        return this;
    }

    @Override
    public String toString() {
        return STR."\{value}";
    }
}
