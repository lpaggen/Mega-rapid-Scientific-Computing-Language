package AST.Nodes;

import Interpreter.Tokenizer.Token;
import Util.Environment;

public class BinaryNode extends Expression {
    final Expression lhs;
    final Expression rhs;
    final Token operator;

    public BinaryNode(Expression lhs, Token operator, Expression rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
        this.operator = operator;
    }

    // evaluate in our case is going to need much more than just a double, so we need to change this to Object
    // it's also going to be quite extensive, because we have a lot of type matches to check

    private Integer evaluateInteger(Object lhsVal, Object rhsVal) {
        return switch (operator.getKind()) {
            case PLUS -> (Integer) lhsVal + (Integer) rhsVal;
            case MINUS -> (Integer) lhsVal - (Integer) rhsVal;
            case MUL -> (Integer) lhsVal * (Integer) rhsVal;
            case DIV -> (Integer) lhsVal / (Integer) rhsVal;
            default -> throw new UnsupportedOperationException("Unsupported operator: " + operator.getLexeme());
        };
    }

    private Float evaluateFloat(Object lhsVal, Object rhsVal) {
        return switch (operator.getKind()) {
            case PLUS -> ((Float) lhsVal) + ((Float) rhsVal);
            case MINUS -> ((Float) lhsVal) - ((Float) rhsVal);
            case MUL -> ((Float) lhsVal) * ((Float) rhsVal);
            case DIV -> ((Float) lhsVal) / ((Float) rhsVal);
            default -> throw new UnsupportedOperationException("Unsupported operator: " + operator.getLexeme());
        };
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

    private Object evaluateBoolean(Boolean lhsVal, Boolean rhsVal) {
        return switch (operator.getKind()) {
            case AND -> lhsVal && rhsVal;
            case OR -> lhsVal || rhsVal;
            default -> throw new UnsupportedOperationException("Unsupported operator: " + operator.getLexeme());
        };
    }

    @Override
    public Object evaluate(Environment env) {
        Object leftVal = lhs.evaluate(env);
        Object rightVal = rhs.evaluate(env);

        // Handle numeric operations
        if (leftVal instanceof Integer && rightVal instanceof Integer) {
            return evaluateInteger(leftVal, rightVal);
        } else if ((leftVal instanceof Float && rightVal instanceof Float) ||
                (leftVal instanceof Integer && rightVal instanceof Float) ||
                (leftVal instanceof Float && rightVal instanceof Integer)) {
            return evaluateFloatTolerant(leftVal, rightVal);
        }

        // Handle boolean operations
        if (leftVal instanceof Boolean && rightVal instanceof Boolean) {
            return evaluateBoolean((Boolean) leftVal, (Boolean) rightVal);
        }

        // If we reach here, it's an unsupported type combination
        throw new RuntimeException("Type error: Cannot apply operator " + operator.getLexeme() +
                " to types " + leftVal.getClass().getSimpleName() +
                " and " + rightVal.getClass().getSimpleName());
    }

    @Override
    public String toString() {
        return lhs + " " + operator + " " + rhs;
    }

    public Expression getLeft() {
        return lhs;
    }

    public Expression getRight() {
        return rhs;
    }

    public Token getOperator() {
        return operator;
    }
}
