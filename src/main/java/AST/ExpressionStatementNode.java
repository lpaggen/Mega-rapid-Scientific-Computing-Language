package AST;

import Semantic.StatementVisitor;

// this class is a wrapper for functions that can be called without returning a value
// so we can work around the fact that functions extend Expression this way
// ie, we use them as Statement
public record ExpressionStatementNode(Expression expression) implements Statement {
    @Override
    public <R> R accept(StatementVisitor<R> visitor) {
        return visitor.visitExpressionStatement(this);
    }
}
