package AST.Literals;

import AST.Expressions.Expression;
import AST.Visitors.ExpressionVisitor;

public class StringLiteralNode extends Expression {
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
