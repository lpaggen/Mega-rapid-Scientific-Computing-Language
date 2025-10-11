package AST.Nodes.Expressions.BinaryOperations;

import AST.Nodes.Expressions.Expression;
import Interpreter.Runtime.Environment;

public abstract class BinaryNode extends Expression {
    protected final Expression lhs;
    protected final Expression rhs;

    public BinaryNode(Expression lhs, Expression rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public Expression getLeft() {
        return lhs;
    }

    public Expression getRight() {
        return rhs;
    }

    @Override
    public abstract Expression evaluate(Environment env);

    @Override
    public abstract String toString();
}
