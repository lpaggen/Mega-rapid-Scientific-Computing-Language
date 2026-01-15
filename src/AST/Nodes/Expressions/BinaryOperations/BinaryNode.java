package AST.Nodes.Expressions.BinaryOperations;

import AST.Nodes.Expressions.Expression;
import Lexer.TokenKind;

public final class BinaryNode extends Expression {
    final Expression lhs;
    final TokenKind operator;
    final Expression rhs;

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
    public String toString() {
        return "BinaryNode{" +
                "lhs=" + lhs +
                ", operator=" + operator +
                ", rhs=" + rhs +
                '}';
    }
}
