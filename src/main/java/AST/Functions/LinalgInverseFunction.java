package AST.Functions;

import AST.MatrixType;
import AST.Type;
import AST.TypeAttributes;
import AST.TypedExpression;
import Semantic.ConstraintStore;

public final class LinalgInverseFunction implements IntrinsicFunction {
    public TypedExpression apply(TypedExpression type, ConstraintStore constraintStore) {
        if (!(type.type().typeInterface() instanceof MatrixType m)) {
            throw new RuntimeException("linalg::inverse expects a matrix argument, got " + type);
        }
        if (!constraintStore.impliesEqual(m.rows(), m.cols())) {
            String namerows = m.rows().getName();
            String namecols = m.cols().getName();
            throw new RuntimeException(
                    "Matrix inverse requires square matrix, but got dimensions " + namerows + "@" + namecols + " where " + namerows + " != " + namecols
            );
        }
        return new TypedExpression(type.expr(),
                new Type(new MatrixType(m.entryDataType(), m.rows(), m.cols()), new TypeAttributes(type.type().attributes().mutable(), type.type().attributes().constant())));
    }
}
