package AST;

import Semantic.StatementVisitor;

public record ReturnStatementNode(Expression returnValue) implements Statement {

    @Override
    public String toString() {
        return "ReturnStatementNode{returnValue=" + returnValue + '}';
    }

    @Override
    public <R> R accept(StatementVisitor<R> visitor) {
        return visitor.visitReturnStatementNode(this);
    }
}
