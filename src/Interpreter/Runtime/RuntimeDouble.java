package Interpreter.Runtime;

public final class RuntimeDouble implements RuntimeScalar {
    private final double value;

    public RuntimeDouble(double value) {
        this.value = value;
    }

    @Override
    public RuntimeScalar add(RuntimeScalar other) {
        return new RuntimeDouble(this.value + other.getValue());
    }

    @Override
    public RuntimeScalar subtract(RuntimeScalar other) {
        return new RuntimeDouble(this.value - other.getValue());
    }

    @Override
    public RuntimeScalar multiply(RuntimeScalar other) {
        return new RuntimeDouble(this.value * other.getValue());
    }

    @Override
    public RuntimeScalar divide(RuntimeScalar other) {
        if (other.getValue() == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return new RuntimeDouble(this.value / other.getValue());
    }

    @Override
    public double getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }
}
