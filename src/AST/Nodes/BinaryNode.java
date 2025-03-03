package AST.Nodes;

import Interpreter.Tokenizer.Token;

public class BinaryNode extends AlgebraicExpression {
    final Expression lhs;
    final Expression rhs;
    final Token operator;

    public BinaryNode(Expression lhs, Token operator, Expression rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
        this.operator = operator;
    }

    @Override
    public Expression derive(String variable) {
        return null;
    }

    // evaluate in our case is going to need much more than just a double, so we need to change this to Object
    // it's also going to be quite extensive, because we have a lot of type matches to check
    @Override
    public Object evaluate() {
        Object lhsVal = lhs.evaluate();
        Object rhsVal = rhs.evaluate();

        if (lhsVal instanceof Number && rhsVal instanceof Number) {
            return evaluateNumeric((Number) lhsVal, (Number) rhsVal);
        }
        throw new UnsupportedOperationException("Unsupported types for" + operator.getLexeme() + ": " + lhsVal.getClass() + " and " + rhsVal.getClass());
    }

    private Object evaluateNumeric(Number lhsVal, Number rhsVal) {
        return switch (operator.getKind()) {
            case PLUS -> lhsVal.doubleValue() + rhsVal.doubleValue();
            case MINUS -> lhsVal.doubleValue() - rhsVal.doubleValue();
            case MUL -> lhsVal.doubleValue() * rhsVal.doubleValue();
            case DIV -> lhsVal.doubleValue() / rhsVal.doubleValue();
            default -> throw new UnsupportedOperationException("Unsupported operator: " + operator.getLexeme());
        };
    }

    @Override
    public String toString() {
        return lhs + " " + operator + " " + rhs;
    }

    public Expression simplify() { // need to fix this eventually
        return lhs.simplify() + rhs.simplify();
    }

    @Override
    public Expression substitute(String... s) {
        return null;
    }

    @Override
    public Expression substitute(Number n) {
        return null;
    }
}
