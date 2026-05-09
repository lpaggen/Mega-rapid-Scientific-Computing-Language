package AST.Functions;

import AST.MatrixTypeNodeInterface;
import AST.Type;
import AST.TypeAttributes;
import Semantic.ConstraintStore;

public final class LinalgInverseFunction implements IntrinsicFunction {
    @Override
    public Type apply(Type type, ConstraintStore constraintStore) {
        if (!(type.typeInterface() instanceof MatrixTypeNodeInterface m)) {
            throw new RuntimeException("linalg::inverse expects a matrix argument, got " + type);
        }
        if (!constraintStore.impliesEqual(m.rows(), m.cols())) {
            String namerows = m.rows().getName();
            String namecols = m.cols().getName();
            throw new RuntimeException(
                    "Matrix inverse requires square matrix, but got dimensions " + namerows + "@" + namecols + " where " + namerows + " != " + namecols
            );
        }
        return new Type(new MatrixTypeNodeInterface(m.entryDataType(), m.rows(), m.cols()), new TypeAttributes(type.attributes().mutable(), type.attributes().constant()));
    }
}
