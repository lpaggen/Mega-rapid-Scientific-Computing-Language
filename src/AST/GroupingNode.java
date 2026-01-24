package AST;

import AST.Visitors.ExpressionVisitor;

public final class GroupingNode implements Expression {
    private final Expression expression;

    public GroupingNode(Expression expression) {
        this.expression = expression;
    }

    public String toString() {
        return "(" + expression.toString() + ")";
    }

    @Override
    public <R> R accept(ExpressionVisitor<R> visitor) {
        return visitor.visitGroupingNode(this);
    }

    // need to double-check my logic here
    public Expression getValue() {
        return expression;
    }
}
