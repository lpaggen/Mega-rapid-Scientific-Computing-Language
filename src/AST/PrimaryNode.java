package AST;

import AST.Expression;
import AST.Visitors.Expressions.ExpressionVisitor;

public final class PrimaryNode implements Expression {
    private final Expression value;

    public PrimaryNode(Expression value) {
        this.value = value;
    }

//    @Override
//    public Expression evaluate(Environment env) {
//        return value;
//    }

    @Override
    public String toString() {
        return value.toString(); // can't believe i didn't implement toString before.
    }

    @Override
    public <R> R accept(ExpressionVisitor<R> visitor) {
        return visitor.visitPrimaryNode(this);
    }
}
