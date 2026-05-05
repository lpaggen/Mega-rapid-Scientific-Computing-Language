package Semantic;

import AST.MatrixTypeNodeInterface;
import AST.Type;
import AST.TypeAttributes;

public final class LinalgInverseFunction implements IntrinsicFunction {
    @Override
    public Type apply(Type type, ConstraintStore constraintStore) {
        if (!(type.typeInterface() instanceof MatrixTypeNodeInterface m)) {
            throw new RuntimeException("linalg::inverse expects a matrix argument, got " + type);
        }
        constraintStore.addEqualityConstraint(m.rows(), m.cols());  // add constraint that matrix must be square
        if (!constraintStore.isSatisfied()) {
            throw new RuntimeException("Constraints are not satisfiable, linalg::inverse requires square matrix, but got " + m.rows() + "@" + m.cols());
        }
        return new Type(new MatrixTypeNodeInterface(m.elementTypeInterface(), m.rows(), m.cols()), new TypeAttributes(type.attributes().mutable(), type.attributes().constant()));
    }
}
