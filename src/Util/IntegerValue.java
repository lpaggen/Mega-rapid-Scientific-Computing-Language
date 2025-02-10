package Util;

import DataTypes.Computable;

public class IntegerValue implements Computable {
    private final int value;

    public IntegerValue(int value) {
        this.value = value;
    }

    @Override
    public Computable add(Computable other) {
        if (other instanceof IntegerValue) {
            return new IntegerValue(this.value + ((IntegerValue) other).value);
        }
        throw new UnsupportedOperationException("Cannot add an IntegerValue to a non-IntegerValue");
    }

    @Override
    public Computable subtract(Computable other) {
        return null;
    }

    @Override
    public Computable multiply(Computable other) {
        if (other instanceof IntegerValue) {
            return new IntegerValue(this.value * ((IntegerValue) other).value);
        }
        throw new UnsupportedOperationException("Cannot multiply an IntegerValue with a non-IntegerValue");
    }

    @Override
    public Computable divide(Computable other) {
        return null;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
