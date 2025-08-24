package AST.Nodes;

import AST.Nodes.Conditional.BooleanNode;
import Interpreter.Tokenizer.Token;
import Interpreter.Tokenizer.TokenKind;
import Interpreter.Runtime.Environment;

public class UnaryNode extends Expression {
    private final Token operator;
    private final Expression rhs;

    public UnaryNode(Token operator, Expression rhs) {
        this.operator = operator;
        this.rhs = rhs;
    }

    @Override
    public Expression evaluate(Environment env) {
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
        if (rightValue instanceof BooleanNode) { return ((BooleanNode) rightValue).getValue(); }
        if (rightValue == null) { return false; }
        throw new RuntimeException("Cannot apply '!' to non-boolean value.");
    }

    private Expression evaluateMinus(Expression rightValue) {
        if (rightValue instanceof Constant c) {
            return new Constant(-c.getValue());
        }
        if (rightValue instanceof UnaryNode unary && unary.operator.getKind() == TokenKind.MINUS) {
            return unary.rhs; // cancel double negation
        }
        return new Mul(new Constant(-1), rightValue);
    }

    public Token getOperator() {
        return operator;
    }

    public Expression getArg() {
        return rhs;
    }
}
