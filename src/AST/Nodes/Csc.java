package AST.Nodes;

import Util.Environment;

public class Csc extends Expression {

    private final Expression arg;

    public Csc(Expression arg) {
        this.arg = arg;
    }

    @Override
    public Object evaluate(Environment env) {
        Object argValue = arg.evaluate(env);
        if (!(argValue instanceof Number)) {
            throw new RuntimeException("Argument to cosecant must be a number, got: " + argValue.getClass());
        }
        return 1 / Math.sin((double) argValue);
    }

    @Override
    public String toString() {
        return "csc(" + arg.toString() + ")";
    }

    public Expression getArg() {
        return arg;
    }
}
