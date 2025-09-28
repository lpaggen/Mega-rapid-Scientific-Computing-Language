package AST.Nodes.DataStructures;

import AST.Nodes.Expression;
import Interpreter.Runtime.Environment;
import Interpreter.Tokenizer.TokenKind;

import java.util.Iterator;

// this is fully compatible with Vector
// e.g. Matrix m = new Matrix(...); vs Vector v = new Vector(...);
public class Matrix extends Expression implements VectorLike {
    private final Expression[][] elements;
    int numRows;
    int numCols;
    private final TokenKind type;
    public Matrix(Expression[][] elements, TokenKind type) {
        this.elements = elements;
        this.type = type;
        this.numRows = elements.length;
        this.numCols = elements.length > 0 ? elements[0].length : 0;
    }

    @Override
    public int length() {
        throw new UnsupportedOperationException("Matrix does not support length(). Use rows() and cols() instead.");
    }

    @Override
    public Expression get(int index) {
        throw new UnsupportedOperationException("Matrix does not support single index access. Use get(row, col) instead.");
    }

    @Override
    public void set(int index, Expression element) {
        throw new UnsupportedOperationException("Matrix does not support single index access. Use set(row, col, element) instead.");
    }

    public void set(int row, int col, Expression element) {
        elements[row][col] = element;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Expression element) {
        return false;
    }

    @Override
    public TokenKind getType() {
        return null;
    }

    @Override
    public int rows() {
        return numRows;
    }

    @Override
    public int cols() {
        return numCols;
    }

    @Override
    public int[] shape() {
        return new int[]{numRows, numCols};
    }

    @Override
    public Expression get(int row, int col) {
        return elements[row][col];
    }

    @Override
    public VectorLike getColumns(int... col) {
        return null;
    }

    @Override
    public VectorLike getRows(int... row) {
        return null;
    }

    @Override
    public Expression[] getElements() {
       throw new UnsupportedOperationException("Matrix does not support getElements(). Use get(row, col) instead.");
    }

    @Override
    public VectorLike dot(Expression left, Expression right) {
        return null;
    }

    @Override
    public VectorLike outer(Expression left, Expression right) {
        return null;
    }

    @Override
    public VectorLike pow(Expression exponent) {
        return null;
    }

    @Override
    public VectorLike transpose() {
        Expression[][] transposed = new Expression[numCols][numRows];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                transposed[j][i] = elements[i][j];
            }
        }
        return new Matrix(transposed, type);
    }

    @Override
    public Iterator<Expression> iterator() {
        return new Iterator<>() {
            private int currentRow = 0;
            private int currentCol = 0;

            @Override
            public boolean hasNext() {
                return currentCol < cols() && currentRow < rows();
            }

            @Override
            public Expression next() {
                Expression element = elements[currentRow][currentCol];
                currentCol++;
                if (currentCol >= cols()) {
                    currentCol = 0;
                    currentRow++;
                }
                return element;
            }
        };
    }

    @Override
    public Expression evaluate(Environment env) {
        Expression[][] evaluatedElements = new Expression[numRows][numCols];
        TokenKind expectedType = type;
        int position = 0; // linear counter
        for (Expression elem : this) {
            Expression evaluated = elem.evaluate(env);
            if (expectedType == null) {
                expectedType = evaluated.getType(env);
            } else if (evaluated.getType(env) != expectedType) {
                throw new RuntimeException("Array type mismatch: expected "
                        + expectedType + ", got " + evaluated.getType(env)
                        + " in element " + evaluated + " at position " + position);
            }
            int row = position / numCols;
            int col = position % numCols;
            evaluatedElements[row][col] = evaluated;
            position++;
        }
        return new Matrix(evaluatedElements, expectedType);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < numRows; i++) {
            sb.append("[");
            for (int j = 0; j < numCols; j++) {
                sb.append(elements[i][j].toString());
                if (j < numCols - 1) {
                    sb.append(", ");
                }
            }
            sb.append("]");
            if (i < numRows - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
