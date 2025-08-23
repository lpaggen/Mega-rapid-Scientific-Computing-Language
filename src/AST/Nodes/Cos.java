package AST.Nodes;

import Interpreter.Runtime.Environment;

public class Cos extends Expression {
    private final Expression arg;

    public Cos(Expression arg) {
        this.arg = arg;
    }

    @Override
    public Object evaluate(Environment env) {
        Object argValue = this.arg.evaluate(env);
        if (!(argValue instanceof Number)) {
            throw new RuntimeException("Argument to cosine must be a number, got: " + arg.getClass());
        }
        return Math.cos((double) argValue);
    }

    @Override
    public String toString() {
        return "cos(" + arg.toString() + ")";
    }

    public Expression getArg() {
        return arg;
    }
}
