package AST.Nodes;

import Interpreter.Tokenizer.Token;
import Util.EnvReWrite;
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
    @Override
    public Object evaluate(EnvReWrite env) {
        Object lhsVal = lhs.evaluate(env);
        Object rhsVal = rhs.evaluate(env);

        if (lhsVal instanceof BooleanNode && rhsVal instanceof BooleanNode) {
            return evaluateBoolean(((BooleanNode) lhsVal).getValue(), ((BooleanNode) rhsVal).getValue());
        }
        if (lhsVal instanceof Integer && rhsVal instanceof Integer) {
            return evaluateInteger(lhsVal, rhsVal);
        }
        if (lhsVal instanceof Float && rhsVal instanceof Float) {
            return evaluateFloat(lhsVal, rhsVal);
        }
        if (lhsVal instanceof Integer && rhsVal instanceof Float) {
            return evaluateFloatTolerant(lhsVal, rhsVal);
        }
        if (lhsVal instanceof Float && rhsVal instanceof Integer) {
            return evaluateFloatTolerant(lhsVal, rhsVal);
        }
        if (lhsVal instanceof MathExpression && rhsVal instanceof MathExpression) {
            return new BinaryNode((MathExpression) lhsVal, operator, (MathExpression) rhsVal);
            }
        if (lhsVal instanceof MathExpression) {
            return new BinaryNode((MathExpression) lhsVal, operator, rhs);
        }
        if (rhsVal instanceof MathExpression) {
            return new BinaryNode(lhs, operator, (MathExpression) rhsVal);
        }
        throw new UnsupportedOperationException("Unsupported types for" + operator.getLexeme() + ": " + lhsVal.getClass() + " and " + rhsVal.getClass());
    }

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
    public String toString() {
        return lhs + " " + operator + " " + rhs;
    }
}
