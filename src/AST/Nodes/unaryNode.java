package AST.Nodes;

import Interpreter.Tokenizer.Token;
import Interpreter.Tokenizer.TokenKind;

public class unaryNode extends Expression {
    private final Token operator;
    private final Expression rhs;

    public unaryNode(Token operator, Expression rhs) {
        this.operator = operator;
        this.rhs = rhs;
    }

    @Override
    public Expression derive(String variable) {
        return new unaryNode(operator, rhs.derive(variable));
    }

    @Override
    public Object evaluate() {
        Object rightValue = rhs.evaluate();
        switch (operator.getKind()) {
            case MINUS: return - (double) rightValue;
            case NOT_EQUAL: return !isTruthy(rightValue);
        }
        throw new RuntimeException("Unsupported unary operator: " + operator.getKind());
    }

    @Override
    public String toString() {
        return operator.getLexeme() + rhs.toString();
    }

    @Override
    public Expression simplify() {
        return null;
    }

    @Override
    public Expression substitute(String... s) {
        return null;
    }

    private boolean isTruthy(Object rightValue) {
        if (rightValue instanceof Boolean) { return (Boolean) rightValue; }
        if (rightValue instanceof Integer) { return (Integer) rightValue == 0; }
        if (rightValue instanceof Double) { return (Double) rightValue == 0; }
        if (rightValue instanceof Float) { return (Float) rightValue == 0; }
        if (rightValue instanceof Long) { return (Long) rightValue == 0; }
        if (rightValue == null) { return false; }
        return false;
    }
}
