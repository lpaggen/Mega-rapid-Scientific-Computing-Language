package AST;

import AST.Visitors.Expressions.ExpressionVisitor;

public record BooleanLiteralNode(boolean value) implements Expression {

    @Override
    public String toString() {
        return "BooleanLiteralNode<" +
                "value=" + value +
                '>';
    }

    @Override
    public <R> R accept(ExpressionVisitor<R> visitor) {
        return visitor.visitBooleanLiteral(this);
    }
}
