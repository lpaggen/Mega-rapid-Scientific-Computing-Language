package Util;

import DataTypes.Computable;

public class FloatValue implements Computable {
    private float value;

    public FloatValue(float value) {
        this.value = value;
    }

    @Override
    public Computable add(Computable other) {
        if (other instanceof FloatValue) {
            return new FloatValue(this.value + ((FloatValue) other).value);
        }
        throw new UnsupportedOperationException("Cannot add a FloatValue to a non-FloatValue");
    }

    @Override
    public Computable subtract(Computable other) {
        return null;
    }

    @Override
    public Computable multiply(Computable other) {
        if (other instanceof FloatValue) {
            return new FloatValue(this.value * ((FloatValue) other).value);
        }
        throw new UnsupportedOperationException("Cannot multiply a FloatValue with a non-FloatValue");
    }

    @Override
    public Computable divide(Computable other) {
        return null;
    }

    @Override
    public String toString() {
        return Float.toString(value);
    }
}
