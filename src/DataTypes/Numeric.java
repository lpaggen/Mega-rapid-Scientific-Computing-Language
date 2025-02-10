package DataTypes;

public class Numeric implements Computable {
    private final Number value;

    public Numeric(Number value) {
        this.value = value;
    }

    @Override
    public Computable add(Computable other) {
        if (other instanceof Numeric) {
            return new Numeric(this.value.doubleValue() + ((Numeric) other).value.doubleValue());
        }
        throw new UnsupportedOperationException("Cannot add a Numeric to a non-Numeric");
    }

    @Override
    public Computable subtract(Computable other) {
        return null;
    }

    @Override
    public Computable multiply(Computable other) {
        if (other instanceof Numeric) {
            return new Numeric(this.value.doubleValue() * ((Numeric) other).value.doubleValue());
        }
        throw new UnsupportedOperationException("Cannot multiply a Numeric with a non-Numeric");
    }

    @Override
    public Computable divide(Computable other) {
        return null;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
