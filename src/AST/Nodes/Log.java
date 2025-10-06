package AST.Nodes;

import AST.Nodes.DataTypes.Constant;
import AST.Nodes.DataTypes.FloatConstant;
import AST.Nodes.DataTypes.IntegerConstant;
import Interpreter.Runtime.Environment;

public class Log extends Expression {
    private final Expression arg, base;

    public Log(Expression arg, Expression base) {
        // here we do need the base to check if natural or not
        this.arg = arg;
        this.base = base;
    }

    @Override
    public Expression evaluate(Environment env) {
        Expression argVal = arg.evaluate(env);
        Expression baseVal = base.evaluate(env);
        if (argVal instanceof Constant a && baseVal instanceof Constant b) {
            double argNum = a.getDoubleValue();
            double baseNum = b.getDoubleValue();
            if (argNum == 1.0) return new IntegerConstant(0);
            if (argNum == baseNum) return new IntegerConstant(1);
            return new FloatConstant(Math.log(argNum) / Math.log(baseNum));
        }
        return new Log(argVal, baseVal);
    }

    public double evaluateNumeric(Environment env) {
        double argValue = arg.evaluateNumeric(env);
        double baseValue = base.evaluateNumeric(env);
        if (baseValue == 1) {
            return Math.log(argValue);
        } else {
            return Math.log10(argValue) / Math.log10(baseValue);
        }
    }

    @Override
    public String toString() {
        if (base instanceof Exp bExp && bExp.getArg().toString().equals("1")) {
            return "ln(" + arg.toString() + ")";
        } return "log(" + arg.toString() + ")";
    }

    public Expression getArg() {
        return arg;
    }

    public Expression getBase() {
        return base;
    }
}
