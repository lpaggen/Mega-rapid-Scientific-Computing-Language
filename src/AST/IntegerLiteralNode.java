package AST;

import AST.Expression;
import AST.Visitors.Expressions.ExpressionVisitor;

public final class IntegerLiteralNode implements Expression {
    private final Integer value;

    public IntegerLiteralNode(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public <T> T accept(ExpressionVisitor<T> visitor) {
        return visitor.visitIntegerLiteral(this);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
