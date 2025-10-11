package AST.Nodes.Expressions.Mathematics.Trigonometry;

import AST.Nodes.DataTypes.Constant;
import AST.Nodes.DataTypes.FloatConstant;
import AST.Nodes.Expressions.Expression;
import Interpreter.Runtime.Environment;

public class Csc extends Expression {

    private final Expression arg;

    public Csc(Expression arg) {
        this.arg = arg;
    }

    @Override
    public Expression evaluate(Environment env) {
        Expression argValue = arg.evaluate(env);
        if (argValue instanceof Constant c) {
            return new FloatConstant(1 / Math.sin(c.getDoubleValue()));
        }
        return new Csc(argValue);
    }

    public double evaluateNumeric(Environment env) {
        double argValue = arg.evaluateNumeric(env);
        return 1 / Math.sin(argValue);
    }

    @Override
    public String toString() {
        return "csc(" + arg.toString() + ")";
    }

    public Expression getArg() {
        return arg;
    }
}
