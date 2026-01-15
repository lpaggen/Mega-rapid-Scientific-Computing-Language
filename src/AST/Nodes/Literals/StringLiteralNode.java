package AST.Nodes.Literals;

import AST.Nodes.Expressions.Expression;

public class StringLiteralNode extends Expression {
    private final String value;

    public StringLiteralNode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "StringLiteralNode{" +
                "value='" + value + '\'' +
                '}';
    }
}
