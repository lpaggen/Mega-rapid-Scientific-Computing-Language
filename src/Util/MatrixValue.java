package Util;

public class MatrixValue implements Value {
    private Object[][] matrix;

    public MatrixValue(Object[][] matrix) {
        this.matrix = matrix;
    }
    public Object[][] getValue() {
        return matrix;
    }

    @Override
    public String toString() {
        return "Matrix[...]"; // will need some more advanced tricks to make sure everything is formatted correctly...
    }

    @Override
    public Object evaluate(Object value) {
        return null;
    }
}
