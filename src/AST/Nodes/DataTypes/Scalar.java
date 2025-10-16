package AST.Nodes.DataTypes;

import AST.Nodes.Expressions.Expression;
import Interpreter.Runtime.Environment;
import Interpreter.Tokenizer.Token;
import Interpreter.Tokenizer.TokenKind;

public class Scalar extends Expression {
    private final Number value; // means we can have both int and float

    public Scalar(Number value) {
        this.value = value;
    }

    @Override
    public Expression evaluate(Environment env) {
        return this;
    }

    // am not a fan of doing this, but it does work for now
    // of course, using Number breaks this logic, doubleValue is not great, but it does work
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Scalar other)) return false;
        return value.doubleValue() == other.getDoubleValue();
    }

    public double getDoubleValue() {
        return value.doubleValue();
    }

    // still keep getValue for compatibility with older code
    public Number getValue() {
        return value;
    }

    // this all makes for less boilerplate in the ArithmeticBinaryNode classes, since we can just use them as is
    public static Scalar subtract(Scalar left, Scalar right) {
        Number leftValue = left.getValue();
        Number rightValue = right.getValue();

        return new Scalar(leftValue.doubleValue() - rightValue.doubleValue());
    }

    public static Scalar add (Scalar left, Scalar right) {
        Number leftValue = left.getValue();
        Number rightValue = right.getValue();

        return new Scalar(leftValue.doubleValue() + rightValue.doubleValue());
    }

    public static Scalar multiply (Scalar left, Scalar right) {
        Number leftValue = left.getValue();
        Number rightValue = right.getValue();

        return new Scalar(leftValue.doubleValue() * rightValue.doubleValue());
    }

    public static Scalar divide(Scalar left, Scalar right) {
        Number leftValue = left.getValue();
        Number rightValue = right.getValue();

        if (rightValue.doubleValue() == 0.0)
            throw new ArithmeticException("Division by zero");

        return new Scalar(leftValue.doubleValue() / rightValue.doubleValue());
    }

    @Override
    public TokenKind getType(Environment env) {
        return TokenKind.SCALAR;
    }

    // this is just displaying as int when it's integer like, nothing really behaves as int however
    @Override
    public String toString() {
        Number n = this.getValue();
        String s;
        if (n.doubleValue() % 1 == 0) {
            // effectively an integer, drop the .0
            s = String.valueOf(n.longValue());
        } else {
            s = n.toString();
        }
        return s;
    }
}
