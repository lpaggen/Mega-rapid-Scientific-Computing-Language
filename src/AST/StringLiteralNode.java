package AST;

import AST.Expression;
import AST.Visitors.Expressions.ExpressionVisitor;

public final class StringLiteralNode implements Expression {
    private final String value;

    public StringLiteralNode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "StringLiteralNode{" +
                "value='" + value + '\'' +
                '}';
    }

    @Override
    public <R> R accept(ExpressionVisitor<R> visitor) {
        return visitor.visitStringLiteral(this);
    }
}
