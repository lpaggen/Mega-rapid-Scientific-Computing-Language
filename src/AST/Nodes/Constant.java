package AST.Nodes;

import AST.Nodes.Conditional.BooleanNode;
import Interpreter.Runtime.Environment;
import Interpreter.Tokenizer.TokenKind;

public class Constant extends Expression {
    private final Number value; // means we can have both int and float
    private boolean isRaw = false; // to differentiate between raw numbers and evaluated constants

    public Constant(Number value, Boolean isRaw) { // changed to Number to allow Integers and Floats -- not just double
        this.value = value;
        this.isRaw = isRaw;
    }

    @Override
    public Expression evaluate(Environment env) {
        return this;
    }

    @Override
    public TokenKind getType(Environment env) {
        String name = value.getClass().getSimpleName();
        switch (name) {
            case "Integer" -> {
                return TokenKind.INTEGER;
            }
            case "Double", "Float" -> {
                return TokenKind.FLOAT;
            }
            default -> throw new RuntimeException("Unknown constant type: " + name);
        }
    }

    // am not a fan of doing this, but it does work for now
    // of course, using Number breaks this logic, doubleValue is not great, but it does work
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Constant other)) return false;
        return value.doubleValue() == other.getDoubleValue();
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
    public static Constant subtract(Constant left, Constant right) {
        Number leftValue = left.getValue();
        Number rightValue = right.getValue();
        boolean leftRaw = left.isRaw();
        boolean rightRaw = right.isRaw();

        if (leftRaw != rightRaw) {
            throw new RuntimeException("Cannot subtract raw and non-raw constants");
        }

        boolean isFloatingPoint = (leftValue instanceof Float || leftValue instanceof Double
                || rightValue instanceof Float || rightValue instanceof Double);

        if (isFloatingPoint) {
            return new Constant(leftValue.doubleValue() - rightValue.doubleValue(), leftRaw);
        } else {
            return new Constant(leftValue.intValue() - rightValue.intValue(), leftRaw);
        }
    }

    public static Constant add (Constant left, Constant right) {
        Number leftValue = left.getValue();
        Number rightValue = right.getValue();
        Boolean leftRaw = left.isRaw();
        Boolean rightRaw = right.isRaw();

        if (leftRaw != rightRaw) {
            throw new RuntimeException("Cannot add raw and non-raw constants");
        }

        boolean isFloatingPoint = (leftValue instanceof Float || leftValue instanceof Double
                || rightValue instanceof Float || rightValue instanceof Double);

        if (isFloatingPoint) {
            return new Constant(leftValue.doubleValue() + rightValue.doubleValue(), leftRaw);
        } else {
            return new Constant(leftValue.intValue() + rightValue.intValue(), leftRaw);
        }
    }

    public static Constant multiply (Constant left, Constant right) {
        Number leftValue = left.getValue();
        Number rightValue = right.getValue();
        Boolean leftRaw = left.isRaw();
        Boolean rightRaw = right.isRaw();

        if (leftRaw != rightRaw) {
            throw new RuntimeException("Cannot multiply raw and non-raw constants");
        }

        boolean isFloatingPoint = (leftValue instanceof Float || leftValue instanceof Double
                || rightValue instanceof Float || rightValue instanceof Double);

        if (isFloatingPoint) {
            return new Constant(leftValue.doubleValue() * rightValue.doubleValue(), leftRaw);
        } else {
            return new Constant(leftValue.intValue() * rightValue.intValue(), leftRaw);
        }
    }

    public static Constant divide (Constant left, Constant right) {
        Number leftValue = left.getValue();
        Number rightValue = right.getValue();
        Boolean leftRaw = left.isRaw();
        Boolean rightRaw = right.isRaw();

        if (leftRaw != rightRaw) {
            throw new RuntimeException("Cannot divide raw and non-raw constants");
        }

        boolean isFloatingPoint = (leftValue instanceof Float || leftValue instanceof Double
                || rightValue instanceof Float || rightValue instanceof Double);

        if (isFloatingPoint) {
            if (rightValue.doubleValue() == 0.0) {
                throw new ArithmeticException("Division by zero");
            }
            return new Constant(leftValue.doubleValue() / rightValue.doubleValue(), leftRaw);
        } else {
            if (rightValue.intValue() == 0) {
                throw new ArithmeticException("Division by zero");
            }
            return new Constant(leftValue.intValue() / rightValue.intValue(), leftRaw);
        }
    }

    public Boolean isRaw() {
        return isRaw;
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
