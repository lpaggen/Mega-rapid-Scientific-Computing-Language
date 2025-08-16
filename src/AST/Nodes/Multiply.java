package AST.Nodes;

import Util.Environment;

public class Multiply extends Expression {
    private final Expression left, right;

    public Multiply(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public double evaluate(Environment env) {
        return left.evaluate(env) * right.evaluate(env);
    }

    @Override
    public String toString() {
        return left.toString() + " * " + right.toString();
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }
}
