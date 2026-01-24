package AST;

import AST.Visitors.ExpressionVisitor;

import java.util.List;

public final class BracketLiteralNode implements Expression {
    private final List<Expression> elements;

    public BracketLiteralNode(List<Expression> elements) {
        this.elements = elements;
    }

    public List<Expression> getElements() {
        return elements;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("BracketLiteralNode{elements=[");
        for (int i = 0; i < elements.size(); i++) {
            sb.append(elements.get(i));
            if (i < elements.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]}");
        return sb.toString();
    }

    @Override
    public <R> R accept(ExpressionVisitor<R> visitor) {
        return visitor.visitBracketLiteral(this);
    }
}
