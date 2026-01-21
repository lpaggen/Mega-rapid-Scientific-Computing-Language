package AST.Expressions;

import AST.Visitors.ExpressionVisitor;

public class PrimaryNode extends Expression {
    private final Expression value;

    public PrimaryNode(Expression value) {
        this.value = value;
    }

//    @Override
//    public Expression evaluate(ScopeStack env) {
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
