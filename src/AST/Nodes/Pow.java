package AST.Nodes;

import Util.Environment;

public class Pow extends Expression {

    private final Expression arg, exponent;

    public Pow(Expression arg, Expression exponent) {
        this.arg = arg;
        this.exponent = exponent;
    }

    // there must be a fix here in that the degree is not always numeric, it can be algebrai

    @Override
    public double evaluate(Environment env) {
        return Math.pow(arg.evaluate(env), exponent.evaluate(env));
    }

    @Override
    public String toString() {
        return arg.toString() + "^" + exponent.toString();
    }

    public Expression getBase() {
        return arg;
    }

    public Expression getExponent() {
        return exponent;
    }
}
