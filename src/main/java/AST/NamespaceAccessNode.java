package AST;

import Semantic.ExpressionVisitor;

public record NamespaceAccessNode(String namespace, String expression) implements Expression {

    @Override
    public String toString() {
        return namespace + "::" + expression.toString();
    }

    @Override
    public <R> R accept(ExpressionVisitor<R> visitor) {
        return visitor.visitNameSpaceAccessNode(this);
    }
}
