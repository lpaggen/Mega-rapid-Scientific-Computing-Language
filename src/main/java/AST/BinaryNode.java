package AST;

import Semantic.ExpressionVisitor;
import Lexer.TokenKind;

public final class BinaryNode implements Expression {
    private final Expression lhs;
    private final Operators operator;
    private final Expression rhs;

    public BinaryNode(Expression lhs, Operators operator, Expression rhs) {
        this.lhs = lhs;
        this.operator = operator;
        this.rhs = rhs;
    }

    public Expression getLeft() {
        return lhs;
    }

    public Operators getOperator() {
        return operator;
    }

    public Expression getRight() {
        return rhs;
    }

    @Override
    public <T> T accept(ExpressionVisitor<T> visitor) {
        return visitor.visitBinaryNode(this);
    }

    @Override
    public String toString() {
        return "BinaryNode{" +
                "lhs=" + lhs +
                ", operator=" + operator +
                ", rhs=" + rhs +
                '}';
    }
}
