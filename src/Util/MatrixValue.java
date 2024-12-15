package Util;

public class MatrixValue implements Value {
    private double[][] matrix;

    public MatrixValue(double[][] matrix) {
        this.matrix = matrix;
    }
    public double[][] getValue() {
        return matrix;
    }
    public String toString() {
        return "Matrix[...]";
    }
}
