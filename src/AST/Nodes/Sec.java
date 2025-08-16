package AST.Nodes;

import Util.Environment;

public class Sec extends Expression {

    private final Expression arg;

    public Sec(Expression arg) {
        this.arg = arg;
    }

    @Override
    public double evaluate(Environment env) {
        return 1 / Math.cos((double) arg.evaluate(env));
    }

    @Override
    public String toString() {
        return "sec(" + arg.toString() + ")";
    }

    public Expression getArg() {
        return arg;
    }
}
