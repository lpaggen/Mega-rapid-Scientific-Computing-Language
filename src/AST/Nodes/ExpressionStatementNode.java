package AST.Nodes;

import Util.Environment;

// it gets complex here, this is meant to use as a wrapper for parser compatibility
public class ExpressionStatementNode extends Statement{
    private final Expression expression;

    public ExpressionStatementNode(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        return expression.toString();
    }

    @Override
    public void execute(Environment env) {
        // this is where we actually evaluate the expression
        // and then we can use the result to do something else
        // for now, we just print it
        Object result = expression.evaluate(env);
        if (result != null) {
            System.out.println(result);
        }
    }
}
