package AST;

import AST.Metadata.Containers.Dimension;
import Semantic.TypeVisitor;

/**
 * @param rows Dimension is a stricter typeInterface than Expression -> constraint.
 *             we can only use dimensions that are provably positive integers, and we can only use them in contexts where they are provably equal to other dimensions (e.g. matrix multiplication)
 */
public record MatrixTypeNodeInterface(TypeInterface elementTypeInterface, Dimension rows, Dimension cols) implements TypeInterface {

    @Override
    public String toString() {
        return "Matrix<" + elementTypeInterface.toString() + ">[" + rows + "x" + cols + "]";
    }

    @Override
    public <R> R accept(TypeVisitor<R> visitor) {
        return visitor.visitMatrixTypeNode(this);
    }
}
