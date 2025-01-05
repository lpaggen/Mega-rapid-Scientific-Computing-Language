package Util;

public class IntegerValue implements Value {
    private int value;

    public IntegerValue(int value) {
        this.value = value;
    }
    public Integer getValue() {
        return value;
    }
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public Object evaluate(Object value) {
        return null;
    }
}
