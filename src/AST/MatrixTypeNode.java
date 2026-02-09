package AST;

import AST.Metadata.Containers.Dimension;
import Semantic.TypeVisitor;

public final class MatrixTypeNode implements Type {
    private final Type elementType;
    private final Dimension rows;  // Dimension is a stricter type than Expression -> constraint
    private final Dimension cols;

    public MatrixTypeNode(Type elementType, Dimension rows, Dimension cols) {
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

    public Type getElementType() {
        return elementType;
    }

    @Override
    public String toString() {
        return "Matrix<" + elementType.toString() + ">[" + rows + "x" + cols + "]";
    }

    @Override
    public <R> R accept(TypeVisitor<R> visitor) {
        return visitor.visitMatrixTypeNode(this);
    }
}
