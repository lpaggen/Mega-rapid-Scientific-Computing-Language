package AST;

import Semantic.ExpressionVisitor;

public final class Sin implements Expression {
    private final Expression arg;

    public Sin(Expression arg) {
        this.arg = arg;
    }

//    @Override
//    public Expression evaluate(Environment env) {
//        Expression argValue = arg.evaluate(env);
//        if (argValue instanceof Scalar c) {
//            return new Scalar(Math.sin(c.getDoubleValue()));
//        }
//        return new Sin(argValue);
//    }
//
//    public double evaluateNumeric(Environment env) {
//        double argValue = arg.evaluateNumeric(env);
//        return Math.sin(argValue);
//    }

    @Override
    public String toString() {
        return "sin(" + arg.toString() + ")";
    }

    @Override
    public <R> R accept(ExpressionVisitor<R> visitor) {
        return visitor.visitSin(this);
    }

    public Expression getArg() {
        return arg;
    }
}
