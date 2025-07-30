package AST.Nodes;

import Util.Environment;

public class Minus extends MathExpression {
    private final MathExpression left, right;

    public Minus(MathExpression left, MathExpression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public MathExpression derive(String variable) {
        // derivative is just sum of derivatives
        return new Minus(left.derive(variable), right.derive(variable));
    }

    @Override
    public MathExpression substitute(String... args) {
        // substitution is just replacing with variable values from env -- args specifies which ones to sub
        return new Minus(left.substitute(args), right.substitute(args));
    }

    @Override
    public Object evaluate(Environment env) {
        Object leftResult = left.evaluate(env);
        Object rightResult = right.evaluate(env);

        if (leftResult instanceof Number && rightResult instanceof Number) {
            return ((Number) leftResult).doubleValue() - ((Number) rightResult).doubleValue();
        }
        if (leftResult instanceof MathExpression && rightResult instanceof MathExpression) {
            return new Minus((MathExpression) leftResult, (MathExpression) rightResult);
        }
        if (leftResult instanceof MathExpression) {
            return new Minus((MathExpression) leftResult, right);
        }
        if (rightResult instanceof MathExpression) {
            return new Minus((MathExpression) rightResult, left);
        }
        throw new RuntimeException("Operation not supported."); // here more work needs to be done
    }

    // the logic needs to be reworked
    @Override
    public String toString() {
        return left.toString() + " - " + right.toString();
    }
}
