package AST.Nodes;

import Interpreter.Runtime.Environment;

public class Mod extends ArithmeticBinaryNode {
    public Mod(Expression lhs, Expression rhs) {
        super(lhs, rhs);
    }

    @Override
    public Object evaluate(Environment env) {
        Object leftVal = lhs.evaluate(env);
        Object rightVal = rhs.evaluate(env);

        if (leftVal instanceof Integer && rightVal instanceof Integer) {
            return (Integer) leftVal % (Integer) rightVal;
        } else if (leftVal instanceof Float && rightVal instanceof Float) {
            return (Float) leftVal % (Float) rightVal;
        } else if (leftVal instanceof Integer && rightVal instanceof Float) {
            return ((Integer) leftVal).floatValue() % (Float) rightVal;
        } else if (leftVal instanceof Float && rightVal instanceof Integer) {
            return (Float) leftVal % ((Integer) rightVal).floatValue();
        }
        throw new RuntimeException("Unsupported types for modulus operation: " + leftVal.getClass() + " and " + rightVal.getClass());
    }
}
