package AST.Expressions;

import AST.Visitors.ExpressionVisitor;

public class ListAccessNode extends Expression {
    private final Expression list;
    private final Expression index;

    public ListAccessNode(Expression list, Expression index) {
        this.list = list;
        this.index = index;
    }

    public Expression getList() {
        return list;
    }

    public Expression getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return list.toString() + "[" + index.toString() + "]";
    }

    @Override
    public <R> R accept(ExpressionVisitor<R> visitor) {
        return visitor.visitListAccessNode(this);
    }
}
