package Util;

public class MatrixValue implements Value {
    private double[][] matrix;

    public MatrixValue(double[][] matrix) {
        this.matrix = matrix;
    }
    public double[][] getValue() {
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
