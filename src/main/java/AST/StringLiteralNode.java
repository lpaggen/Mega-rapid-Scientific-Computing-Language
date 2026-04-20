package AST;

import Semantic.ExpressionVisitor;

public record StringLiteralNode(String value) implements Expression {

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
