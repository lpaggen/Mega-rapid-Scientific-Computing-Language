//package Runtime;
//
//import AST.Expressions.Expression;
//import Lexer.TokenKind;
//import Util.WarningLogger;
//
//import java.util.Iterator;
//import java.util.function.BiFunction;
//
//public final class RuntimeMatrix implements MatrixLike {
//    private final Expression[][] elements;
//    int numRows;
//    int numCols;
//    private final TokenKind type;
//    private static final double EPSILON = 1e-10;  // used as tol for stability
//    //  private WarningLogger warningLogger = new WarningLogger();
//    public RuntimeMatrix(Expression[][] elements, TokenKind type) {
//        this.elements = elements;
//        this.type = type;
//        this.numRows = elements.length;
//        this.numCols = elements.length > 0 ? elements[0].length : 0;
//    }
//
//    public void set(int row, int col, Expression element) {
//        elements[row][col] = element;
//    }
//
//    // sets a row starting from startCol, no need to default to 0
//    public void setRow(int row, int startCol, MatrixLike newRow) {
//        if (newRow.cols() + startCol > numCols) {
//            throw new RuntimeException("New row exceeds Matrix dimensions.");
//        }
//        for (int j = 0; j < newRow.cols(); j++) {
//            RuntimeValue rv = newRow.get(0, j);
//            if (rv instanceof RuntimeScalar scalar) {
//                elements[row][startCol + j] = scalar.toExpression(); // unwrap
//            } else {
//                throw new RuntimeException("Cannot assign non-scalar RuntimeValue to RuntimeMatrix");
//            }
//        }
//    }
//
//    public void setColumn(int col, int startRow, MatrixLike newCol) {
//        if (newCol.rows() + startRow > numRows) {
//            throw new RuntimeException("New column exceeds RuntimeMatrix dimensions.");
//        }
//        for (int i = 0; i < newCol.rows(); i++) {
//            elements[startRow + i][col] = newCol.get(i, 0);
//        }
//    }
//
//    @Override
//    public boolean isEmpty() {
//        return false;
//    }
//
//    @Override
//    public TokenKind getType() {
//        return null;
//    }
//
//    @Override
//    public int rows() {
//        return numRows;
//    }
//
//    @Override
//    public int cols() {
//        return numCols;
//    }
//
//    @Override
//    public int[] shape() {
//        return new int[]{numRows, numCols};
//    }
//
//    @Override
//    public Expression get(int row, int col) {
//        return elements[row][col];
//    }
//
//    @Override
//    public MatrixLike getColumns(int... col) {
//        return null;
//    }
//
//    @Override
//    public MatrixLike getRows(int... row) {
//        return null;
//    }
//
//    public RuntimeMatrix getRow(int row) {
//        Expression[][] rowElements = new Expression[1][numCols];
//        System.arraycopy(elements[row], 0, rowElements[0], 0, numCols);
//        return new RuntimeMatrix(rowElements, type);
//    }
//
//    public RuntimeMatrix sliceRow(int startCol, int endCol) {
//        if (startCol < 0 || endCol > numCols || startCol >= endCol) {
//            throw new RuntimeException("Invalid column slice indices.");
//        }
//        Expression[][] slicedElements = new Expression[1][endCol - startCol];
//        System.arraycopy(this.getRow(0).elements[0], startCol, slicedElements[0], 0, endCol - startCol);
//        return new RuntimeMatrix(slicedElements, type);
//    }
//
//    public RuntimeMatrix sliceColumn(int startRow, int endRow) {
//        if (startRow < 0 || endRow > numRows || startRow >= endRow) {
//            throw new RuntimeException("Invalid row slice indices.");
//        }
//        Expression[][] slicedElements = new Expression[endRow - startRow][1];
//        for (int i = startRow; i < endRow; i++) {
//            slicedElements[i - startRow][0] = this.getColumn(0).elements[i][0];
//        }
//        return new RuntimeMatrix(slicedElements, type);
//    }
//
//    public RuntimeMatrix getColumn(int col) {
//        Expression[][] colElements = new Expression[numRows][1];
//        for (int i = 0; i < numRows; i++) {
//            colElements[i][0] = elements[i][col];
//        }
//        return new RuntimeMatrix(colElements, type);
//    }
//
//    @Override
//    public Expression[] getElements() {
//       throw new UnsupportedOperationException("RuntimeMatrix does not support getElements(). Use get(row, col) instead.");
//    }
//
//    @Override
//    public RuntimeMatrix dot(Expression left, Expression right) {
//        return null;
//    }
//
//    @Override
//    public RuntimeMatrix outer(Expression left, Expression right) {
//        return null;
//    }
//
//    @Override
//    public RuntimeMatrix pow(Expression exponent) {
//        return null;
//    }
//
//    @Override
//    public RuntimeMatrix transpose() {
//        Expression[][] transposed = new Expression[numCols][numRows];
//        for (int i = 0; i < numRows; i++) {
//            for (int j = 0; j < numCols; j++) {
//                transposed[j][i] = elements[i][j];
//            }
//        }
//        return new RuntimeMatrix(transposed, type);
//    }
//
//    @Override
//    public Iterator<Expression> iterator() {
//        return new Iterator<>() {
//            private int currentRow = 0;
//            private int currentCol = 0;
//
//            @Override
//            public boolean hasNext() {
//                return currentCol < cols() && currentRow < rows();
//            }
//
//            @Override
//            public Expression next() {
//                Expression element = elements[currentRow][currentCol];
//                currentCol++;
//                if (currentCol >= cols()) {
//                    currentCol = 0;
//                    currentRow++;
//                }
//                return element;
//            }
//        };
//    }
//
//    @Override
//    public TokenKind getType(Environment env) {
//        return type;
//    }
//
//    @Override
//    public Expression evaluate(Environment env) {
//        Expression[][] evaluatedElements = new Expression[numRows][numCols];
//        TokenKind expectedType = type;
//        int position = 0; // linear counter
//        for (Expression elem : this) {
//            Expression evaluated = elem.evaluate(env);
//            if (expectedType == null) {
//                expectedType = evaluated.getType(env);
//            } else if ((evaluated.getType(env) == TokenKind.RuntimeScalar) && expectedType == TokenKind.BOOLEAN) {
//                evaluated = BooleanNode.fromNumeric((RuntimeScalar) evaluated);
//                warningLogger.addWarning(1, "Implicit conversion from numeric to boolean in RuntimeMatrix at position " + position, -1);
//                warningLogger.logWarningsToFile();
//            } else if (evaluated.getType(env) != expectedType) {
//                throw new RuntimeException("RuntimeMatrix type mismatch: expected "
//                        + expectedType + ", got " + evaluated.getType(env)
//                        + " in element " + evaluated + " at position " + position);
//            }
//            int row = position / numCols;
//            int col = position % numCols;
//            evaluatedElements[row][col] = evaluated;
//            position++;
//        }
//        return new RuntimeMatrix(evaluatedElements, expectedType);
//    }
//
//    private boolean dimensionsMatch(RuntimeMatrix other) {
//        return this.numRows == other.numRows && this.numCols == other.numCols;
//    }
//
//    // will make an operator for this eventually? not sure
//    public RuntimeMatrix inverse() {
//        throw new UnsupportedOperationException("RuntimeMatrix inversion not implemented yet.");
//    }
//
//    public static RuntimeMatrix zeros(int rows, int cols) {
//        Expression[][] zeroElements = new Expression[rows][cols];
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < cols; j++) {
//                zeroElements[i][j] = new RuntimeScalar(0); // assuming integer zero, could be float zero too
//            }
//        }
//        return new RuntimeMatrix(zeroElements, TokenKind.RuntimeScalar);
//    }
//
//    private boolean isSquare() {
//        return numRows == numCols;
//    }
//
//    // we should pass by copy, so we can use in other methods easily the results
//    // WARNING this isn't the LU decomposition, just the two triangular matrices
//    public RuntimeMatrix lowerTriangular() {
//        RuntimeMatrix newRuntimeMatrix = this.clone();
//        if (!isSquare()) {
//            throw new RuntimeException("RuntimeMatrix must be square to get lower triangular.");
//        }
//        for (int i = 0; i < numRows; i++) {  // number of rows, start at 1 to skip first row
//            for (int j = i + 1; j < numCols; j++) {
//                newRuntimeMatrix.elements[i][j] = new RuntimeScalar(0); // assuming integer zero, could be float zero too
//            }
//        }
//        return newRuntimeMatrix;
//    }
//
//    public RuntimeMatrix upperTriangular() {
//        RuntimeMatrix newRuntimeMatrix = this.clone();
//        if (!isSquare()) {
//            throw new RuntimeException("RuntimeMatrix must be square to get upper triangular.");
//        }
//        for (int i = 1; i < numRows; i++) {  // number of rows
//            for (int j = 0; j < i; j++) {    // up to the diagonal
//                newRuntimeMatrix.elements[i][j] = new RuntimeScalar(0); // assuming integer zero, could be float zero too
//            }
//        }
//        return newRuntimeMatrix;
//    }
//
//    public RuntimeMatrix trace() {
//        if (!isSquare()) {
//            throw new RuntimeException("RuntimeMatrix must be square to compute trace.");
//        }
//        Expression sum = new RuntimeScalar(0); // assuming integer zero, could be float zero too
//        for (int i = 0; i < numRows; i++) {
//            sum = Runtime.RuntimeMatrix.add(sum, elements[i][i]);
//        }
//        throw new UnsupportedOperationException("RuntimeMatrix trace not implemented yet.");
//    }
//
//    public static RuntimeMatrix identity(int size) {
//        Expression[][] identityElements = new Expression[size][size];
//        for (int i = 0; i < size; i++) {
//            for (int j = 0; j < size; j++) {
//                if (i == j) {
//                    identityElements[i][j] = new RuntimeScalar(1); // assuming integer one, could be float one too
//                } else {
//                    identityElements[i][j] = new RuntimeScalar(0);
//                }
//            }
//        }
//        return new RuntimeMatrix(identityElements, TokenKind.RuntimeScalar);
//    }
//
//    // maybe return a BinaryNode
//    // it makes more sense, the node then holds the two matrices and can evaluate them
//    public Mul DoolittleLUDecomposition() {
//        // i don't want to allow pseudo-inverse or anything like that, it's all bullshit
//        if (!isSquare()) {
//            throw new RuntimeException("RuntimeMatrix must be square for LU decomposition.");
//        }
//        // initialize L and U
//        RuntimeMatrix L = identity(numRows);
//        RuntimeMatrix U = this.clone();  // just need shallow copy
//        for (int i = 0; i < numCols; i++) {
//            for (int j = i + 1; j < numRows; j++) {
//                if (U.elements[i][i] instanceof RuntimeScalar zeroCheck && Math.abs(zeroCheck.getDoubleValue()) < 1e-10) {
//                    throw new RuntimeException("RuntimeMatrix is singular, cannot perform LU decomposition.");
//                }
//                Expression factor = Runtime.RuntimeMatrix.div(U.elements[j][i], U.elements[i][i]);
//                L.elements[j][i] = factor;
//                for (int k = i; k < numCols; k++) {
//                    U.elements[j][k] = Runtime.RuntimeMatrix.sub(U.elements[j][k], Runtime.RuntimeMatrix.mul(factor, U.elements[i][k]));
//                }
//            }
//        }
//        return new Mul(L, U);  // should be super handy because we can getLeft or getRight
//    }
//
//    private RuntimeMatrix RuntimeMatrixEqualsRuntimeScalar(RuntimeScalar RuntimeScalar) {
//        Expression[][] out = new Expression[numRows][numCols];
//        for (int i = 0; i < numRows; i++) {
//            for (int j = 0; j < numCols; j++) {
//                if (!this.elements[i][j].equals(RuntimeScalar)) {
//                    out[i][j] = new BooleanNode(false);
//                } else {
//                    out[i][j] = new BooleanNode(true);
//                }
//            }
//        }
//        return new RuntimeMatrix(out, TokenKind.BOOLEAN);
//    }
//
//    // need to make this recursive for matrices of matrices, will do later
//    private RuntimeMatrix RuntimeMatrixGreaterThanRuntimeScalar(RuntimeScalar RuntimeScalar) {
//        Expression[][] out = new Expression[numRows][numCols];
//        for (int i = 0; i < numRows; i++) {
//            for (int j = 0; j < numCols; j++) {
//                if (this.elements[i][j] instanceof RuntimeScalar current && RuntimeScalar instanceof RuntimeScalar s) {
//                    if (current.getDoubleValue() > s.getDoubleValue()) {
//                        out[i][j] = new BooleanNode(true);
//                    } else {
//                        out[i][j] = new BooleanNode(false);
//                    }
//                } else {
//                    throw new RuntimeException("Unable to compare elements: " + this.elements[i][j] + " and " + RuntimeScalar + " at position (" + i + ", " + j + ")");
//                }
//            }
//        }
//        return new RuntimeMatrix(out, TokenKind.BOOLEAN);
//    }
//
//    private RuntimeMatrix RuntimeMatrixLessThanRuntimeScalar(RuntimeScalar RuntimeScalar) {
//        Expression[][] out = new Expression[numRows][numCols];
//        for (int i = 0; i < numRows; i++) {
//            for (int j = 0; j < numCols; j++) {
//                if (this.elements[i][j] instanceof RuntimeScalar current && RuntimeScalar instanceof RuntimeScalar s) {
//                    if (current.getDoubleValue() < s.getDoubleValue()) {
//                        out[i][j] = new BooleanNode(true);
//                    } else {
//                        out[i][j] = new BooleanNode(false);
//                    }
//                } else {
//                    throw new RuntimeException("Unable to compare elements: " + this.elements[i][j] + " and " + RuntimeScalar + " at position (" + i + ", " + j + ")");
//                }
//            }
//        }
//        return new RuntimeMatrix(out, TokenKind.BOOLEAN);
//    }
//
//    private RuntimeMatrix RuntimeMatrixLessEqualThanRuntimeScalar(RuntimeScalar RuntimeScalar) {
//        Expression[][] out = new Expression[numRows][numCols];
//        for (int i = 0; i < numRows; i++) {
//            for (int j = 0; j < numCols; j++) {
//                if (this.elements[i][j] instanceof RuntimeScalar current && RuntimeScalar instanceof RuntimeScalar s) {
//                    if (current.getDoubleValue() <= s.getDoubleValue()) {
//                        out[i][j] = new BooleanNode(true);
//                    } else {
//                        out[i][j] = new BooleanNode(false);
//                    }
//                } else {
//                    throw new RuntimeException("Unable to compare elements: " + this.elements[i][j] + " and " + RuntimeScalar + " at position (" + i + ", " + j + ")");
//                }
//            }
//        }
//        return new RuntimeMatrix(out, TokenKind.BOOLEAN);
//    }
//
//    private RuntimeMatrix RuntimeMatrixGreaterEqualThanRuntimeScalar(RuntimeScalar RuntimeScalar) {
//        Expression[][] out = new Expression[numRows][numCols];
//        for (int i = 0; i < numRows; i++) {
//            for (int j = 0; j < numCols; j++) {
//                if (this.elements[i][j] instanceof RuntimeScalar current && RuntimeScalar instanceof RuntimeScalar s) {
//                    if (current.getDoubleValue() >= s.getDoubleValue()) {
//                        out[i][j] = new BooleanNode(true);
//                    } else {
//                        out[i][j] = new BooleanNode(false);
//                    }
//                } else {
//                    throw new RuntimeException("Unable to compare elements: " + this.elements[i][j] + " and " + RuntimeScalar + " at position (" + i + ", " + j + ")");
//                }
//            }
//        }
//        return new RuntimeMatrix(out, TokenKind.BOOLEAN);
//    }
//
//    private RuntimeMatrix RuntimeMatrixNotEqualThanRuntimeScalar(RuntimeScalar RuntimeScalar) {
//        Expression[][] out = new Expression[numRows][numCols];
//        for (int i = 0; i < numRows; i++) {
//            for (int j = 0; j < numCols; j++) {
//                if (this.elements[i][j] instanceof RuntimeScalar current && RuntimeScalar instanceof RuntimeScalar s) {
//                    if (current.getDoubleValue() != s.getDoubleValue()) {
//                        out[i][j] = new BooleanNode(true);
//                    } else {
//                        out[i][j] = new BooleanNode(false);
//                    }
//                } else {
//                    throw new RuntimeException("Unable to compare elements: " + this.elements[i][j] + " and " + RuntimeScalar + " at position (" + i + ", " + j + ")");
//                }
//            }
//        }
//        return new RuntimeMatrix(out, TokenKind.BOOLEAN);
//    }
//
//    // handle through static method, with recursion for block matrices
//    public static RuntimeMatrix greater(Expression left, Expression right) {
//        RuntimeMatrix leftMat = left instanceof RuntimeMatrix ? (RuntimeMatrix) left : null;
//        RuntimeMatrix rightMat = right instanceof RuntimeMatrix ? (RuntimeMatrix) right : null;
//        RuntimeScalar leftConst = left instanceof RuntimeScalar ? (RuntimeScalar) left : null;
//        RuntimeScalar rightConst = right instanceof RuntimeScalar ? (RuntimeScalar) right : null;
//        int rows;
//        int cols;
//        // check if the types are legal on either side
//        if (leftMat == null && leftConst == null) {
//            throw new RuntimeException("Left operand must be a RuntimeMatrix or RuntimeScalar for comparison, got: " + left.getClass().getSimpleName());
//        } else if (rightMat == null && rightConst == null) {
//            throw new RuntimeException("Right operand must be a RuntimeMatrix or RuntimeScalar for comparison, got: " + right.getClass().getSimpleName());
//        }
//        Expression result;
//        if (leftMat != null) {
//            rows = leftMat.rows();
//            cols = leftMat.cols();
//        } else if (rightMat != null) {
//            cols = rightMat.cols();
//            rows = rightMat.rows();
//        } else {  // both RuntimeScalars -- don't know if this would ever be needed, but in case keep it
//            System.out.println("Comparing two constants: " + leftConst + " and " + rightConst);
//            result = new BooleanNode(leftConst.getValue() > rightConst.getValue());
//            Expression[][] singleElement = new Expression[1][1];
//            singleElement[0][0] = result;
//            return new RuntimeMatrix(singleElement, TokenKind.BOOLEAN);
//        }
//        Expression[][] out = new Expression[rows][cols];
//        if (rightConst != null) {  // RuntimeMatrix on left, constant on right
//            return leftMat.RuntimeMatrixGreaterThanRuntimeScalar(rightConst);
//        } else if (leftConst != null) {
//            return rightMat.RuntimeMatrixGreaterThanRuntimeScalar(leftConst);
//        }
//        for (int i = 0; i < leftMat.numRows; i++) {
//            for (int j = 0; j < leftMat.numCols; j++) {
//                if (leftMat.elements[i][j] instanceof RuntimeScalar leftC && rightMat.elements[i][j] instanceof RuntimeScalar rightC) {
//                    out[i][j] = new BooleanNode(leftC.getDoubleValue() > rightC.getDoubleValue());
//                } else if (leftMat.elements[i][j] instanceof RuntimeMatrix || rightMat.elements[i][j] instanceof RuntimeMatrix) {
//                    out[i][j] = Runtime.RuntimeMatrix.greater(leftMat.elements[i][j], rightMat.elements[i][j]);
//                } else {
//                    throw new RuntimeException("Unable to compare elements: " + leftMat.elements[i][j] + " and " + rightMat.elements[i][j] + " at position (" + i + ", " + j + ")");
//                }
//            }
//        }
//        return new RuntimeMatrix(out, TokenKind.BOOLEAN);
//    }
//
//    public static RuntimeMatrix less(Expression left, Expression right) {
//        RuntimeMatrix leftMat = left instanceof RuntimeMatrix ? (RuntimeMatrix) left : null;
//        RuntimeMatrix rightMat = right instanceof RuntimeMatrix ? (RuntimeMatrix) right : null;
//        RuntimeScalar leftConst = left instanceof RuntimeScalar ? (RuntimeScalar) left : null;
//        RuntimeScalar rightConst = right instanceof RuntimeScalar ? (RuntimeScalar) right : null;
//        int rows;
//        int cols;
//        // check if the types are legal on either side
//        if (leftMat == null && leftConst == null) {
//            throw new RuntimeException("Left operand must be a RuntimeMatrix or RuntimeScalar for comparison, got: " + left.getClass().getSimpleName());
//        } else if (rightMat == null && rightConst == null) {
//            throw new RuntimeException("Right operand must be a RuntimeMatrix or RuntimeScalar for comparison, got: " + right.getClass().getSimpleName());
//        }
//        Expression result;
//        if (leftMat != null) {
//            rows = leftMat.rows();
//            cols = leftMat.cols();
//        } else if (rightMat != null) {
//            cols = rightMat.cols();
//            rows = rightMat.rows();
//        } else {  // both RuntimeScalars -- don't know if this would ever be needed, but in case keep it
//            System.out.println("Comparing two constants: " + leftConst + " and " + rightConst);
//            result = new BooleanNode(leftConst.getDoubleValue() < rightConst.getDoubleValue());
//            Expression[][] singleElement = new Expression[1][1];
//            singleElement[0][0] = result;
//            return new RuntimeMatrix(singleElement, TokenKind.BOOLEAN);
//        }
//        Expression[][] out = new Expression[rows][cols];
//        if (rightConst != null) {  // RuntimeMatrix on left, constant on right
//            return leftMat.RuntimeMatrixLessThanRuntimeScalar(rightConst);
//        } else if (leftConst != null) {
//            return rightMat.RuntimeMatrixLessThanRuntimeScalar(leftConst);
//        }
//        for (int i = 0; i < leftMat.numRows; i++) {
//            for (int j = 0; j < leftMat.numCols; j++) {
//                if (leftMat.elements[i][j] instanceof RuntimeScalar leftC && rightMat.elements[i][j] instanceof RuntimeScalar rightC) {
//                    out[i][j] = new BooleanNode(leftC.getDoubleValue() < rightC.getDoubleValue());
//                } else if (leftMat.elements[i][j] instanceof RuntimeMatrix || rightMat.elements[i][j] instanceof RuntimeMatrix) {
//                    out[i][j] = Runtime.RuntimeMatrix.less(leftMat.elements[i][j], rightMat.elements[i][j]);
//                } else {
//                    throw new RuntimeException("idk man, sorry.");
//                }
//            }
//        }
//        return new RuntimeMatrix(out, TokenKind.BOOLEAN);
//    }
//
//    public static RuntimeMatrix lessEqual(Expression left, Expression right) {
//        RuntimeMatrix leftMat = left instanceof RuntimeMatrix ? (RuntimeMatrix) left : null;
//        RuntimeMatrix rightMat = right instanceof RuntimeMatrix ? (RuntimeMatrix) right : null;
//        RuntimeScalar leftConst = left instanceof RuntimeScalar ? (RuntimeScalar) left : null;
//        RuntimeScalar rightConst = right instanceof RuntimeScalar ? (RuntimeScalar) right : null;
//        int rows;
//        int cols;
//        // check if the types are legal on either side
//        if (leftMat == null && leftConst == null) {
//            throw new RuntimeException("Left operand must be a RuntimeMatrix or RuntimeScalar for comparison, got: " + left.getClass().getSimpleName());
//        } else if (rightMat == null && rightConst == null) {
//            throw new RuntimeException("Right operand must be a RuntimeMatrix or RuntimeScalar for comparison, got: " + right.getClass().getSimpleName());
//        }
//        Expression result;
//        if (leftMat != null) {
//            rows = leftMat.rows();
//            cols = leftMat.cols();
//        } else if (rightMat != null) {
//            cols = rightMat.cols();
//            rows = rightMat.rows();
//        } else {  // both RuntimeScalars -- don't know if this would ever be needed, but in case keep it
//            System.out.println("Comparing two constants: " + leftConst + " and " + rightConst);
//            result = new BooleanNode(leftConst.getDoubleValue() <= rightConst.getDoubleValue());
//            Expression[][] singleElement = new Expression[1][1];
//            singleElement[0][0] = result;
//            return new RuntimeMatrix(singleElement, TokenKind.BOOLEAN);
//        }
//        Expression[][] out = new Expression[rows][cols];
//        if (rightConst != null) {  // RuntimeMatrix on left, constant on right
//            return leftMat.RuntimeMatrixLessEqualThanRuntimeScalar(rightConst);
//        } else if (leftConst != null) {
//            return rightMat.RuntimeMatrixLessEqualThanRuntimeScalar(leftConst);
//        }
//        for (int i = 0; i < leftMat.numRows; i++) {
//            for (int j = 0; j < leftMat.numCols; j++) {
//                if (leftMat.elements[i][j] instanceof RuntimeScalar leftC && rightMat.elements[i][j] instanceof RuntimeScalar rightC) {
//                    out[i][j] = new BooleanNode(leftC.getDoubleValue() <= rightC.getDoubleValue());
//                } else if (leftMat.elements[i][j] instanceof RuntimeMatrix || rightMat.elements[i][j] instanceof RuntimeMatrix) {
//                    out[i][j] = Runtime.RuntimeMatrix.lessEqual(leftMat.elements[i][j], rightMat.elements[i][j]);
//                } else {
//                    throw new RuntimeException("idk man, sorry.");
//                }
//            }
//        }
//        return new RuntimeMatrix(out, TokenKind.BOOLEAN);
//    }
//
//    public static RuntimeMatrix greaterEqual(Expression left, Expression right) {
//        RuntimeMatrix leftMat = left instanceof RuntimeMatrix ? (RuntimeMatrix) left : null;
//        RuntimeMatrix rightMat = right instanceof RuntimeMatrix ? (RuntimeMatrix) right : null;
//        RuntimeScalar leftConst = left instanceof RuntimeScalar ? (RuntimeScalar) left : null;
//        RuntimeScalar rightConst = right instanceof RuntimeScalar ? (RuntimeScalar) right : null;
//        int rows;
//        int cols;
//        // check if the types are legal on either side
//        if (leftMat == null && leftConst == null) {
//            throw new RuntimeException("Left operand must be a RuntimeMatrix or RuntimeScalar for comparison, got: " + left.getClass().getSimpleName());
//        } else if (rightMat == null && rightConst == null) {
//            throw new RuntimeException("Right operand must be a RuntimeMatrix or RuntimeScalar for comparison, got: " + right.getClass().getSimpleName());
//        }
//        Expression result;
//        if (leftMat != null) {
//            rows = leftMat.rows();
//            cols = leftMat.cols();
//        } else if (rightMat != null) {
//            cols = rightMat.cols();
//            rows = rightMat.rows();
//        } else {  // both RuntimeScalars -- don't know if this would ever be needed, but in case keep it
//            result = new BooleanNode(leftConst.getDoubleValue() >= rightConst.getDoubleValue());
//            Expression[][] singleElement = new Expression[1][1];
//            singleElement[0][0] = result;
//            return new RuntimeMatrix(singleElement, TokenKind.BOOLEAN);
//        }
//        Expression[][] out = new Expression[rows][cols];
//        if (rightConst != null) {  // RuntimeMatrix on left, constant on right
//            return leftMat.RuntimeMatrixGreaterEqualThanRuntimeScalar(rightConst);
//        } else if (leftConst != null) {
//            return rightMat.RuntimeMatrixGreaterEqualThanRuntimeScalar(leftConst);
//        }
//        for (int i = 0; i < leftMat.numRows; i++) {
//            for (int j = 0; j < leftMat.numCols; j++) {
//                if (leftMat.elements[i][j] instanceof RuntimeScalar leftC && rightMat.elements[i][j] instanceof RuntimeScalar rightC) {
//                    out[i][j] = new BooleanNode(leftC.getDoubleValue() >= rightC.getDoubleValue());
//                } else if (leftMat.elements[i][j] instanceof RuntimeMatrix || rightMat.elements[i][j] instanceof RuntimeMatrix) {
//                    out[i][j] = Runtime.RuntimeMatrix.greaterEqual(leftMat.elements[i][j], rightMat.elements[i][j]);
//                } else {
//                    throw new RuntimeException("idk man, sorry.");
//                }
//            }
//        }
//        return new RuntimeMatrix(out, TokenKind.BOOLEAN);
//    }
//
//    public static RuntimeMatrix equal(Expression left, Expression right) {
//        RuntimeMatrix leftMat = left instanceof RuntimeMatrix ? (RuntimeMatrix) left : null;
//        RuntimeMatrix rightMat = right instanceof RuntimeMatrix ? (RuntimeMatrix) right : null;
//        RuntimeScalar leftConst = left instanceof RuntimeScalar ? (RuntimeScalar) left : null;
//        RuntimeScalar rightConst = right instanceof RuntimeScalar ? (RuntimeScalar) right : null;
//        int rows;
//        int cols;
//        // check if the types are legal on either side
//        if (leftMat == null && leftConst == null) {
//            throw new RuntimeException("Left operand must be a RuntimeMatrix or RuntimeScalar for comparison, got: " + left.getClass().getSimpleName());
//        } else if (rightMat == null && rightConst == null) {
//            throw new RuntimeException("Right operand must be a RuntimeMatrix or RuntimeScalar for comparison, got: " + right.getClass().getSimpleName());
//        }
//        Expression result;
//        if (leftMat != null) {
//            rows = leftMat.rows();
//            cols = leftMat.cols();
//        } else if (rightMat != null) {
//            cols = rightMat.cols();
//            rows = rightMat.rows();
//        } else {  // both RuntimeScalars -- don't know if this would ever be needed, but in case keep it
//            result = new BooleanNode(leftConst.getDoubleValue() == rightConst.getDoubleValue());
//            Expression[][] singleElement = new Expression[1][1];
//            singleElement[0][0] = result;
//            return new RuntimeMatrix(singleElement, TokenKind.BOOLEAN);
//        }
//        Expression[][] out = new Expression[rows][cols];
//        if (rightConst != null) {  // RuntimeMatrix on left, constant on right
//            return leftMat.RuntimeMatrixEqualsRuntimeScalar(rightConst);
//        } else if (leftConst != null) {
//            return rightMat.RuntimeMatrixEqualsRuntimeScalar(leftConst);
//        }
//        for (int i = 0; i < leftMat.numRows; i++) {
//            for (int j = 0; j < leftMat.numCols; j++) {
//                if (leftMat.elements[i][j] instanceof RuntimeScalar leftC && rightMat.elements[i][j] instanceof RuntimeScalar rightC) {
//                    out[i][j] = new BooleanNode(leftC.getDoubleValue() == rightC.getDoubleValue());
//                } else if (leftMat.elements[i][j] instanceof RuntimeMatrix || rightMat.elements[i][j] instanceof RuntimeMatrix) {
//                    out[i][j] = Runtime.RuntimeMatrix.equal(leftMat.elements[i][j], rightMat.elements[i][j]);
//                } else {
//                    throw new RuntimeException("idk man, sorry.");
//                }
//            }
//        }
//        return new RuntimeMatrix(out, TokenKind.BOOLEAN);
//    }
//
//    public static RuntimeMatrix notEqual(Expression left, Expression right) {
//        RuntimeMatrix leftMat = left instanceof RuntimeMatrix ? (RuntimeMatrix) left : null;
//        RuntimeMatrix rightMat = right instanceof RuntimeMatrix ? (RuntimeMatrix) right : null;
//        RuntimeScalar leftConst = left instanceof RuntimeScalar ? (RuntimeScalar) left : null;
//        RuntimeScalar rightConst = right instanceof RuntimeScalar ? (RuntimeScalar) right : null;
//        int rows;
//        int cols;
//        // check if the types are legal on either side
//        if (leftMat == null && leftConst == null) {
//            throw new RuntimeException("Left operand must be a RuntimeMatrix or RuntimeScalar for comparison, got: " + left.getClass().getSimpleName());
//        } else if (rightMat == null && rightConst == null) {
//            throw new RuntimeException("Right operand must be a RuntimeMatrix or RuntimeScalar for comparison, got: " + right.getClass().getSimpleName());
//        }
//        Expression result;
//        if (leftMat != null) {
//            rows = leftMat.rows();
//            cols = leftMat.cols();
//        } else if (rightMat != null) {
//            cols = rightMat.cols();
//            rows = rightMat.rows();
//        } else {  // both RuntimeScalars -- don't know if this would ever be needed, but in case keep it
//            System.out.println("Comparing two constants: " + leftConst + " and " + rightConst);
//            result = new BooleanNode(leftConst.getDoubleValue() != rightConst.getDoubleValue());
//            Expression[][] singleElement = new Expression[1][1];
//            singleElement[0][0] = result;
//            return new RuntimeMatrix(singleElement, TokenKind.BOOLEAN);
//        }
//        Expression[][] out = new Expression[rows][cols];
//        if (rightConst != null) {  // RuntimeMatrix on left, constant on right
//            return leftMat.RuntimeMatrixNotEqualThanRuntimeScalar(rightConst);
//        } else if (leftConst != null) {
//            return rightMat.RuntimeMatrixGreaterThanRuntimeScalar(leftConst);
//        }
//        for (int i = 0; i < leftMat.numRows; i++) {
//            for (int j = 0; j < leftMat.numCols; j++) {
//                if (leftMat.elements[i][j] instanceof RuntimeScalar leftC && rightMat.elements[i][j] instanceof RuntimeScalar rightC) {
//                    out[i][j] = new BooleanNode(leftC.getDoubleValue() != rightC.getDoubleValue());
//                } else if (leftMat.elements[i][j] instanceof RuntimeMatrix || rightMat.elements[i][j] instanceof RuntimeMatrix) {
//                    out[i][j] = Runtime.RuntimeMatrix.notEqual(leftMat.elements[i][j], rightMat.elements[i][j]);
//                } else {
//                    throw new RuntimeException("Unable to compare elements: " + leftMat.elements[i][j] + " and " + rightMat.elements[i][j] + " at position (" + i + ", " + j + ")");
//                }
//            }
//        }
//        return new RuntimeMatrix(out, TokenKind.BOOLEAN);
//    }
//
////    public Mul PLUDecomposition() {
////        if (!isSquare()) {
////            throw new RuntimeException("RuntimeMatrix must be square for LU decomposition.");
////        }
////        RuntimeMatrix L = identity(numRows);
////        RuntimeMatrix U = this.clone();  // just need shallow copy
////        RuntimeMatrix P = identity(numRows);  // permutation RuntimeMatrix
////        // first step is to pivot the rows
////        // we do this by finding the max in each column and swapping rows
////        for (int i = 0; i < numCols; i++) {
////            int idxMax = 0;  // need to keep track of where the max is
////            // find the max
////            for (int j = i + 1; j < numRows; j++) {  // remember to skip whatever is below pivot row
////                RuntimeScalar candidate = (RuntimeScalar) U.elements[j][i];  // won't work if not constant, but should be fine for now
////                RuntimeScalar currentMax = (RuntimeScalar) U.elements[idxMax][i];
////                if (j == i || (candidate != null && currentMax != null && Math.abs(candidate.getDoubleValue()) > Math.abs(currentMax.getDoubleValue()))) {
////                    idxMax = j;
////                }
////            }
////            // found the row to pivot, now swap to Rj (where j is the current column number)
////            RuntimeMatrixLike toSwap = U.getRow(i);
////            U.setRow(i, 0, U.getRow(idxMax));  // replace first row with max row
////            U.setRow(idxMax, 0, toSwap);       // replace max row with first row
////            for (int j = i; j < numRows; j++) {  // eliminate lower columns
////                double denom = ((RuntimeScalar) U.elements[i][i]).getDoubleValue();
////                if (Math.abs(denom) < 1e-10) {
////                    throw new RuntimeException("RuntimeMatrix is singular, cannot perform LU decomposition.");
////                }
////                Expression factor = RuntimeMatrix.div(U.elements[j][i], U.elements[i][i]);
////                RuntimeMatrix currentRow = U.getRow(j);
////                // now subtract factor * pivot row from current row
////                setRow(j, 0, currentRow * RuntimeMatrix.sub(new RuntimeScalar(1, false), factor) - U.getRow(i) * factor);
////            }
////        }
////        return new Mul(new Mul(P, L), U);  // should be super handy because we can getLeft or getRight
////    }
//
//    public Mul LUDecomposition() {
//        if (!isSquare()) {
//            throw new RuntimeException("RuntimeMatrix must be square for LU decomposition.");
//        }
//        RuntimeMatrix L = identity(numRows);
//        RuntimeMatrix U = this.clone();  // just need shallow copy
//        for (int i = 0; i < numCols; i++) {
//            int idxMax = 0;  // need to keep track of where the max is
//            for (int j = i + 1; j < numRows; j++) {  // remember to skip whatever is below pivot row
//                idxMax = j == i || (U.elements[j][i] instanceof RuntimeScalar currentMax && U.elements[idxMax][i] instanceof RuntimeScalar previousMax
//                        && Math.abs(currentMax.getDoubleValue()) > Math.abs(previousMax.getDoubleValue())) ? j : idxMax;
//                // found the row to pivot, now swap to Rj (where j is the current column)
//                if (idxMax != i) {
//                    RuntimeMatrixLike toSwap = U.getRow(i);
//                    U.setRow(i, 0, U.getRow(idxMax));
//                    U.setRow(idxMax, 0, toSwap);
//                    for (int k = 0; k < i; k++) {
//                        Expression temp = L.elements[i][k];
//                        L.elements[i][k] = L.elements[idxMax][k];
//                        L.elements[idxMax][k] = temp;
//                    }
//                }
//            } // done finding pivot row, now eliminate below
//            for (int j = i + 1; j < numRows; j++) {
//                if (U.elements[i][i] instanceof RuntimeScalar zeroCheck && Math.abs(zeroCheck.getDoubleValue()) < 1e-10) {
//                    throw new RuntimeException("RuntimeMatrix is singular, cannot perform LU decomposition.");
//                }
//                Expression factor = Runtime.RuntimeMatrix.div(U.elements[j][i], U.elements[i][i]);
//                L.elements[j][i] = factor;
//                for (int k = i; k < numCols; k++) {
//                    U.elements[j][k] = Runtime.RuntimeMatrix.sub(U.elements[j][k], Runtime.RuntimeMatrix.mul(factor, U.elements[i][k]));
//                }
//            }
//        }
//        return new Mul(L, U);  // should be super handy because we can getLeft or getRight
//    }
//
//    private boolean RuntimeMatrixDimensionsMatch(RuntimeMatrix other) {
//        return this.numRows == other.numRows && this.numCols == other.numCols;
//    }
//
//    public static Expression add(Expression left, Expression right) {
//        return Runtime.RuntimeMatrix.ElementWiseOp(left, right,
//                (a, b) -> RuntimeScalar.add((RuntimeScalar) a, (RuntimeScalar) b),
//                "addition");
//    }
//
//    public static Expression sub(Expression left, Expression right) {
//        return Runtime.RuntimeMatrix.ElementWiseOp(left, right,
//                (a, b) -> RuntimeScalar.subtract((RuntimeScalar) a, (RuntimeScalar) b),
//                "subtraction");
//    }
//
//    public static Expression mul(Expression left, Expression right) {
//        return Runtime.RuntimeMatrix.ElementWiseOp(left, right,
//                (a, b) -> RuntimeScalar.multiply((RuntimeScalar) a, (RuntimeScalar) b),
//                "multiplication");
//    }
//
//    public static Expression div(Expression left, Expression right) {
//        return Runtime.RuntimeMatrix.ElementWiseOp(left, right,
//                (a, b) -> RuntimeScalar.divide((RuntimeScalar) a, (RuntimeScalar) b),
//                "Hadamard division");
//    }
//
//
//    // refactor and use Java lambdas to reduce code duplication for all the element-wise ops
//    private static Expression ElementWiseOp(Expression left, Expression right, BiFunction<Expression, Expression, Expression> operator, String opName) {
//        RuntimeMatrix leftMat = left instanceof RuntimeMatrix ? (RuntimeMatrix) left : null;
//        RuntimeMatrix rightMat = right instanceof RuntimeMatrix ? (RuntimeMatrix) right : null;
//        RuntimeScalar leftConst = left instanceof RuntimeScalar ? (RuntimeScalar) left : null;
//        RuntimeScalar rightConst = right instanceof RuntimeScalar ? (RuntimeScalar) right : null;
//        int rows;
//        int cols;
//        TokenKind type;
//        // check if the types are legal on either side
//        if (leftMat == null && leftConst == null) {
//            throw new RuntimeException("Left operand must be a RuntimeMatrix or RuntimeScalar for addition, got: " + left.getClass().getSimpleName());
//        } else if (rightMat == null && rightConst == null) {
//            throw new RuntimeException("Right operand must be a RuntimeMatrix or RuntimeScalar for addition, got: " + right.getClass().getSimpleName());
//        }
//        if (leftMat != null) {
//            rows = leftMat.rows();
//            cols = leftMat.cols();
//            type = leftMat.getType();
//        } else if (rightMat != null) {
//            cols = rightMat.cols();
//            rows = rightMat.rows();
//            type = rightMat.getType();
//        } else {  // both RuntimeScalars
//            return operator.apply(leftConst, rightConst);
//        }
//        // this only applies if both are matrices, because a constant can ALWAYS be added to the RuntimeMatrix
//        if ((leftMat != null && rightMat !=null) && !(((RuntimeMatrix) left).dimensionsMatch((RuntimeMatrix) right))) {
//            throw new RuntimeException("RuntimeMatrix dimension mismatch for " + opName + ": left is " +
//                    leftMat.rows() + "x" + leftMat.cols() + ", right is " +
//                    rightMat.rows() + "x" + rightMat.cols());
//        }
//        Expression[][] resultElements = new Expression[rows][cols];
//        for (int i = 0; i < rows; i++) {  // need to resolve the type of the elements every time
//            for (int j = 0; j < cols; j++) {
//                resultElements[i][j] = operator.apply(
//                        leftMat != null ? leftMat.get(i, j) : leftConst,
//                        rightMat != null ? rightMat.get(i, j) : rightConst
//                );
//            }
//        }
//        return new RuntimeMatrix(resultElements, type);
//    }
//
//    public Expression determinant() {
//        if (!isSquare()) {
//            throw new RuntimeException("RuntimeMatrix must be square.");
//        }
//        int n = numRows;
//        // 1x1
//        if (n == 1) {
//            return elements[0][0];
//        }
//        // 2x2
//        if (n == 2) {
//            return Runtime.RuntimeMatrix.sub(
//                    Runtime.RuntimeMatrix.mul(elements[0][0], elements[1][1]),
//                    Runtime.RuntimeMatrix.mul(elements[0][1], elements[1][0])
//            );
//        }
//        Expression result = new RuntimeScalar(0); // accumulate sum
//        for (int j = 0; j < n; j++) {
//            Expression RuntimeScalar = elements[0][j];
//            // skip zero elements
//            if (RuntimeScalar instanceof RuntimeScalar zeroCheck && Math.abs(zeroCheck.getDoubleValue()) < 1e-10) {
//                continue;
//            }
//            // create subRuntimeMatrix excluding row 0 and column j
//            Expression[][] subElements = new Expression[n - 1][n - 1];
//            for (int r = 1; r < n; r++) {
//                int subColIndex = 0;
//                for (int c = 0; c < n; c++) {
//                    if (c == j) continue;
//                    subElements[r - 1][subColIndex++] = elements[r][c];
//                }
//            }
//            RuntimeMatrix subRuntimeMatrix = new RuntimeMatrix(subElements, type);
//            Expression subDet = subRuntimeMatrix.determinant();  // recursive call
//            Expression term = Runtime.RuntimeMatrix.mul(RuntimeScalar, subDet);
//            // alternate signs
//            if (j % 2 == 0) {
//                result = Runtime.RuntimeMatrix.add(result, term);
//            } else {
//                result = Runtime.RuntimeMatrix.sub(result, term);
//            }
//        }
//        return result;
//    }
//
//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("[");
//        for (int i = 0; i < numRows; i++) {
//            sb.append("[");
//            for (int j = 0; j < numCols; j++) {
//                sb.append(elements[i][j].toString());
//                if (j < numCols - 1) {
//                    sb.append(", ");
//                }
//            }
//            sb.append("]");
//            if (i < numRows - 1) {
//                sb.append(", \n");
//            }
//        }
//        sb.append("]");
//        return sb.toString();
//    }
//
//    @Override
//    public RuntimeMatrix clone() {
//        Expression[][] clonedElements = new Expression[numRows][numCols];
//        for (int i = 0; i < numRows; i++) {
//            for (int j = 0; j < numCols; j++) {
//                clonedElements[i][j] = elements[i][j].clone();
//            }
//        }
//        return new RuntimeMatrix(clonedElements, type);
//    }
//}
