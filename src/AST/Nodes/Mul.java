package AST.Nodes;

import Interpreter.Runtime.Environment;

public class Mul extends ArithmeticBinaryNode {
    public Mul(Expression lhs, Expression rhs) {
        super(lhs, rhs);
    }

    @Override
    public Expression evaluate(Environment env) {
        Expression leftVal = lhs.evaluate(env);
        Expression rightVal = rhs.evaluate(env);

        // this is all handled for floats, int in Constant
        if (leftVal instanceof Constant l && rightVal instanceof Constant r) {
            return Constant.multiply(l, r);
        }
        return new Mul(leftVal, rightVal);
    }

    public double evaluateNumeric(Environment env) {
        return lhs.evaluateNumeric(env) * rhs.evaluateNumeric(env);
    }

    @Override
    public String toString() {
        return lhs.toString() + " * " + rhs.toString();
    }

    public Expression getLeft() {
        return lhs;
    }

    public Expression getRight() {
        return rhs;
    }
}
