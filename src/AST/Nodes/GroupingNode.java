package AST.Nodes;

import Interpreter.Runtime.Environment;

public class GroupingNode extends Expression {
    private final Expression expression;

    public GroupingNode(Expression expression) {
        this.expression = expression;
    }

    public String toString() {
        return "(" + expression.toString() + ")";
    }

    @Override
    public Object evaluate(Environment env) {
        return expression.evaluate(env);
    }

    // need to double-check my logic here
    public Expression getValue() {
        return expression;
    }
}
