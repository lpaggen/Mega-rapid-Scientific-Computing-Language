package Runtime;

public final class RuntimeInteger implements RuntimeScalar {
    private final int value;

    public RuntimeInteger(int value) {
        this.value = value;
    }

    @Override
    public RuntimeScalar add(RuntimeScalar other) {
        return new RuntimeInteger(this.value + (int) other.getValue());
    }

    @Override
    public RuntimeScalar subtract(RuntimeScalar other) {
        return new RuntimeInteger(this.value - (int) other.getValue());
    }

    @Override
    public RuntimeScalar multiply(RuntimeScalar other) {
        return new RuntimeInteger(this.value * (int) other.getValue());
    }

    @Override
    public RuntimeScalar divide(RuntimeScalar other) {
        return new RuntimeInteger(this.value / (int) other.getValue());
    }

    @Override
    public double getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
