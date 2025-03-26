package AST.Nodes;

import Interpreter.Tokenizer.Token;
import Interpreter.Tokenizer.TokenKind;
import Util.LookupTable;

public class unaryNode extends Expression {
    private final Token operator;
    private final Expression rhs;

    public unaryNode(Token operator, Expression rhs) {
        this.operator = operator;
        this.rhs = rhs;
    }

    @Override
    public Object evaluate(LookupTable<String, Token> env) {
        Object rightValue = rhs.evaluate(env);
        return switch (operator.getKind()) {
            case MINUS -> -(double) rightValue;
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
        if (rightValue instanceof Integer) { return (Integer) rightValue == 0; }
        if (rightValue instanceof Double) { return (Double) rightValue == 0; }
        if (rightValue instanceof Float) { return (Float) rightValue == 0; }
        if (rightValue instanceof Long) { return (Long) rightValue == 0; }
        if (rightValue == null) { return false; }
        return false;
    }
}
