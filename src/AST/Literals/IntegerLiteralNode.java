package AST.Literals;

import AST.Expressions.Expression;
import AST.Visitors.ExpressionVisitor;

public final class IntegerLiteralNode extends Expression {
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
