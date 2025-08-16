package AST.Nodes;

import Util.Environment;

public class Tan extends Expression {
    private final Expression arg;

    public Tan(Expression arg) {
        this.arg = arg;
    }

    @Override
    public double evaluate(Environment env) {
        return Math.tan(arg.evaluate(env));
    }

    @Override
    public String toString() {
        return "tan(" + arg.toString() + ")";
    }

    public Expression getArg() {
        return arg;
    }
}
