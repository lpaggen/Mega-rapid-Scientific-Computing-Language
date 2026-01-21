package AST.Literals.Graph;

import AST.Expressions.Expression;
import AST.Visitors.ExpressionVisitor;

public class EdgeLiteralNode extends Expression {
    private String fromNode;
    private String toNode;
    private Expression weight;
    public EdgeLiteralNode(String fromNode, String toNode, Expression weight) {
        this.fromNode = fromNode;
        this.toNode = toNode;
        this.weight = weight;
    }

    public String getFromNode() {
        return fromNode;
    }

    public String getToNode() {
        return toNode;
    }

    public Expression getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "EdgeLiteralNode{}";
    }

    @Override
    public <R> R accept(ExpressionVisitor<R> visitor) {
        return visitor.visitEdgeLiteralNode(this);
    }
}
