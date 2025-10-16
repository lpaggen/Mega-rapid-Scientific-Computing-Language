package AST.Nodes.DataStructures;

import AST.Nodes.Conditional.BooleanNode;
import AST.Nodes.DataTypes.Scalar;
import AST.Nodes.Expressions.BinaryOperations.Arithmetic.Mul;
import AST.Nodes.Expressions.Expression;
import Interpreter.Runtime.Environment;
import Interpreter.Tokenizer.TokenKind;
import Util.WarningLogger;

import java.util.Iterator;

public class Matrix extends Expression implements MatrixLike {
    private final Expression[][] elements;
    int numRows;
    int numCols;
    private final TokenKind type;
    private static final double EPSILON = 1e-10;  // used as tol for stability
    private WarningLogger warningLogger = new WarningLogger();
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

    // sets a row starting from startCol, no need to default to 0
    public void setRow(int row, int startCol, MatrixLike newRow) {
        if (newRow.cols() + startCol > numCols) {
            throw new RuntimeException("New row exceeds matrix dimensions.");
        }
        for (int j = 0; j < newRow.cols(); j++) {
            elements[row][startCol + j] = newRow.get(0, j);
        }
    }

    public void setColumn(int col, int startRow, MatrixLike newCol) {
        if (newCol.rows() + startRow > numRows) {
            throw new RuntimeException("New column exceeds matrix dimensions.");
        }
        for (int i = 0; i < newCol.rows(); i++) {
            elements[startRow + i][col] = newCol.get(i, 0);
        }
    }

    @Override
    public boolean isEmpty() {
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
    public MatrixLike getColumns(int... col) {
        return null;
    }

    @Override
    public MatrixLike getRows(int... row) {
        return null;
    }

    public Matrix getRow(int row) {
        Expression[][] rowElements = new Expression[1][numCols];
        System.arraycopy(elements[row], 0, rowElements[0], 0, numCols);
        return new Matrix(rowElements, type);
    }

    public Matrix sliceRow(int startCol, int endCol) {
        if (startCol < 0 || endCol > numCols || startCol >= endCol) {
            throw new RuntimeException("Invalid column slice indices.");
        }
        Expression[][] slicedElements = new Expression[1][endCol - startCol];
        System.arraycopy(this.getRow(0).elements[0], startCol, slicedElements[0], 0, endCol - startCol);
        return new Matrix(slicedElements, type);
    }

    public Matrix sliceColumn(int startRow, int endRow) {
        if (startRow < 0 || endRow > numRows || startRow >= endRow) {
            throw new RuntimeException("Invalid row slice indices.");
        }
        Expression[][] slicedElements = new Expression[endRow - startRow][1];
        for (int i = startRow; i < endRow; i++) {
            slicedElements[i - startRow][0] = this.getColumn(0).elements[i][0];
        }
        return new Matrix(slicedElements, type);
    }

    public Matrix getColumn(int col) {
        Expression[][] colElements = new Expression[numRows][1];
        for (int i = 0; i < numRows; i++) {
            colElements[i][0] = elements[i][col];
        }
        return new Matrix(colElements, type);
    }

    @Override
    public Expression[] getElements() {
       throw new UnsupportedOperationException("Matrix does not support getElements(). Use get(row, col) instead.");
    }

    @Override
    public Matrix dot(Expression left, Expression right) {
        return null;
    }

    @Override
    public Matrix outer(Expression left, Expression right) {
        return null;
    }

    @Override
    public Matrix pow(Expression exponent) {
        return null;
    }

    @Override
    public Matrix transpose() {
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
    public TokenKind getType(Environment env) {
        return type;
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
            } else if ((evaluated.getType(env) == TokenKind.SCALAR) && expectedType == TokenKind.BOOLEAN) {
                evaluated = BooleanNode.fromNumeric((Scalar) evaluated);
                warningLogger.addWarning(1, "Implicit conversion from numeric to boolean in matrix at position " + position, -1);
                warningLogger.logWarningsToFile();
            } else if (evaluated.getType(env) != expectedType) {
                throw new RuntimeException("Matrix type mismatch: expected "
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

    private boolean dimensionsMatch(Matrix other) {
        return this.numRows == other.numRows && this.numCols == other.numCols;
    }

    // will make an operator for this eventually? not sure
    public Matrix inverse() {
        throw new UnsupportedOperationException("Matrix inversion not implemented yet.");
    }

    public Matrix identity(int size) {
        throw new UnsupportedOperationException("Matrix identity not implemented yet.");
    }

    public Matrix zeros(int rows, int cols) {
        throw new UnsupportedOperationException("Matrix zeros not implemented yet.");
    }

    private boolean isSquare() {
        return numRows == numCols;
    }

    // we should pass by copy, so we can use in other methods easily the results
    // WARNING this isn't the LU decomposition, just the two triangular matrices
    public Matrix lowerTriangular() {
        Matrix newMatrix = this.clone();
        if (!isSquare()) {
            throw new RuntimeException("Matrix must be square to get lower triangular.");
        }
        for (int i = 0; i < numRows; i++) {  // number of rows, start at 1 to skip first row
            for (int j = i + 1; j < numCols; j++) {
                newMatrix.elements[i][j] = new Scalar(0); // assuming integer zero, could be float zero too
            }
        }
        return newMatrix;
    }

    public Matrix upperTriangular() {
        Matrix newMatrix = this.clone();
        if (!isSquare()) {
            throw new RuntimeException("Matrix must be square to get upper triangular.");
        }
        for (int i = 1; i < numRows; i++) {  // number of rows
            for (int j = 0; j < i; j++) {    // up to the diagonal
                newMatrix.elements[i][j] = new Scalar(0); // assuming integer zero, could be float zero too
            }
        }
        return newMatrix;
    }

    public Matrix trace() {
        if (!isSquare()) {
            throw new RuntimeException("Matrix must be square to compute trace.");
        }
        Expression sum = new Scalar(0); // assuming integer zero, could be float zero too
        for (int i = 0; i < numRows; i++) {
            sum = Matrix.add(sum, elements[i][i]);
        }
        throw new UnsupportedOperationException("Matrix trace not implemented yet.");
    }

    public Matrix Identity(int size) {
        Expression[][] identityElements = new Expression[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == j) {
                    identityElements[i][j] = new Scalar(1); // assuming integer one, could be float one too
                } else {
                    identityElements[i][j] = new Scalar(0);
                }
            }
        }
        return new Matrix(identityElements, TokenKind.SCALAR);
    }

    // maybe return a BinaryNode
    // it makes more sense, the node then holds the two matrices and can evaluate them
    public Mul DoolittleLUDecomposition() {
        // i don't want to allow pseudo-inverse or anything like that, it's all bullshit
        if (!isSquare()) {
            throw new RuntimeException("Matrix must be square for LU decomposition.");
        }
        // initialize L and U
        Matrix L = Identity(numRows);
        Matrix U = this.clone();  // just need shallow copy
        for (int i = 0; i < numCols; i++) {
            for (int j = i + 1; j < numRows; j++) {
                if (U.elements[i][i] instanceof Scalar zeroCheck && Math.abs(zeroCheck.getDoubleValue()) < 1e-10) {
                    throw new RuntimeException("Matrix is singular, cannot perform LU decomposition.");
                }
                Expression factor = Matrix.div(U.elements[j][i], U.elements[i][i]);
                L.elements[j][i] = factor;
                for (int k = i; k < numCols; k++) {
                    U.elements[j][k] = Matrix.sub(U.elements[j][k], Matrix.mul(factor, U.elements[i][k]));
                }
            }
        }
        return new Mul(L, U);  // should be super handy because we can getLeft or getRight
    }

    private Matrix matrixEqualsScalar(Scalar scalar) {
        Expression[][] out = new Expression[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (!this.elements[i][j].equals(scalar)) {
                    out[i][j] = new BooleanNode(false);
                } else {
                    out[i][j] = new BooleanNode(true);
                }
            }
        }
        return new Matrix(out, TokenKind.BOOLEAN);
    }

    // need to make this recursive for matrices of matrices, will do later
    private Matrix matrixGreaterThanScalar(Scalar scalar) {
        Expression[][] out = new Expression[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (this.elements[i][j] instanceof Scalar current && scalar instanceof Scalar s) {
                    if (current.getDoubleValue() > s.getDoubleValue()) {
                        out[i][j] = new BooleanNode(true);
                    } else {
                        out[i][j] = new BooleanNode(false);
                    }
                } else {
                    throw new RuntimeException("Unable to compare elements: " + this.elements[i][j] + " and " + scalar + " at position (" + i + ", " + j + ")");
                }
            }
        }
        return new Matrix(out, TokenKind.BOOLEAN);
    }

    private Matrix matrixLessThanScalar(Scalar scalar) {
        Expression[][] out = new Expression[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (this.elements[i][j] instanceof Scalar current && scalar instanceof Scalar s) {
                    if (current.getDoubleValue() < s.getDoubleValue()) {
                        out[i][j] = new BooleanNode(true);
                    } else {
                        out[i][j] = new BooleanNode(false);
                    }
                } else {
                    throw new RuntimeException("Unable to compare elements: " + this.elements[i][j] + " and " + scalar + " at position (" + i + ", " + j + ")");
                }
            }
        }
        return new Matrix(out, TokenKind.BOOLEAN);
    }

    private Matrix matrixLessEqualThanScalar(Scalar scalar) {
        Expression[][] out = new Expression[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (this.elements[i][j] instanceof Scalar current && scalar instanceof Scalar s) {
                    if (current.getDoubleValue() <= s.getDoubleValue()) {
                        out[i][j] = new BooleanNode(true);
                    } else {
                        out[i][j] = new BooleanNode(false);
                    }
                } else {
                    throw new RuntimeException("Unable to compare elements: " + this.elements[i][j] + " and " + scalar + " at position (" + i + ", " + j + ")");
                }
            }
        }
        return new Matrix(out, TokenKind.BOOLEAN);
    }

    private Matrix matrixGreaterEqualThanScalar(Scalar scalar) {
        Expression[][] out = new Expression[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (this.elements[i][j] instanceof Scalar current && scalar instanceof Scalar s) {
                    if (current.getDoubleValue() >= s.getDoubleValue()) {
                        out[i][j] = new BooleanNode(true);
                    } else {
                        out[i][j] = new BooleanNode(false);
                    }
                } else {
                    throw new RuntimeException("Unable to compare elements: " + this.elements[i][j] + " and " + scalar + " at position (" + i + ", " + j + ")");
                }
            }
        }
        return new Matrix(out, TokenKind.BOOLEAN);
    }

    private Matrix matrixNotEqualThanScalar(Scalar scalar) {
        Expression[][] out = new Expression[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (this.elements[i][j] instanceof Scalar current && scalar instanceof Scalar s) {
                    if (current.getDoubleValue() != s.getDoubleValue()) {
                        out[i][j] = new BooleanNode(true);
                    } else {
                        out[i][j] = new BooleanNode(false);
                    }
                } else {
                    throw new RuntimeException("Unable to compare elements: " + this.elements[i][j] + " and " + scalar + " at position (" + i + ", " + j + ")");
                }
            }
        }
        return new Matrix(out, TokenKind.BOOLEAN);
    }

////     by default, the matrix operation will return a matrix of booleans
//    public Matrix equals(Expression other) {
//        Expression[][] out = new Expression[numRows][numCols];
//        if (other instanceof Scalar scalar) {
//            return matrixEqualsScalar(scalar);
//        }
//        // compare matrix to matrix
//        Matrix otherMat = other instanceof Matrix ? (Matrix) other : null;
//        if (!matrixDimensionsMatch(otherMat)) {
//            throw new RuntimeException("Matrix dimension mismatch for comparison: left is " +
//                    this.numRows + "x" + this.numCols + ", right is " +
//                    otherMat.numRows + "x" + otherMat.numCols);
//        }
//        for (int i = 0; i < numRows; i++) {
//            for (int j = 0; j < numCols; j++) {
//                if (!this.elements[i][j].equals(otherMat.elements[i][j])) {
//                    out[i][j] = new BooleanNode(false);
//                } else {
//                    out[i][j] = new BooleanNode(true);
//                }
//            }
//        }
//        return new Matrix(out, TokenKind.BOOLEAN);
//    }

    // handle through static method, with recursion for block matrices
    public static Matrix greater(Expression left, Expression right) {
        Matrix leftMat = left instanceof Matrix ? (Matrix) left : null;
        Matrix rightMat = right instanceof Matrix ? (Matrix) right : null;
        Scalar leftConst = left instanceof Scalar ? (Scalar) left : null;
        Scalar rightConst = right instanceof Scalar ? (Scalar) right : null;
        int rows;
        int cols;
        // check if the types are legal on either side
        if (leftMat == null && leftConst == null) {
            throw new RuntimeException("Left operand must be a Matrix or Scalar for comparison, got: " + left.getClass().getSimpleName());
        } else if (rightMat == null && rightConst == null) {
            throw new RuntimeException("Right operand must be a Matrix or Scalar for comparison, got: " + right.getClass().getSimpleName());
        }
        Expression result;
        if (leftMat != null) {
            rows = leftMat.rows();
            cols = leftMat.cols();
        } else if (rightMat != null) {
            cols = rightMat.cols();
            rows = rightMat.rows();
        } else {  // both scalars -- don't know if this would ever be needed, but in case keep it
            System.out.println("Comparing two constants: " + leftConst + " and " + rightConst);
            result = new BooleanNode(leftConst.getDoubleValue() > rightConst.getDoubleValue());
            Expression[][] singleElement = new Expression[1][1];
            singleElement[0][0] = result;
            return new Matrix(singleElement, TokenKind.BOOLEAN);
        }
        Expression[][] out = new Expression[rows][cols];
        if (rightConst != null) {  // matrix on left, constant on right
            return leftMat.matrixGreaterThanScalar(rightConst);
        } else if (leftConst != null) {
            return rightMat.matrixGreaterThanScalar(leftConst);
        }
        for (int i = 0; i < leftMat.numRows; i++) {
            for (int j = 0; j < leftMat.numCols; j++) {
                if (leftMat.elements[i][j] instanceof Scalar leftC && rightMat.elements[i][j] instanceof Scalar rightC) {
                    out[i][j] = new BooleanNode(leftC.getDoubleValue() > rightC.getDoubleValue());
                } else if (leftMat.elements[i][j] instanceof Matrix || rightMat.elements[i][j] instanceof Matrix) {
                    out[i][j] = Matrix.greater(leftMat.elements[i][j], rightMat.elements[i][j]);
                } else {
                    throw new RuntimeException("Unable to compare elements: " + leftMat.elements[i][j] + " and " + rightMat.elements[i][j] + " at position (" + i + ", " + j + ")");
                }
            }
        }
        return new Matrix(out, TokenKind.BOOLEAN);
    }

    public static Matrix less(Expression left, Expression right) {
        Matrix leftMat = left instanceof Matrix ? (Matrix) left : null;
        Matrix rightMat = right instanceof Matrix ? (Matrix) right : null;
        Scalar leftConst = left instanceof Scalar ? (Scalar) left : null;
        Scalar rightConst = right instanceof Scalar ? (Scalar) right : null;
        int rows;
        int cols;
        // check if the types are legal on either side
        if (leftMat == null && leftConst == null) {
            throw new RuntimeException("Left operand must be a Matrix or Scalar for comparison, got: " + left.getClass().getSimpleName());
        } else if (rightMat == null && rightConst == null) {
            throw new RuntimeException("Right operand must be a Matrix or Scalar for comparison, got: " + right.getClass().getSimpleName());
        }
        Expression result;
        if (leftMat != null) {
            rows = leftMat.rows();
            cols = leftMat.cols();
        } else if (rightMat != null) {
            cols = rightMat.cols();
            rows = rightMat.rows();
        } else {  // both scalars -- don't know if this would ever be needed, but in case keep it
            System.out.println("Comparing two constants: " + leftConst + " and " + rightConst);
            result = new BooleanNode(leftConst.getDoubleValue() < rightConst.getDoubleValue());
            Expression[][] singleElement = new Expression[1][1];
            singleElement[0][0] = result;
            return new Matrix(singleElement, TokenKind.BOOLEAN);
        }
        Expression[][] out = new Expression[rows][cols];
        if (rightConst != null) {  // matrix on left, constant on right
            return leftMat.matrixLessThanScalar(rightConst);
        } else if (leftConst != null) {
            return rightMat.matrixLessThanScalar(leftConst);
        }
        for (int i = 0; i < leftMat.numRows; i++) {
            for (int j = 0; j < leftMat.numCols; j++) {
                if (leftMat.elements[i][j] instanceof Scalar leftC && rightMat.elements[i][j] instanceof Scalar rightC) {
                    out[i][j] = new BooleanNode(leftC.getDoubleValue() < rightC.getDoubleValue());
                } else if (leftMat.elements[i][j] instanceof Matrix || rightMat.elements[i][j] instanceof Matrix) {
                    out[i][j] = Matrix.less(leftMat.elements[i][j], rightMat.elements[i][j]);
                } else {
                    throw new RuntimeException("idk man, sorry.");
                }
            }
        }
        return new Matrix(out, TokenKind.BOOLEAN);
    }

    public static Matrix lessEqual(Expression left, Expression right) {
        Matrix leftMat = left instanceof Matrix ? (Matrix) left : null;
        Matrix rightMat = right instanceof Matrix ? (Matrix) right : null;
        Scalar leftConst = left instanceof Scalar ? (Scalar) left : null;
        Scalar rightConst = right instanceof Scalar ? (Scalar) right : null;
        int rows;
        int cols;
        // check if the types are legal on either side
        if (leftMat == null && leftConst == null) {
            throw new RuntimeException("Left operand must be a Matrix or Scalar for comparison, got: " + left.getClass().getSimpleName());
        } else if (rightMat == null && rightConst == null) {
            throw new RuntimeException("Right operand must be a Matrix or Scalar for comparison, got: " + right.getClass().getSimpleName());
        }
        Expression result;
        if (leftMat != null) {
            rows = leftMat.rows();
            cols = leftMat.cols();
        } else if (rightMat != null) {
            cols = rightMat.cols();
            rows = rightMat.rows();
        } else {  // both scalars -- don't know if this would ever be needed, but in case keep it
            System.out.println("Comparing two constants: " + leftConst + " and " + rightConst);
            result = new BooleanNode(leftConst.getDoubleValue() <= rightConst.getDoubleValue());
            Expression[][] singleElement = new Expression[1][1];
            singleElement[0][0] = result;
            return new Matrix(singleElement, TokenKind.BOOLEAN);
        }
        Expression[][] out = new Expression[rows][cols];
        if (rightConst != null) {  // matrix on left, constant on right
            return leftMat.matrixLessEqualThanScalar(rightConst);
        } else if (leftConst != null) {
            return rightMat.matrixLessEqualThanScalar(leftConst);
        }
        for (int i = 0; i < leftMat.numRows; i++) {
            for (int j = 0; j < leftMat.numCols; j++) {
                if (leftMat.elements[i][j] instanceof Scalar leftC && rightMat.elements[i][j] instanceof Scalar rightC) {
                    out[i][j] = new BooleanNode(leftC.getDoubleValue() <= rightC.getDoubleValue());
                } else if (leftMat.elements[i][j] instanceof Matrix || rightMat.elements[i][j] instanceof Matrix) {
                    out[i][j] = Matrix.lessEqual(leftMat.elements[i][j], rightMat.elements[i][j]);
                } else {
                    throw new RuntimeException("idk man, sorry.");
                }
            }
        }
        return new Matrix(out, TokenKind.BOOLEAN);
    }

    public static Matrix greaterEqual(Expression left, Expression right) {
        Matrix leftMat = left instanceof Matrix ? (Matrix) left : null;
        Matrix rightMat = right instanceof Matrix ? (Matrix) right : null;
        Scalar leftConst = left instanceof Scalar ? (Scalar) left : null;
        Scalar rightConst = right instanceof Scalar ? (Scalar) right : null;
        int rows;
        int cols;
        // check if the types are legal on either side
        if (leftMat == null && leftConst == null) {
            throw new RuntimeException("Left operand must be a Matrix or Scalar for comparison, got: " + left.getClass().getSimpleName());
        } else if (rightMat == null && rightConst == null) {
            throw new RuntimeException("Right operand must be a Matrix or Scalar for comparison, got: " + right.getClass().getSimpleName());
        }
        Expression result;
        if (leftMat != null) {
            rows = leftMat.rows();
            cols = leftMat.cols();
        } else if (rightMat != null) {
            cols = rightMat.cols();
            rows = rightMat.rows();
        } else {  // both scalars -- don't know if this would ever be needed, but in case keep it
            System.out.println("Comparing two constants: " + leftConst + " and " + rightConst);
            result = new BooleanNode(leftConst.getDoubleValue() >= rightConst.getDoubleValue());
            Expression[][] singleElement = new Expression[1][1];
            singleElement[0][0] = result;
            return new Matrix(singleElement, TokenKind.BOOLEAN);
        }
        Expression[][] out = new Expression[rows][cols];
        if (rightConst != null) {  // matrix on left, constant on right
            return leftMat.matrixGreaterEqualThanScalar(rightConst);
        } else if (leftConst != null) {
            return rightMat.matrixGreaterEqualThanScalar(leftConst);
        }
        for (int i = 0; i < leftMat.numRows; i++) {
            for (int j = 0; j < leftMat.numCols; j++) {
                if (leftMat.elements[i][j] instanceof Scalar leftC && rightMat.elements[i][j] instanceof Scalar rightC) {
                    out[i][j] = new BooleanNode(leftC.getDoubleValue() >= rightC.getDoubleValue());
                } else if (leftMat.elements[i][j] instanceof Matrix || rightMat.elements[i][j] instanceof Matrix) {
                    out[i][j] = Matrix.greaterEqual(leftMat.elements[i][j], rightMat.elements[i][j]);
                } else {
                    throw new RuntimeException("idk man, sorry.");
                }
            }
        }
        return new Matrix(out, TokenKind.BOOLEAN);
    }

    public static Matrix equal(Expression left, Expression right) {
        System.out.println("Comparing two expressions for equality: " + left + " and " + right);
        Matrix leftMat = left instanceof Matrix ? (Matrix) left : null;
        Matrix rightMat = right instanceof Matrix ? (Matrix) right : null;
        Scalar leftConst = left instanceof Scalar ? (Scalar) left : null;
        Scalar rightConst = right instanceof Scalar ? (Scalar) right : null;
        int rows;
        int cols;
        // check if the types are legal on either side
        if (leftMat == null && leftConst == null) {
            throw new RuntimeException("Left operand must be a Matrix or Scalar for comparison, got: " + left.getClass().getSimpleName());
        } else if (rightMat == null && rightConst == null) {
            throw new RuntimeException("Right operand must be a Matrix or Scalar for comparison, got: " + right.getClass().getSimpleName());
        }
        Expression result;
        if (leftMat != null) {
            rows = leftMat.rows();
            cols = leftMat.cols();
        } else if (rightMat != null) {
            cols = rightMat.cols();
            rows = rightMat.rows();
        } else {  // both scalars -- don't know if this would ever be needed, but in case keep it
            System.out.println("Comparing two constants: " + leftConst + " and " + rightConst);
            result = new BooleanNode(leftConst.getDoubleValue() == rightConst.getDoubleValue());
            Expression[][] singleElement = new Expression[1][1];
            singleElement[0][0] = result;
            return new Matrix(singleElement, TokenKind.BOOLEAN);
        }
        Expression[][] out = new Expression[rows][cols];
        if (rightConst != null) {  // matrix on left, constant on right
            return leftMat.matrixEqualsScalar(rightConst);
        } else if (leftConst != null) {
            return rightMat.matrixEqualsScalar(leftConst);
        }
        for (int i = 0; i < leftMat.numRows; i++) {
            for (int j = 0; j < leftMat.numCols; j++) {
                if (leftMat.elements[i][j] instanceof Scalar leftC && rightMat.elements[i][j] instanceof Scalar rightC) {
                    out[i][j] = new BooleanNode(leftC.getDoubleValue() == rightC.getDoubleValue());
                } else if (leftMat.elements[i][j] instanceof Matrix || rightMat.elements[i][j] instanceof Matrix) {
                    out[i][j] = Matrix.equal(leftMat.elements[i][j], rightMat.elements[i][j]);
                } else {
                    throw new RuntimeException("idk man, sorry.");
                }
            }
        }
        return new Matrix(out, TokenKind.BOOLEAN);
    }

    public static Matrix notEqual(Expression left, Expression right) {
        Matrix leftMat = left instanceof Matrix ? (Matrix) left : null;
        Matrix rightMat = right instanceof Matrix ? (Matrix) right : null;
        Scalar leftConst = left instanceof Scalar ? (Scalar) left : null;
        Scalar rightConst = right instanceof Scalar ? (Scalar) right : null;
        int rows;
        int cols;
        // check if the types are legal on either side
        if (leftMat == null && leftConst == null) {
            throw new RuntimeException("Left operand must be a Matrix or Scalar for comparison, got: " + left.getClass().getSimpleName());
        } else if (rightMat == null && rightConst == null) {
            throw new RuntimeException("Right operand must be a Matrix or Scalar for comparison, got: " + right.getClass().getSimpleName());
        }
        Expression result;
        if (leftMat != null) {
            rows = leftMat.rows();
            cols = leftMat.cols();
        } else if (rightMat != null) {
            cols = rightMat.cols();
            rows = rightMat.rows();
        } else {  // both scalars -- don't know if this would ever be needed, but in case keep it
            System.out.println("Comparing two constants: " + leftConst + " and " + rightConst);
            result = new BooleanNode(leftConst.getDoubleValue() != rightConst.getDoubleValue());
            Expression[][] singleElement = new Expression[1][1];
            singleElement[0][0] = result;
            return new Matrix(singleElement, TokenKind.BOOLEAN);
        }
        Expression[][] out = new Expression[rows][cols];
        if (rightConst != null) {  // matrix on left, constant on right
            return leftMat.matrixNotEqualThanScalar(rightConst);
        } else if (leftConst != null) {
            return rightMat.matrixGreaterThanScalar(leftConst);
        }
        for (int i = 0; i < leftMat.numRows; i++) {
            for (int j = 0; j < leftMat.numCols; j++) {
                if (leftMat.elements[i][j] instanceof Scalar leftC && rightMat.elements[i][j] instanceof Scalar rightC) {
                    out[i][j] = new BooleanNode(leftC.getDoubleValue() != rightC.getDoubleValue());
                } else if (leftMat.elements[i][j] instanceof Matrix || rightMat.elements[i][j] instanceof Matrix) {
                    out[i][j] = Matrix.notEqual(leftMat.elements[i][j], rightMat.elements[i][j]);
                } else {
                    throw new RuntimeException("Unable to compare elements: " + leftMat.elements[i][j] + " and " + rightMat.elements[i][j] + " at position (" + i + ", " + j + ")");
                }
            }
        }
        return new Matrix(out, TokenKind.BOOLEAN);
    }

//    public LinalgMul PLUDecomposition() {
//        if (!isSquare()) {
//            throw new RuntimeException("Matrix must be square for LU decomposition.");
//        }
//        Matrix L = Identity(numRows);
//        Matrix U = this.clone();  // just need shallow copy
//        Matrix P = Identity(numRows);  // permutation matrix
//        // first step is to pivot the rows
//        // we do this by finding the max in each column and swapping rows
//        for (int i = 0; i < numCols; i++) {
//            int idxMax = 0;  // need to keep track of where the max is
//            // find the max
//            for (int j = i; j < numRows; j++) {  // remember to skip whatever is below pivot row
//                Scalar candidate = (Scalar) U.elements[j][i];  // won't work if not constant, but should be fine for now
//                Scalar currentMax = (Scalar) U.elements[idxMax][i];
//                if (j == i || (candidate != null && currentMax != null && Math.abs(candidate.getDoubleValue()) > Math.abs(currentMax.getDoubleValue()))) {
//                    idxMax = j;
//                }
//            }
//            // found the row to pivot, now swap to Rj (where j is the current column number)
//            MatrixLike toSwap = U.getRow(i);
//            U.setRow(i, 0, U.getRow(idxMax));  // replace first row with max row
//            U.setRow(idxMax, 0, toSwap);       // replace max row with first row
//            for (int j = i; j < numRows; j++) {  // eliminate lower columns
//                double denom = ((Scalar) U.elements[i][i]).getDoubleValue();
//                if (Math.abs(denom) < 1e-10) {
//                    throw new RuntimeException("Matrix is singular, cannot perform LU decomposition.");
//                }
//                Expression factor = Matrix.div(U.elements[j][i], U.elements[i][i]);
//                Matrix currentRow = U.getRow(j);
//                // now subtract factor * pivot row from current row
//                setRow(j, 0, currentRow * Matrix.sub(new Scalar(1, false), factor) - U.getRow(i) * factor);
//            }
//        }
//    }

//    public LinalgMul LUDecomposition() {
//        if (!isSquare()) {
//            throw new RuntimeException("Matrix must be square for LU decomposition.");
//        }
//        Matrix L = Identity(numRows);
//        Matrix U = this.clone();  // just need shallow copy
//        for (int i = 0; i < numCols; i++) {
//            int idxMax = 0;  // need to keep track of where the max is
//            for (int j = i; j < numRows; j++) {  // remember to skip whatever is below pivot row
//                idxMax = j == i || (U.elements[j][i] instanceof Scalar currentMax && U.elements[idxMax][i] instanceof Scalar previousMax
//                        && Math.abs(currentMax.getDoubleValue()) > Math.abs(previousMax.getDoubleValue())) ? j : idxMax;
//                // found the row to pivot, now swap to Rj (where j is the current column)
//                MatrixLike toSwap = U.getRow(i);  //
//                U.setRow(i, 0, U.getRow(idxMax));
//                U.setRow(idxMax, 0, toSwap);
//            } // done finding pivot row, now eliminate below
//            for (int j = i + 1; j < numRows; j++) {
//                if (U.elements[i][i] instanceof Scalar zeroCheck && Math.abs(zeroCheck.getDoubleValue()) < 1e-10) {
//                    throw new RuntimeException("Matrix is singular, cannot perform LU decomposition.");
//                }
//                Expression factor = Matrix.div(U.elements[j][i], U.elements[i][i]);
//                L.elements[j][i] = factor;
//                for (int k = i; k < numCols; k++) {
//                    U.elements[j][k] = Matrix.sub(U.elements[j][k], Matrix.mul(factor, U.elements[i][k]));
//                }
//            }
//        }
//        return new LinalgMul(L, U);  // should be super handy because we can getLeft or getRight
//    }

    private boolean matrixDimensionsMatch(Matrix other) {
        return this.numRows == other.numRows && this.numCols == other.numCols;
    }

    public static Expression sub(Expression left, Expression right) {
        Matrix leftMat = left instanceof Matrix ? (Matrix) left : null;
        Matrix rightMat = right instanceof Matrix ? (Matrix) right : null;
        Scalar leftConst = left instanceof Scalar ? (Scalar) left : null;
        Scalar rightConst = right instanceof Scalar ? (Scalar) right : null;
        int rows;
        int cols;
        TokenKind type;
        // check if the types are legal on either side
        if (leftMat == null && leftConst == null) {
            throw new RuntimeException("Left operand must be a Matrix or Scalar for subtraction, got: " + left.getClass().getSimpleName());
        } else if (rightMat == null && rightConst == null) {
            throw new RuntimeException("Right operand must be a Matrix or Scalar for subtraction, got: " + right.getClass().getSimpleName());
        }
        if (leftMat != null) {
            rows = leftMat.rows();
            cols = leftMat.cols();
            type = leftMat.getType();
        } else if (rightMat != null) {
            cols = rightMat.cols();
            rows = rightMat.rows();
            type = rightMat.getType();
        } else {  // both scalars
            return Scalar.subtract(leftConst, rightConst);
        }
        // this only applies if both are matrices, because a constant can ALWAYS be multiplied to the matrix
        if ((leftMat != null && rightMat !=null) && !(((Matrix) left).dimensionsMatch((Matrix) right))) {
            throw new RuntimeException("Matrix dimension mismatch for subtraction: left is " +
                    leftMat.rows() + "x" + leftMat.cols() + ", right is " +
                    rightMat.rows() + "x" + rightMat.cols());
        }
        Expression[][] resultElements = new Expression[rows][cols];
        for (int i = 0; i < rows; i++) {  // need to resolve the type of the elements every time
            for (int j = 0; j < cols; j++) {
                resultElements[i][j] = Matrix.sub(
                        leftMat != null ? leftMat.get(i, j) : leftConst,
                        rightMat != null ? rightMat.get(i, j) : rightConst);
            }
        }
        return new Matrix(resultElements, type);
    }

    // !!! hadamard (element-wise) division, not matrix division, that will come later
    public static Expression div(Expression left, Expression right) {
        Matrix leftMat = left instanceof Matrix ? (Matrix) left : null;
        Matrix rightMat = right instanceof Matrix ? (Matrix) right : null;
        Scalar leftConst = left instanceof Scalar ? (Scalar) left : null;
        Scalar rightConst = right instanceof Scalar ? (Scalar) right : null;
        int rows;
        int cols;
        TokenKind type;
        // check if the types are legal on either side
        if (leftMat == null && leftConst == null) {
            throw new RuntimeException("Left operand must be a Matrix or Scalar for Hadamard division, got: " + left.getClass().getSimpleName());
        } else if (rightMat == null && rightConst == null) {
            throw new RuntimeException("Right operand must be a Matrix or Scalar for Hadamard division, got: " + right.getClass().getSimpleName());
        }
        if (leftMat != null) {
            rows = leftMat.rows();
            cols = leftMat.cols();
            type = leftMat.getType();
        } else if (rightMat != null) {
            cols = rightMat.cols();
            rows = rightMat.rows();
            type = rightMat.getType();
        } else {  // both scalars
            return Scalar.divide(leftConst, rightConst);
        }
        // this only applies if both are matrices, because a constant can ALWAYS be multiplied to the matrix
        if ((leftMat != null && rightMat !=null) && !(((Matrix) left).dimensionsMatch((Matrix) right))) {
            throw new RuntimeException("Matrix dimension mismatch for Hadamard division: left is " +
                    leftMat.rows() + "x" + leftMat.cols() + ", right is " +
                    rightMat.rows() + "x" + rightMat.cols());
        }
        Expression[][] resultElements = new Expression[rows][cols];
        for (int i = 0; i < rows; i++) {  // need to resolve the type of the elements every time
            for (int j = 0; j < cols; j++) {
                resultElements[i][j] = Matrix.div(
                        leftMat != null ? leftMat.get(i, j) : leftConst,
                        rightMat != null ? rightMat.get(i, j) : rightConst);
            }
        }
        return new Matrix(resultElements, type);
    }

    public static Expression mul(Expression left, Expression right) {
        Matrix leftMat = left instanceof Matrix ? (Matrix) left : null;
        Matrix rightMat = right instanceof Matrix ? (Matrix) right : null;
        Scalar leftConst = left instanceof Scalar ? (Scalar) left : null;
        Scalar rightConst = right instanceof Scalar ? (Scalar) right : null;
        int rows;
        int cols;
        TokenKind type;
        // check if the types are legal on either side
        if (leftMat == null && leftConst == null) {
            throw new RuntimeException("Left operand must be a Matrix or Scalar for Hadamard product, got: " + left.getClass().getSimpleName());
        } else if (rightMat == null && rightConst == null) {
            throw new RuntimeException("Right operand must be a Matrix or Scalar for Hadamard product, got: " + right.getClass().getSimpleName());
        }
        if (leftMat != null) {
            rows = leftMat.rows();
            cols = leftMat.cols();
            type = leftMat.getType();
        } else if (rightMat != null) {
            cols = rightMat.cols();
            rows = rightMat.rows();
            type = rightMat.getType();
        } else {  // both scalars
            return Scalar.multiply(leftConst, rightConst);
        }
        // this only applies if both are matrices, because a constant can ALWAYS be multiplied to the matrix
        if ((leftMat != null && rightMat !=null) && !(((Matrix) left).dimensionsMatch((Matrix) right))) {
            throw new RuntimeException("Matrix dimension mismatch for Hadamard product: left is " +
                    leftMat.rows() + "x" + leftMat.cols() + ", right is " +
                    rightMat.rows() + "x" + rightMat.cols());
        }
        Expression[][] resultElements = new Expression[rows][cols];
        for (int i = 0; i < rows; i++) {  // need to resolve the type of the elements every time
            for (int j = 0; j < cols; j++) {
                resultElements[i][j] = Matrix.mul(
                        leftMat != null ? leftMat.get(i, j) : leftConst,
                        rightMat != null ? rightMat.get(i, j) : rightConst);
            }
        }
        return new Matrix(resultElements, type);
    }

    public static Expression add(Expression left, Expression right) {
        // check legal types -> Matrix or Scalar (more will come with the algebra)
        Matrix leftMat = left instanceof Matrix ? (Matrix) left : null;
        Matrix rightMat = right instanceof Matrix ? (Matrix) right : null;
        Scalar leftConst = left instanceof Scalar ? (Scalar) left : null;
        Scalar rightConst = right instanceof Scalar ? (Scalar) right : null;
        int rows;
        int cols;
        TokenKind type;
        // check if the types are legal on either side
        if (leftMat == null && leftConst == null) {
            throw new RuntimeException("Left operand must be a Matrix or Scalar for addition, got: " + left.getClass().getSimpleName());
        } else if (rightMat == null && rightConst == null) {
            throw new RuntimeException("Right operand must be a Matrix or Scalar for addition, got: " + right.getClass().getSimpleName());
        }
        if (leftMat != null) {
            rows = leftMat.rows();
            cols = leftMat.cols();
            type = leftMat.getType();
        } else if (rightMat != null) {
            cols = rightMat.cols();
            rows = rightMat.rows();
            type = rightMat.getType();
        } else {  // both scalars
            return Scalar.add(leftConst, rightConst);
        }
        System.out.println("Adding matrices of size " + rows + "x" + cols);
        // this only applies if both are matrices, because a constant can ALWAYS be added to the matrix
        if ((leftMat != null && rightMat !=null) && !(((Matrix) left).dimensionsMatch((Matrix) right))) {
            throw new RuntimeException("Matrix dimension mismatch for addition: left is " +
                    leftMat.rows() + "x" + leftMat.cols() + ", right is " +
                    rightMat.rows() + "x" + rightMat.cols());
        }
        Expression[][] resultElements = new Expression[rows][cols];
        for (int i = 0; i < rows; i++) {  // need to resolve the type of the elements every time
            for (int j = 0; j < cols; j++) {
                resultElements[i][j] = Matrix.add(
                        leftMat != null ? leftMat.get(i, j) : leftConst,
                        rightMat != null ? rightMat.get(i, j) : rightConst);
            }
        }
        return new Matrix(resultElements, type);
    }

    public Expression determinant() {
        if (!isSquare()) {
            throw new RuntimeException("Matrix must be square.");
        }
        int n = numRows;
        // 1x1
        if (n == 1) {
            return elements[0][0];
        }
        // 2x2
        if (n == 2) {
            return Matrix.sub(
                    Matrix.mul(elements[0][0], elements[1][1]),
                    Matrix.mul(elements[0][1], elements[1][0])
            );
        }
        Expression result = new Scalar(0); // accumulate sum
        for (int j = 0; j < n; j++) {
            Expression scalar = elements[0][j];
            // skip zero elements
            if (scalar instanceof Scalar zeroCheck && Math.abs(zeroCheck.getDoubleValue()) < 1e-10) {
                continue;
            }
            // create submatrix excluding row 0 and column j
            Expression[][] subElements = new Expression[n - 1][n - 1];
            for (int r = 1; r < n; r++) {
                int subColIndex = 0;
                for (int c = 0; c < n; c++) {
                    if (c == j) continue;
                    subElements[r - 1][subColIndex++] = elements[r][c];
                }
            }
            Matrix subMatrix = new Matrix(subElements, type);
            Expression subDet = subMatrix.determinant();  // recursive call
            Expression term = Matrix.mul(scalar, subDet);
            // alternate signs
            if (j % 2 == 0) {
                result = Matrix.add(result, term);
            } else {
                result = Matrix.sub(result, term);
            }
        }
        return result;
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
                sb.append(", \n");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public Matrix clone() {
        Matrix expressions = (Matrix) super.clone();
        Expression[][] clonedElements = new Expression[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                clonedElements[i][j] = elements[i][j].clone();
            }
        }
        return new Matrix(clonedElements, type);
    }
}
