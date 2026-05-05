package AST;

import Semantic.ExpressionVisitor;

public record VariableNode(String name) implements Expression {
    @Override
    public String toString() {
        return name;
    }

    @Override
    public <R> R accept(ExpressionVisitor<R> visitor) {
        return visitor.visitVariableNode(this);
    }
}
