package AST;

import Semantic.ExpressionVisitor;

import java.util.List;

public final class ListLiteralNode implements Expression {
    private final List<Expression> elements;

    public ListLiteralNode(List<Expression> elements) {
        this.elements = elements;
    }

    public java.util.List<Expression> getElements() {
        return elements;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ListLiteralNode{elements=[");
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
    public <T> T accept(ExpressionVisitor<T> visitor) {
        return visitor.visitListLiteral(this);
    }
}
