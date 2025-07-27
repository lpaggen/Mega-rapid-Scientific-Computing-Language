package AST.Nodes;

import Interpreter.Tokenizer.Token;
import Util.EnvReWrite;
import Util.Environment;

public class unaryNode extends Expression {
    private final Token operator;
    private final Expression rhs;

    public unaryNode(Token operator, Expression rhs) {
        this.operator = operator;
        this.rhs = rhs;
    }

    // i'm not sure yet what to do, a 2nd constructor could work
    // we have to accept stuff like this: !true, -5, !true, but also -x ...
    // i will implement at a later stage, first i need to make sure this works
    public unaryNode(Token operator, MathExpression rhs) {
        this.operator = operator;
        this.rhs = rhs;
    }

    @Override
    public Object evaluate(EnvReWrite env) {
        Object rightValue = rhs.evaluate(env);
        return switch (operator.getKind()) {
            case MINUS -> evaluateMinus(rightValue);
            case NOT_EQUAL -> !isTruthy(rightValue);
            default -> throw new RuntimeException("Unsupported unary operator: " + operator.getKind());
        };
    }

    @Override
    public String toString() {
        return operator.getLexeme() + rhs.toString();
    }

    // this block makes it so that we can also evaluate 0 and 1s as T/F
    private boolean isTruthy(Object rightValue) {
        if (rightValue instanceof Boolean) { return (Boolean) rightValue; }
        if (rightValue instanceof BooleanNode) { return ((BooleanNode) rightValue).getValue(); }
        if (rightValue == null) { return false; }
        throw new RuntimeException("Cannot apply '!' to non-boolean value.");
    }

    private Object evaluateMinus(Object rightValue) {
        if (rightValue instanceof Number) { return -((Number) rightValue).doubleValue(); }
        if (rightValue instanceof MathExpression) { return new Multiply(new Constant(-1), (MathExpression) rightValue); }
        throw new RuntimeException("Cannot apply '-' to non-numeric (algebraic) value.");
    }
}
