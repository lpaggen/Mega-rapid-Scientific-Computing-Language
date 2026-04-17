package AST;

import Semantic.ExpressionVisitor;
import Lexer.Token;

public final class UnaryNode implements Expression {
    private final Token operator;
    private final Expression rhs;

    public UnaryNode(Token operator, Expression rhs) {
        this.operator = operator;
        this.rhs = rhs;
    }

//    @Override
//    public Expression evaluate(Environment env) {
//        Expression rightValue = rhs.evaluate(env);
//        return switch (operator.getKind()) {
//            case PLUS -> rightValue; // unary plus does nothing
//            case MINUS -> evaluateMinus(rightValue);
//            case NOT_EQUAL -> new BooleanNode(!isTruthy(rightValue));
//            default -> throw new RuntimeException("Unsupported unary operator: " + operator.getKind());
//        };
//    }

    @Override
    public String toString() {
        return operator.getLexeme() + rhs.toString();
    }

    @Override
    public <R> R accept(ExpressionVisitor<R> visitor) {
        return visitor.visitUnaryNode(this);
    }

    // this block makes it so that we can also evaluate 0 and 1s as T/F
    private boolean isTruthy(Object rightValue) {
        if (rightValue instanceof BooleanLiteralNode r) { return r.value(); }
        if (rightValue == null) { return false; }
        throw new RuntimeException("Cannot apply '!' to non-boolean value.");
    }

//    public double evaluateNumeric() {
//        Environment env = new Environment();
//        Expression rightValue = rhs.evaluate(env);
//        if (rightValue instanceof Scalar c) {
//            return c.evaluateNumeric(env);
//        }
//        if (rightValue instanceof BooleanNode b) {
//            return b.toNumeric().evaluateNumeric(env);
//        }
//        throw new RuntimeException("Cannot evaluate numeric value for unary operation: " + operator.getKind());
//    }

//    @Override
//    public TokenKind getType(Environment env) {
//        return switch (operator.getKind()) {
//            case PLUS, MINUS -> rhs.getType(env);
//            case NOT_EQUAL -> TokenKind.BOOLEAN;
//            default -> throw new RuntimeException("Unsupported unary operator: " + operator.getKind());
//        };
//    }

//    private Expression evaluateMinus(Expression rightValue) {
//        if (rightValue instanceof UnaryNode unary && unary.operator.getKind() == TokenKind.MINUS) {
//            return unary.rhs; // cancel double negation
//        }
//        return new Mul(new Scalar(-1), rightValue);
//    }

    public Token getOperator() {
        return operator;
    }

    public Expression getArg() {
        return rhs;
    }
}
