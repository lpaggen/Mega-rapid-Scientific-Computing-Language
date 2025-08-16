package AST.Nodes;

import Util.Environment;

public class Cos extends Expression {
    private final Expression arg;

    public Cos(Expression arg) {
        this.arg = arg;
    }

    @Override
    public double evaluate(Environment env) {
        return Math.cos(arg.evaluate(env));
    }

    @Override
    public String toString() {
        return "cos(" + arg.toString() + ")";
    }

    public Expression getArg() {
        return arg;
    }
}
