package AST.Nodes;

import AST.Nodes.DataTypes.Constant;
import AST.Nodes.DataTypes.FloatConstant;
import Interpreter.Runtime.Environment;

public class Exp extends Expression {
    private final Expression arg;

    public Exp(Expression arg) {
        this.arg = arg;
    }

    @Override
    public double evaluateNumeric(Environment env) {
        double argValue = arg.evaluateNumeric(env);
        return Math.exp(argValue);
    }

    // so this is how we make the distinction between numeric or not, numeric are now wrapped in Constant
    public Expression evaluate(Environment env) {
        Expression evaluatedArg = arg.evaluate(env);
        if (evaluatedArg instanceof Constant c) {
            double argValue = c.evaluateNumeric(env);
            return new FloatConstant(Math.exp(argValue), c.isRaw());
        }
        return new Exp(evaluatedArg);
    }

    @Override
    public String toString() {
        return "exp(" + arg.toString() + ")";
    }

    public Expression getArg() {
        return arg;
    }
}
