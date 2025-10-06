package AST.Nodes.DataTypes;

import AST.Nodes.Expression;
import Interpreter.Runtime.Environment;

public abstract class Constant extends Expression {
    private final Number value; // means we can have both int and float

    public Constant(Number value) { // changed to Number to allow Integers and Floats -- not just double
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
        if (!(obj instanceof Constant other)) return false;
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
    public static Constant subtract(Constant left, Constant right) {
        Number leftValue = left.getValue();
        Number rightValue = right.getValue();

        boolean isFloatingPoint = (leftValue instanceof Float || leftValue instanceof Double
                || rightValue instanceof Float || rightValue instanceof Double);

        if (isFloatingPoint) {
            return new FloatConstant(leftValue.doubleValue() - rightValue.doubleValue());
        } else {
            return new IntegerConstant(leftValue.intValue() - rightValue.intValue());
        }
    }

    public static Constant add (Constant left, Constant right) {
        Number leftValue = left.getValue();
        Number rightValue = right.getValue();

        boolean isFloatingPoint = (leftValue instanceof Float || leftValue instanceof Double
                || rightValue instanceof Float || rightValue instanceof Double);

        if (isFloatingPoint) {
            return new FloatConstant(leftValue.doubleValue() + rightValue.doubleValue());
        } else {
            return new IntegerConstant(leftValue.intValue() + rightValue.intValue());
        }
    }

    public static Constant multiply (Constant left, Constant right) {
        Number leftValue = left.getValue();
        Number rightValue = right.getValue();

        boolean isFloatingPoint = (leftValue instanceof Float || leftValue instanceof Double
                || rightValue instanceof Float || rightValue instanceof Double);

        if (isFloatingPoint) {
            return new FloatConstant(leftValue.doubleValue() * rightValue.doubleValue());
        } else {
            return new IntegerConstant(leftValue.intValue() * rightValue.intValue());
        }
    }

    public static Constant divide (Constant left, Constant right) {
        Number leftValue = left.getValue();
        Number rightValue = right.getValue();

        boolean isFloatingPoint = (leftValue instanceof Float || leftValue instanceof Double
                || rightValue instanceof Float || rightValue instanceof Double);

        if (isFloatingPoint) {
            if (rightValue.doubleValue() == 0.0) {
                throw new ArithmeticException("Division by zero");
            }
            return new FloatConstant(leftValue.doubleValue() / rightValue.doubleValue());
        } else {
            if (rightValue.intValue() == 0) {
                throw new ArithmeticException("Division by zero");
            }
            return new IntegerConstant(leftValue.intValue() / rightValue.intValue());
        }
    }

    // this is fine, not clean but it will work just fine
    @Override
    public String toString() {
        if (value instanceof Integer) {
            return value.intValue() + "";
        }
        return value.doubleValue() + "";
    }
}
