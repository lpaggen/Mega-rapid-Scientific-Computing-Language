package AST;

import AST.Visitors.ExpressionVisitor;

import java.util.List;

public final class BraceLiteralNode implements Expression {
    private final List<Statement> body;

    public BraceLiteralNode(List<Statement> body) {
        this.body = body;
    }

    public List<Statement> getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "BraceLiteralNode{" +
                "body=" + body +
                '}';
    }

    @Override
    public <R> R accept(ExpressionVisitor<R> visitor) {
        return visitor.visitBraceLiteral(this);
    }
}
