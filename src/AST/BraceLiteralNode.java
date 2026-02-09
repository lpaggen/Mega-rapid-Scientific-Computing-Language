package AST;

import Semantic.ExpressionVisitor;

import java.util.List;

public record BraceLiteralNode(List<Statement> body) implements Expression {

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
