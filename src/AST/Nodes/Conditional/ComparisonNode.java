package AST.Nodes.Conditional;

import AST.Nodes.DataTypes.Scalar;
import AST.Nodes.Expressions.BinaryOperations.LogicalBinaryNode;
import AST.Nodes.DataStructures.Matrix;
import AST.Nodes.Expressions.Expression;
import Interpreter.Runtime.Environment;
import Interpreter.Tokenizer.TokenKind;

// we don't need a separate node for everything, this handles it all easily
public class ComparisonNode extends LogicalBinaryNode {
    private final TokenKind operator;

    public ComparisonNode(Expression lhs, TokenKind operator, Expression rhs) {
        super(lhs, rhs);
        this.operator = operator;
    }

    public TokenKind getOperator() {
        return operator;
    }

    @Override
    public Expression evaluate(Environment env) {
        Expression leftValue = lhs.evaluate(env);
        Expression rightValue = rhs.evaluate(env);

        if (leftValue instanceof Scalar l && rightValue instanceof Scalar r) {
            return switch (operator) {
                case EQUAL -> new BooleanNode(l.getValue().equals(r.getValue()));
                case NOT_EQUAL -> new BooleanNode(!l.getValue().equals(r.getValue()));
                case LESS -> new BooleanNode(l.getDoubleValue() < r.getDoubleValue());
                case LESS_EQUAL -> new BooleanNode(l.getDoubleValue() <= r.getDoubleValue());
                case GREATER -> new BooleanNode(l.getDoubleValue() > r.getDoubleValue());
                case GREATER_EQUAL -> new BooleanNode(l.getDoubleValue() >= r.getDoubleValue());
                default -> throw new RuntimeException("Unsupported operator: " + operator);
            };
        }
        Matrix leftMatrix = leftValue instanceof Matrix ? (Matrix) leftValue : null;
        Matrix rightMatrix = rightValue instanceof Matrix ? (Matrix) rightValue : null;
        Scalar leftConst = leftValue instanceof Scalar ? (Scalar) leftValue : null;
        Scalar rightConst = rightValue instanceof Scalar ? (Scalar) rightValue : null;
        Expression l = leftMatrix != null ? leftMatrix : leftConst;
        Expression r = rightMatrix != null ? rightMatrix : rightConst;
        return switch (operator) {
            case NOT_EQUAL -> Matrix.notEqual(l, r);
            case EQUAL -> Matrix.equal(l, r);
            case GREATER -> Matrix.greater(l, r);
            case LESS -> Matrix.less(l, r);
            case GREATER_EQUAL -> Matrix.greaterEqual(l, r);
            case LESS_EQUAL -> Matrix.lessEqual(l, r);
            default -> throw new RuntimeException("Unsupported operator for matrices: " + operator);
        };
    }

    @Override
    public TokenKind getType(Environment env) {
        return TokenKind.BOOLEAN;
    }

    @Override
    public String toString() {
        String symbol = switch (operator) {
            case EQUAL -> "==";
            case NOT_EQUAL -> "!=";
            case LESS -> "<";
            case LESS_EQUAL -> "<=";
            case GREATER -> ">";
            case GREATER_EQUAL -> ">=";
            default -> throw new RuntimeException("Unsupported operator: " + operator);
        };
        return lhs.toString() + " " + symbol + " " + rhs.toString();
    }
}
