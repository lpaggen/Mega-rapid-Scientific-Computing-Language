package AST;

import AST.Expression;
import AST.Visitors.Expressions.ExpressionVisitor;

public final class Exp implements Expression {
    private final Expression arg;

    public Exp(Expression arg) {
        this.arg = arg;
    }

//    @Override
//    public double evaluateNumeric(Environment env) {
//        double argValue = arg.evaluateNumeric(env);
//        return Math.exp(argValue);
//    }
//
//    // so this is how we make the distinction between numeric or not, numeric are now wrapped in Constant
//    public Expression evaluate(Environment env) {
//        Expression evaluatedArg = arg.evaluate(env);
//        if (evaluatedArg instanceof Scalar c) {
//            double argValue = c.evaluateNumeric(env);
//            return new Scalar(Math.exp(argValue));
//        }
//        return new Exp(evaluatedArg);
//    }

    @Override
    public String toString() {
        return "exp(" + arg.toString() + ")";
    }

    @Override
    public <R> R accept(ExpressionVisitor<R> visitor) {
        return visitor.visitExp(this);
    }

    public Expression getArg() {
        return arg;
    }
}
