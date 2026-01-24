package AST;

import AST.Expression;
import AST.Visitors.Expressions.ExpressionVisitor;

public final class GraphNodeLiteralNode implements Expression {
    private final String identifier;
    private final Expression value;
    public GraphNodeLiteralNode(String identifier, Expression value) {
        this.identifier = identifier;
        this.value = value;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Expression getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "GraphNodeLiteralNode{" +
                "identifier='" + identifier + '\'' +
                ", value=" + value +
                '}';
    }

    @Override
    public <R> R accept(ExpressionVisitor<R> visitor) {
        return visitor.visitGraphNodeLiteralNode(this);
    }
}
