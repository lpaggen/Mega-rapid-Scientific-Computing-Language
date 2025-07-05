package AST.Nodes;

import Interpreter.Tokenizer.Token;
import Util.Environment;

public class Multiply extends MathExpression {
    private final MathExpression left, right;

    public Multiply(MathExpression left, MathExpression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public MathExpression derive(String variable) {
        // follows the basic rules of diff, power rule
        return new Add(
                new Multiply(left.derive(variable), right),
                new Multiply(left, right.derive(variable))
        );
    }

    @Override
    public MathExpression substitute(String... args) {
        return new Multiply(left.substitute(args), right.substitute(args));
    }

    @Override
    public Object evaluate(Environment<String, Token> env) {
        Object leftResult = left.evaluate(env);
        Object rightResult = right.evaluate(env);

        if (leftResult instanceof Number && rightResult instanceof Number) {
            return ((Number) leftResult).doubleValue() * ((Number) rightResult).doubleValue();
        }
        if (leftResult instanceof MathExpression && rightResult instanceof MathExpression) {
            return new Multiply((MathExpression) leftResult, (MathExpression) rightResult);
        }
        if (leftResult instanceof MathExpression) {
            return new Multiply((MathExpression) leftResult, right);
        }
        if (rightResult instanceof MathExpression) {
            return new Multiply((MathExpression) rightResult, left);
        }
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    public String toString() {
        return left.toString() + " * " + right.toString();
    }
}
