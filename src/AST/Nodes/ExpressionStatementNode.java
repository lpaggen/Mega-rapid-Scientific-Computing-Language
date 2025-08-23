package AST.Nodes;

import Interpreter.Runtime.Environment;

// this class is a wrapper for functions that can be called without returning a value
// so we can work around the fact that functions extend Expression this way
// ie, we use them as Statement
public class ExpressionStatementNode extends Statement {
    Expression expression;
    public ExpressionStatementNode(Expression expression) {
        this.expression = expression;
    }
    @Override
    public void execute(Environment env) {
        if (expression != null) {
            expression.evaluate(env);
        }
    }
}
