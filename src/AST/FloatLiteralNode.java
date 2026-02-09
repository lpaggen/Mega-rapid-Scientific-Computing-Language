package AST;

import Semantic.ExpressionVisitor;

public final class FloatLiteralNode implements Expression {
    private final double value;

    public FloatLiteralNode(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "FloatLiteralNode{" +
                "value=" + value +
                '}';
    }

    @Override
    public <R> R accept(ExpressionVisitor<R> visitor) {
        return visitor.visitFloatLiteral(this);
    }
}
