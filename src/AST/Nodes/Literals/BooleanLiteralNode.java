package AST.Nodes.Literals;

import AST.Nodes.Expressions.Expression;

public class BooleanLiteralNode extends Expression {
    private final boolean value;

    public BooleanLiteralNode(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "BooleanLiteralNode<" +
                "value=" + value +
                '>';
    }
}
