package AST.Nodes;

import Interpreter.Runtime.Environment;

public class Div extends ArithmeticBinaryNode {

    public Div(Expression lhs, Expression rhs) {
        super(lhs, rhs);
    }

    @Override
    public Expression evaluate(Environment env) {
        Expression numVal = lhs.evaluate(env);
        Expression denomVal = rhs.evaluate(env);

        // Handle numeric operations
        if (numVal instanceof Constant n && denomVal instanceof Constant d) {
            return Constant.divide(n, d);
        }
        // If we reach here, it's an unsupported type combination
        return new Div(numVal, denomVal);
    }

    public double evaluateNumeric(Environment env) {
        return lhs.evaluateNumeric(env) / rhs.evaluateNumeric(env);
    }

    @Override
    public String toString() {
        return lhs.toString() + " / " + rhs.toString();
    }

    public Expression getNum() {
        return lhs;
    }

    public Expression getDenom() {
        return rhs;
    }
}
