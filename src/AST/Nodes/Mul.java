package AST.Nodes;

import Util.Environment;

public class Mul extends BinaryNode {
    public Mul(Expression lhs, Expression rhs) {
        super(lhs, rhs);
    }

    private Integer evaluateInteger(Object lhsVal, Object rhsVal) {
        return (Integer) lhsVal * (Integer) rhsVal;
    }

    private Float evaluateFloat(Object lhsVal, Object rhsVal) {
        return (Float) lhsVal * (Float) rhsVal;
    }

    private Float evaluateFloatTolerant(Object lhsVal, Object rhsVal) {
        if (lhsVal instanceof Integer) {
            lhsVal = ((Integer) lhsVal).floatValue();
        }
        if (rhsVal instanceof Integer) {
            rhsVal = ((Integer) rhsVal).floatValue();
        }
        return evaluateFloat(lhsVal, rhsVal);
    }

    @Override
    public Object evaluate(Environment env) {
        Object leftVal = lhs.evaluate(env);
        Object rightVal = rhs.evaluate(env);

        if (leftVal instanceof Integer && rightVal instanceof Integer) {
            return evaluateInteger(leftVal, rightVal);
        } else if ((leftVal instanceof Float && rightVal instanceof Float) ||
                (leftVal instanceof Integer && rightVal instanceof Float) ||
                (leftVal instanceof Float && rightVal instanceof Integer)) {
            return evaluateFloatTolerant(leftVal, rightVal);
        }
        throw new RuntimeException("Unsupported types for addition: " + leftVal.getClass() + " and " + rightVal.getClass());
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
