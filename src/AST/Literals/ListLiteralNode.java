package AST.Literals;

import AST.Expressions.Expression;

import java.util.List;

public class ListLiteralNode extends Expression {
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
}
