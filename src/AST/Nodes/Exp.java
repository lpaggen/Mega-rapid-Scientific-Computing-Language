package AST.Nodes;

import Interpreter.Runtime.Environment;

public class Exp extends Expression {
    private final Expression arg;

    public Exp(Expression arg) {
        this.arg = arg;
    }

    @Override
    public Object evaluate(Environment env) {
        Object argValue = arg.evaluate(env);
        if (!(argValue instanceof Number)) {
            throw new RuntimeException("Argument to exp must be a number, got: " + argValue.getClass());
        }
        return Math.exp((double) argValue);
    }

    @Override
    public String toString() {
        return "exp(" + arg.toString() + ")";
    }

    public Expression getArg() {
        return arg;
    }
}
