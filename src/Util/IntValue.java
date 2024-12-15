package Util;

public class IntValue implements Value {
    private int value;

    public IntValue(int value) {
        this.value = value;
    }
    public Integer getValue() {
        return value;
    }
    public String toString() {
        return Integer.toString(value);
    }
}
