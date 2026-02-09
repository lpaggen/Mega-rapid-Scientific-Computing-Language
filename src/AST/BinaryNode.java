package AST;

import Semantic.ExpressionVisitor;
import Lexer.TokenKind;

public final class BinaryNode implements Expression {
    final Expression lhs;
    final TokenKind operator;
    final Expression rhs;
    public Type inferredType;  // set during type inference

    public BinaryNode(Expression lhs, TokenKind operator, Expression rhs) {
        this.lhs = lhs;
        this.operator = operator;
        this.rhs = rhs;
    }

    public Expression getLeft() {
        return lhs;
    }

    public TokenKind getOperator() {
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
