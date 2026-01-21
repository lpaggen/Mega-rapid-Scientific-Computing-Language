package AST.Expressions.Mathematics.Trigonometry;

import AST.Expressions.Expression;
import AST.Visitors.ExpressionVisitor;

public class Csc extends Expression {

    private final Expression arg;

    public Csc(Expression arg) {
        this.arg = arg;
    }

//    @Override
//    public Expression evaluate(ScopeStack env) {
//        Expression argValue = arg.evaluate(env);
//        if (argValue instanceof Scalar c) {
//            return new Scalar(1 / Math.sin(c.getDoubleValue()));
//        }
//        return new Csc(argValue);
//    }
//
//    public double evaluateNumeric(ScopeStack env) {
//        double argValue = arg.evaluateNumeric(env);
//        return 1 / Math.sin(argValue);
//    }

    @Override
    public String toString() {
        return "csc(" + arg.toString() + ")";
    }

    @Override
    public <R> R accept(ExpressionVisitor<R> visitor) {
        return visitor.visitCsc(this);
    }

    public Expression getArg() {
        return arg;
    }
}
