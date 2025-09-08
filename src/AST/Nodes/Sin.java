package AST.Nodes;

import Interpreter.Runtime.Environment;
import Interpreter.Tokenizer.TokenKind;

public class Sin extends Expression {
    private final Expression arg;

    public Sin(Expression arg) {
        this.arg = arg;
    }

    @Override
    public Expression evaluate(Environment env) {
        Expression argValue = arg.evaluate(env);
        if (argValue instanceof Constant c) {
            return new Constant(Math.sin(c.getDoubleValue()), c.isRaw());
        }
        return new Sin(argValue);
    }

    public double evaluateNumeric(Environment env) {
        double argValue = arg.evaluateNumeric(env);
        return Math.sin(argValue);
    }

    @Override
    public String toString() {
        return "sin(" + arg.toString() + ")";
    }

    public Expression getArg() {
        return arg;
    }
}
