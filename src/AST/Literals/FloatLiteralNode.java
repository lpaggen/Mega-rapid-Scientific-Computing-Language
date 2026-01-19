package AST.Literals;

import AST.Expressions.Expression;

public class FloatLiteralNode extends Expression {
    private final double value;

    public FloatLiteralNode(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "FloatLiteralNode{" +
                "value=" + value +
                '}';
    }
}
