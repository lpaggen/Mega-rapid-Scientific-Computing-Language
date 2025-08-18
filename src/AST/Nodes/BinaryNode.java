package AST.Nodes;

import Util.Environment;

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
    public abstract Object evaluate(Environment env);

    @Override
    public abstract String toString();
}
