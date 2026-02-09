package AST;

import Semantic.StatementVisitor;

public record ClaimStatementNode(Expression claimExpression) implements Statement {
    @Override
    public String toString() {
        return "ClaimStatementNode{claimExpression=" + claimExpression + '}';
    }

    @Override
    public <R> R accept(StatementVisitor<R> visitor) {
        return visitor.visitClaimStatementNode(this);
    }
}
