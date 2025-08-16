package AST.Nodes;

import Util.Environment;

public class Div extends Expression {
    private final Expression num, denom;

    public Div(Expression num, Expression denom) {
        this.num = num;
        this.denom = denom;
    }

    private int evaluateInteger(Object lhsVal, Object rhsVal) {
        return (Integer) lhsVal / (Integer) rhsVal;
    }

    private float evaluateFloat(Object lhsVal, Object rhsVal) {
        return (Float) lhsVal / (Float) rhsVal;
    }

    private float evaluateFloatTolerant(Object lhsVal, Object rhsVal) {
        // this is a tolerant version of the float evaluation, it will convert int to float
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
        Object numVal = num.evaluate(env);
        Object denomVal = denom.evaluate(env);

        // Handle numeric operations
        if (numVal instanceof Integer && denomVal instanceof Integer) {
            return evaluateInteger(numVal, denomVal);
        } else if ((numVal instanceof Float && denomVal instanceof Float) ||
                (numVal instanceof Integer && denomVal instanceof Float) ||
                (numVal instanceof Float && denomVal instanceof Integer)) {
            return evaluateFloatTolerant(numVal, denomVal);
        }
        // If we reach here, it's an unsupported type combination
        throw new UnsupportedOperationException("Unsupported types for subtraction: " + numVal.getClass() + " and " + denomVal.getClass());
    }

    @Override
    public String toString() {
        return num.toString() + " / " + denom.toString();
    }

    public Expression getNum() {
        return num;
    }

    public Expression getDenom() {
        return denom;
    }
}
