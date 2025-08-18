package AST.Nodes;

import Util.Environment;

public class Tan extends Expression {
    private final Expression arg;

    public Tan(Expression arg) {
        this.arg = arg;
    }

    @Override
    public Object evaluate(Environment env) {
        Object argValue = arg.evaluate(env);
        if (!(argValue instanceof Number)) {
            throw new RuntimeException("Argument to tangent must be a number, got: " + argValue.getClass());
        }
        return Math.tan((double) argValue);
    }

    @Override
    public String toString() {
        return "tan(" + arg.toString() + ")";
    }

    public Expression getArg() {
        return arg;
    }
}
