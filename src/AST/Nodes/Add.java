package AST.Nodes;

import Util.Environment;

public class Add extends Expression {
    private final Expression left, right;

    public Add(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public double evaluate(Environment env) {
        double leftResult = left.evaluate(env);
        double rightResult = right.evaluate(env);

        return leftResult + rightResult;
    }

    // the logic needs to be reworked
    @Override
    public String toString() {
        return left.toString() + " + " + right.toString();
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }
}
