package AST.Literals;

import AST.Expressions.Expression;
import AST.Visitors.ExpressionVisitor;

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

    @Override
    public <R> R accept(ExpressionVisitor<R> visitor) {
        return visitor.visitBooleanLiteral(this);
    }
}
