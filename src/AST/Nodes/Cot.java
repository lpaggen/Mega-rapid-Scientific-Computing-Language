package AST.Nodes;

import Interpreter.Runtime.Environment;

public class Cot extends Expression {
    private final Expression arg;

    public Cot(Expression arg) {
        this.arg = arg;
    }

    @Override
    public Object evaluate(Environment env) {
        Object argValue = arg.evaluate(env);
        if (!(argValue instanceof Number)) {
            throw new RuntimeException("Argument to cotangent must be a number, got: " + argValue.getClass());
        }
        return Math.cos((double) argValue) / Math.sin((double) argValue);
    }

    @Override
    public String toString() {
        return "cot(" + arg.toString() + ")";
    }

    public Expression getArg() {
        return arg;
    }
}
