package Types;

import Types.Abstract.Dimension;

public class MatrixTypeNode extends TypeNode {
    private final TypeNode elementType;
    private final Dimension rows;  // Dimension is a stricter type than Expression -> constraint
    private final Dimension cols;

    public MatrixTypeNode(TypeNode elementType, Dimension rows, Dimension cols) {
        super("Matrix");
        this.elementType = elementType;
        this.rows = rows;
        this.cols = cols;
    }

    public Dimension getRows() {
        return rows;
    }

    public Dimension getCols() {
        return cols;
    }

    public TypeNode getElementType() {
        return elementType;
    }

    @Override
    public String toString() {
        return "Matrix<" + elementType.toString() + ">[" + rows + "x" + cols + "]";
    }
}
