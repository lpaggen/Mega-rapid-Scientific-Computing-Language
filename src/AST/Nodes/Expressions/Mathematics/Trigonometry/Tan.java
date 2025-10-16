package AST.Nodes.Expressions.Mathematics.Trigonometry;

import AST.Nodes.DataTypes.Scalar;
import AST.Nodes.Expressions.Expression;
import Interpreter.Runtime.Environment;

public class Tan extends Expression {
    private final Expression arg;

    public Tan(Expression arg) {
        this.arg = arg;
    }

    @Override
    public Expression evaluate(Environment env) {
        Expression argValue = arg.evaluate(env);
        if (argValue instanceof Scalar c) {
            return new Scalar(Math.tan(c.getDoubleValue()));
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
