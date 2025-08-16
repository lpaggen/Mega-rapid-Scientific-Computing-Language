package AST.Nodes;

import Util.Environment;

public class Exp extends Expression {
    private final Expression arg;

    public Exp(Expression arg) {
        this.arg = arg;
    }

    @Override
    public double evaluate(Environment env) {
        return Math.exp(arg.evaluate(env));
    }

    @Override
    public String toString() {
        return "exp(" + arg.toString() + ")";
    }

    public Expression getArg() {
        return arg;
    }
}
