package AST.Nodes;

import Interpreter.Runtime.Environment;

public class Sin extends Expression {
    private final Expression arg;

    public Sin(Expression arg) {
        this.arg = arg;
    }

    @Override
    public Object evaluate(Environment env) {
        Object argValue = arg.evaluate(env);
        if (!(argValue instanceof Number)) {
            throw new RuntimeException("Argument to sine must be a number, got: " + argValue.getClass());
        }
        return Math.sin((double) argValue);
    }

    @Override
    public String toString() {
        return "sin(" + arg.toString() + ")";
    }

    public Expression getArg() {
        return arg;
    }
}
