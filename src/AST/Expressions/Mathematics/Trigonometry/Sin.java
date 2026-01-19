package AST.Expressions.Mathematics.Trigonometry;

import AST.Expressions.Expression;

public class Sin extends Expression {
    private final Expression arg;

    public Sin(Expression arg) {
        this.arg = arg;
    }

//    @Override
//    public Expression evaluate(ScopeStack env) {
//        Expression argValue = arg.evaluate(env);
//        if (argValue instanceof Scalar c) {
//            return new Scalar(Math.sin(c.getDoubleValue()));
//        }
//        return new Sin(argValue);
//    }
//
//    public double evaluateNumeric(ScopeStack env) {
//        double argValue = arg.evaluateNumeric(env);
//        return Math.sin(argValue);
//    }

    @Override
    public String toString() {
        return "sin(" + arg.toString() + ")";
    }

    public Expression getArg() {
        return arg;
    }
}
