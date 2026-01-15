package AST.Literals.Abstract;

import AST.Expressions.Expression;

import java.util.List;

public final class BracketLiteralNode extends Expression {
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
}
