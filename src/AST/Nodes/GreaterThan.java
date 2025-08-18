package AST.Nodes;

import Util.Environment;

public class GreaterThan extends BinaryNode {
    public GreaterThan(Expression lhs, Expression rhs) {
        super(lhs, rhs);
    }

    @Override
    public Object evaluate(Environment env) {
        Object leftVal = lhs.evaluate(env);
        Object rightVal = rhs.evaluate(env);

        if (leftVal instanceof Integer && rightVal instanceof Integer) {
            return (Integer) leftVal > (Integer) rightVal;
        } else if ((leftVal instanceof Float && rightVal instanceof Float) ||
                (leftVal instanceof Integer && rightVal instanceof Float) ||
                (leftVal instanceof Float && rightVal instanceof Integer)) {
            float l = (leftVal instanceof Integer) ? ((Integer) leftVal).floatValue() : (Float) leftVal;
            float r = (rightVal instanceof Integer) ? ((Integer) rightVal).floatValue() : (Float) rightVal;
            return l > r;
        }

        throw new RuntimeException("Unsupported types for comparison: " + leftVal.getClass() + " and " + rightVal.getClass());
    }

    @Override
    public String toString() {
        return lhs.toString() + " > " + rhs.toString();
    }
}
