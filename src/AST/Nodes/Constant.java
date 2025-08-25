package AST.Nodes;

import Interpreter.Runtime.Environment;

public class Constant extends Expression {
    private final Number value; // means we can have both int and float

    public Constant(double value) {
        this.value = value;
    }

    @Override
    public Expression evaluate(Environment env) {
        return this;
    }

    @Override
    public double evaluateNumeric(Environment env) {
        return value.doubleValue();
    }

    public double getDoubleValue() {
        return value.doubleValue();
    }

    // still keep getValue for compatibility with older code
    public Number getValue() {
        return value;
    }

    // this all makes for less boilerplate in the ArithmeticBinaryNode classes, since we can just use them as is
    public static Constant substract (Constant left, Constant right) {
        Number leftValue = left.getValue();
        Number rightValue = right.getValue();

        // return a float if either is a float, else return integer
        if (leftValue instanceof Float || rightValue instanceof Float) {
            return new Constant(leftValue.doubleValue() - rightValue.doubleValue());
        }
        return new Constant(leftValue.intValue() - rightValue.intValue());
    }

    public static Constant add (Constant left, Constant right) {
        Number leftValue = left.getValue();
        Number rightValue = right.getValue();

        // return a float if either is a float, else return integer
        if (leftValue instanceof Float || rightValue instanceof Float) {
            return new Constant(leftValue.doubleValue() + rightValue.doubleValue());
        }
        return new Constant(leftValue.intValue() + rightValue.intValue());
    }

    public static Constant multiply (Constant left, Constant right) {
        Number leftValue = left.getValue();
        Number rightValue = right.getValue();

        // return a float if either is a float, else return integer
        if (leftValue instanceof Float || rightValue instanceof Float) {
            return new Constant(leftValue.doubleValue() * rightValue.doubleValue());
        }
        return new Constant(leftValue.intValue() * rightValue.intValue());
    }

    public static Constant divide (Constant left, Constant right) {
        Number leftValue = left.getValue();
        Number rightValue = right.getValue();

        // division by zero check
        if (rightValue.doubleValue() == 0) {
            throw new ArithmeticException("Division by zero");
        }

        // then we can check if division will return a float or not with modulus
        if (leftValue.doubleValue() % rightValue.doubleValue() == 0) {
            // if it does, we return a float
            return new Constant(leftValue.intValue() / rightValue.intValue());
        }
        return new Constant(leftValue.doubleValue() / rightValue.doubleValue());
    }

    // not that we really have to but the i and f might be cool
    @Override
    public String toString() {
        if (value instanceof Integer) {return value.intValue() + "i";}
        return value.doubleValue() + "f";
    }
}
