package AST;

import Semantic.ExpressionVisitor;

public final class MapFunctionNode implements Expression {
    private final Expression body;
    public MapFunctionNode(Expression body) {
        this.body = body;
    }

    public Expression getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "MapFunctionNode{body=" + body + "}";
    }

    @Override
    public <R> R accept(ExpressionVisitor<R> visitor) {
        return visitor.visitMapFunctionNode(this);
    }
}
