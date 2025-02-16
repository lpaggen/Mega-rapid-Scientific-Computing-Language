package AST.Nodes;

import Interpreter.Tokenizer.Token;

public class binaryNode extends Expression {
    final Expression lhs;
    final Expression rhs;
    final Token operator;

    public binaryNode(Expression lhs, Token operator, Expression rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
        this.operator = operator;
    }

    @Override
    public Expression derive(String variable) {
        return null;
    }

    @Override
    public Object evaluate() {
        Object lhsVal = lhs.evaluate();
        Object rhsVal = rhs.evaluate();

        return switch (operator.getKind()) {
            case PLUS -> lhsVal + rhsVal;
            case MINUS -> lhsVal - rhsVal;
            case MUL -> lhsVal * rhsVal;
            case DIV -> lhsVal / rhsVal;
            default -> throw new UnsupportedOperationException("Unsupported operator: " + operator.getLexeme());
        };
    }

    @Override
    public String toString() {
        return lhs + " " + operator + " " + rhs;
    }

    @Override
    public Expression simplify() { // need to fix this eventually
        return lhs.simplify() + rhs.simplify();
    }

    @Override
    public Expression substitute(String... s) {
        return null;
    }
}
