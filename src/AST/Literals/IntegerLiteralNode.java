package AST.Literals;

import AST.Expressions.Expression;

public final class IntegerLiteralNode extends Expression {
    private final Integer value;

    public IntegerLiteralNode(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
