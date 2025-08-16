package AST.Nodes;

import Util.Environment;

public class Csc extends Expression {

    private final Expression arg;

    public Csc(Expression arg) {
        this.arg = arg;
    }

    @Override
    public double evaluate(Environment env) {
        return 1 / Math.sin(arg.evaluate(env));
    }

    @Override
    public String toString() {
        return "csc(" + arg.toString() + ")";
    }

    public Expression getArg() {
        return arg;
    }
}
