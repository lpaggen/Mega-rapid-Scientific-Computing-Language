package AST;

import AST.Expression;
import AST.Statement;
import AST.Visitors.StatementVisitor;

// this class is a wrapper for functions that can be called without returning a value
// so we can work around the fact that functions extend Expression this way
// ie, we use them as Statement
public final class ExpressionStatementNode implements Statement {
    Expression expression;
    public ExpressionStatementNode(Expression expression) {
        this.expression = expression;
    }

    @Override
    public <R> R accept(StatementVisitor<R> visitor) {
        return visitor.visitExpressionStatement(this);
    }
//    @Override
//    public void execute(Environment env) {
//        if (expression != null) {
//            expression.evaluate(env);
//        }
//    }
}
