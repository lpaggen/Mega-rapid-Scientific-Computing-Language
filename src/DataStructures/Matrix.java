//package DataStructures;
//
//import DataTypes.Computable;
//
//// have to implement a matrix structure, with capability for eigenvalue, inversion, det, mul, etc. etc
//// this is more complex than i initially expected it to be
//public class Matrix<T extends Computable> { // so apparently T is "type" (generic)
//    private final int rows, columns;
//    private final T[][] entries; // define as Object, specify later... might be "slow"
//
//    public Matrix(int rows, int columns) {
//        this.rows = rows;
//        this.columns = columns;
//        this.entries = (T[][]) new Computable[rows][columns];
//    }
//
//    public void set(int row, int col, T value) { // the Object class is used here because I need support for symbols AND Numbers
//        if (row >= rows || col >= columns || row < 0 || col < 0) {
//            throw new IndexOutOfBoundsException("Invalid index <" + col + ", " + row + "> for <" + this.rows + "x" + this.columns + "> matrix");
//        }
//        entries[row][col] = value;
//    }
//
//    @Override
//    public String toString() { // this is kinda slow, we might want to optimize this later
//        StringBuilder sb = new StringBuilder();
//        sb.append('[');
//        for (int i = 0; i < rows; i++) {
//            sb.append("[");
//            for (int j = 0; j < columns; j++) {
//                sb.append(entries[i][j] == null ? "null" : entries[i][j].toString());
//                if (j < columns - 1) sb.append(", ");
//            }
//            sb.append("]");
//            if (i < rows - 1) sb.append(", ");
//        }
//        sb.append(']');
//        return sb.toString();
//    }
//
//    // any operation on matrices should return a matrix type
//    public Matrix multiply(Matrix A, Matrix B) {
//        int rowsA = A.rows;
//        int colsA = A.columns;
//        int rowsB = B.rows;
//        int colsB = B.columns;
//
//        if (rowsA != colsB) {
//            throw new IllegalArgumentException("Matrix multiplication not possible: Columns of A (" + colsA + ") must match rows of B (" + rowsB + ").");
//        }
//
//        Object[][] result = new Object[rowsA][colsB]; // still object type
//
//        // main computation loop, I will set up some (minor) optimizations later down the line, maybe not
//        // currently O(n^3) is a bit slow - "slow" for my purposes should be just fine
//        // there is an additional degree of complexity here; a matrix needs to handle symbols AND Numbers
//        for (int i = 0; i < rowsA; i++) {
//            for (int j = 0; j < colsB; j++) {
//                Object sum = 0; // could be a symbol ...
//                for (int k = 0; k < colsA; k++) {
//                    Object elementA = A.entries[i][k]; // this would get the contents of the matrix, parse int and sym and float
//                    Object elementB = B.entries[k][j];
//                    if (elementA instanceof Integer && elementB instanceof Integer) {
//                        sum = (Integer) sum + (Integer) elementA + (Integer) elementB; // we could handle all in floats too
//                    } else if (elementA instanceof Float && elementB instanceof Float) {
//                        sum = (Float) sum + (Float) elementA + (Float) elementB;
//                    } else if (elementA instanceof Expression && elementB instanceof Expression) {
//                        sum = new Expression(); // this is a big expression, initialize a fresh one
//                    }
//                }
//            }
//        }
//        return null;
//    }
//}
