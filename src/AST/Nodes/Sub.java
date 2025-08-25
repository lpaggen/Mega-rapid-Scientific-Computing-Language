package AST.Nodes;

import Interpreter.Runtime.Environment;

import java.awt.font.NumericShaper;

public class Sub extends ArithmeticBinaryNode {
    public Sub(Expression lhs, Expression rhs) {
        super(lhs, rhs);
    }

    // this evaluate method should stick to Constant etc
    @Override
    public Expression evaluate(Environment env) {
        Expression leftVal = lhs.evaluate(env);
        Expression rightVal = rhs.evaluate(env);

        // check if we have constants
        if (leftVal instanceof Constant l && rightVal instanceof Constant r) {
            return Constant.substract(l, r);
        }
        // if we don't have constants, we return a new Sub node
        return new Sub(leftVal, rightVal);
    }

    // i think this should work, not sure yet
    @Override
    public double evaluateNumeric(Environment env) {
        return lhs.evaluateNumeric(env) - rhs.evaluateNumeric(env);
    }

    @Override
    public String toString() {
        return lhs.toString() + " + " + rhs.toString();
    }

    public Expression getLeft() {
        return lhs;
    }

    public Expression getRight() {
        return rhs;
    }
}
