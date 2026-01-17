package AST.Literals.Graph;

import AST.Expressions.Expression;

public class NodeLiteralNode extends Expression {
    private final String identifier;
    private final Expression value;
    public NodeLiteralNode(String identifier, Expression value) {
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
        return "NodeLiteralNode{" +
                "identifier='" + identifier + '\'' +
                ", value=" + value +
                '}';
    }
}
