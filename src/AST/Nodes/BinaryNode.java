package AST.Nodes;

import Interpreter.Tokenizer.Token;
import Util.LookupTable;

public class BinaryNode extends Expression {
    final MathExpression lhs;
    final MathExpression rhs;
    final Token operator;

    public BinaryNode(MathExpression lhs, Token operator, MathExpression rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
        this.operator = operator;
    }

    public Expression simplify() {
        return null;
    }

    // this needs a lot of logic too
    public Expression derive(String variable) {
        return null;
    }

    public Expression substitute(String variable) {
        return null;
    }

    // evaluate in our case is going to need much more than just a double, so we need to change this to Object
    // it's also going to be quite extensive, because we have a lot of type matches to check
    @Override
    public Object evaluate(LookupTable<String, Token> env) {
        Object lhsVal = lhs.evaluate(env);
        Object rhsVal = rhs.evaluate(env);

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

    public MathExpression substitute() { // need to fix this eventually
        return lhs.substitute() + rhs.substitute();
    }
}
