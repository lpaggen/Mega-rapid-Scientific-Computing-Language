package AST.Nodes;

import Interpreter.Tokenizer.Token;
import Util.Environment;

// this class might be temporary, it serves as a placeholder for the actual implementation
// of when we want to deal with logic and non strictly symbolic expressions
public class LogicalBinaryNode extends Expression {
    private final Expression lhs;
    private final Token operator;
    private final Expression rhs;

    public LogicalBinaryNode(Expression lhs, Token operator, Expression rhs) {
        this.lhs = lhs;
        this.operator = operator;
        this.rhs = rhs;
    }

    @Override
    public Object evaluate(Environment<String, Token> env) {
        Object lhsVal = lhs.evaluate(env);
        Object rhsVal = rhs.evaluate(env);

        return switch(operator.getKind()) {
            case AND, OR -> evaluateLogical(lhsVal, rhsVal);
            case EQUAL_EQUAL -> evaluateEquality(lhsVal, rhsVal);
            case NOT_EQUAL -> !evaluateEquality(lhsVal, rhsVal);
            default -> throw new UnsupportedOperationException("Unsupported operator: " + operator.getLexeme());
        };
    }

    // most likely what i actually want to do here is to use the env to evaluate the expressions
    // when they are symbolic, and then compare the results
    private boolean evaluateEquality(Object lhsVal, Object rhsVal) {
        if (lhsVal instanceof Number && rhsVal instanceof Number) {
            return ((Number) lhsVal).doubleValue() == ((Number) rhsVal).doubleValue();
        }
        if (lhsVal instanceof MathExpression && rhsVal instanceof MathExpression) {
            return lhsVal.equals(rhsVal);
        }
        if (lhsVal instanceof Boolean && rhsVal instanceof Boolean) {
            return lhsVal.equals(rhsVal);
        }
        if (lhsVal instanceof MathExpression) {
            return lhsVal.equals(rhsVal);
        }
        if (rhsVal instanceof MathExpression) {
            return lhsVal.equals(rhsVal);
        }
        return lhsVal.equals(rhsVal);
    }

    private Object evaluateLogical(Object lhsVal, Object rhsVal) {
        return switch (operator.getKind()) {
            case AND -> (Boolean) lhsVal && (Boolean) rhsVal;
            case OR -> (Boolean) lhsVal || (Boolean) rhsVal;
            default -> throw new UnsupportedOperationException("Unsupported operator: " + operator.getLexeme());
        };
    }

    @Override
    public String toString() {
        return null;
    }
}
