package AST.Nodes;

import AST.Nodes.DataTypes.Constant;
import AST.Nodes.DataTypes.FloatConstant;
import Interpreter.Runtime.Environment;

public class Tan extends Expression {
    private final Expression arg;

    public Tan(Expression arg) {
        this.arg = arg;
    }

    @Override
    public Expression evaluate(Environment env) {
        Expression argValue = arg.evaluate(env);
        if (argValue instanceof Constant c) {
            return new FloatConstant(Math.tan(c.getDoubleValue()), c.isRaw());
        }
        return new Tan(argValue);
    }

    public double evaluateNumeric(Environment env) {
        double argValue = arg.evaluateNumeric(env);
        return Math.tan(argValue);
    }

    @Override
    public String toString() {
        return "tan(" + arg.toString() + ")";
    }

    public Expression getArg() {
        return arg;
    }
}
