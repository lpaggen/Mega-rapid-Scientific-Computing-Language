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
    public double evaluate() {
        double lhsVal = lhs.evaluate();
        double rhsVal = rhs.evaluate();

        return switch (operator.getLexeme()) {
            case "+" -> lhsVal + rhsVal;
            case "-" -> lhsVal - rhsVal;
            case "*" -> lhsVal * rhsVal;
            case "/" -> lhsVal / rhsVal;
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
}
