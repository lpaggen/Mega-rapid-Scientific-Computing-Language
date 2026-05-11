package AST;

import AST.Metadata.Containers.Dimension;

/**
 * @param rows Dimension is a stricter typeInterface than Expression -> constraint.
 *             we can only use dimensions that are provably positive integers, and we can only use them in contexts where they are provably equal to other dimensions (e.g. matrix multiplication)
 */
public record MatrixType(Type entryDataType, Dimension rows, Dimension cols) implements TypeInterface {

    @Override
    public String toString() {
        return "Matrix<" + entryDataType.toString() + ">[" + rows + "@" + cols + "]";
    }
//
//    @Override
//    public <R> R accept(TypeVisitor<R> visitor) {
//        return visitor.visitMatrixTypeNode(this);
//    }
}
