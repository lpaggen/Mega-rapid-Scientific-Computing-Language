package Util;

public class FloatValue implements Value {
    private float value;

    public FloatValue(float value) {
        this.value = value;
    }
    public Float getValue() {
        return value;
    }
    public String toString() {
        return Float.toString(value);
    }

    @Override
    public Object evaluate(Object value) {
        return null;
    }
}
