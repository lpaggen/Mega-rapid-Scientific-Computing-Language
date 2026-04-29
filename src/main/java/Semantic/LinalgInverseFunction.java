package Semantic;

import AST.MatrixTypeNodeInterface;
import AST.Type;
import AST.TypeAttributes;

public final class LinalgInverseFunction implements IntrinsicFunction {
    public Type apply(Type argType) {
        // TODO implement this, need to check that argType is a matrix typeInterface, and check dimensions are square
        if (!(argType.typeInterface() instanceof MatrixTypeNodeInterface m)) {
            throw new RuntimeException("TypeInterface error: linalg::inverse expects a matrix argument, got " + argType);
        }
        if (!(ConstraintStore.canProveEqual(m.rows(), m.cols()))) {
            throw new RuntimeException("TypeInterface error: linalg::inverse expects a square matrix argument, got " + argType);
        }
        return new Type(new MatrixTypeNodeInterface(m.elementTypeInterface(), m.rows(), m.cols()), new TypeAttributes(false, false));
    }
}
