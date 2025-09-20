package AST.Nodes;

import AST.Nodes.DataTypes.Constant;
import AST.Nodes.DataTypes.FloatConstant;
import Interpreter.Runtime.Environment;

public class Sin extends Expression {
    private final Expression arg;

    public Sin(Expression arg) {
        this.arg = arg;
    }

    @Override
    public Expression evaluate(Environment env) {
        Expression argValue = arg.evaluate(env);
        if (argValue instanceof Constant c) {
            return new FloatConstant(Math.sin(c.getDoubleValue()), c.isRaw());
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
