package AST.Nodes;

import Interpreter.Runtime.Environment;

public class Pow extends Expression {

    private final Expression arg, exponent;

    public Pow(Expression arg, Expression exponent) {
        this.arg = arg;
        this.exponent = exponent;
    }

    // there must be a fix here in that the degree is not always numeric, it can be algebrai

    @Override
    public Object evaluate(Environment env) {
        Object argValue = arg.evaluate(env);
        Object exponentValue = exponent.evaluate(env);
        if (!(argValue instanceof Number)) {
            throw new RuntimeException("Base of power must be a number, got: " + argValue.getClass());
        } else if (!(exponentValue instanceof Number)) {
            throw new RuntimeException("Exponent of power must be a number, got: " + exponentValue.getClass());
        }
        return Math.pow((double) argValue, (double) exponentValue);
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
