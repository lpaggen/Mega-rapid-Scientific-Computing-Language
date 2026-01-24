package AST;

import AST.Expression;
import AST.Statement;
import AST.Visitors.StatementVisitor;

public final class ReturnStatementNode implements Statement {
    private final Expression returnValue;

    public ReturnStatementNode(Expression returnValue) {
        this.returnValue = returnValue;
    }

    public Expression getReturnValue() {
        return returnValue;
    }

    @Override
    public String toString() {
        return "ReturnStatementNode{returnValue=" + returnValue + '}';
    }

    @Override
    public <R> R accept(StatementVisitor<R> visitor) {
        return visitor.visitReturnStatementNode(this);
    }
}
