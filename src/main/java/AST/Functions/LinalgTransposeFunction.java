package AST.Functions;

import AST.MatrixType;
import AST.Type;
import AST.TypedExpression;
import Semantic.ConstraintStore;

public final class LinalgTransposeFunction implements IntrinsicFunction {
    @Override
    public TypedExpression apply(TypedExpression type, ConstraintStore constraintStore) {
        if (!(type.type().typeInterface() instanceof MatrixType m)) {
            throw new RuntimeException("linalg::transpose expects a matrix argument, got " + type);
        }
        return new TypedExpression(type.expr(), new Type(new MatrixType(m.entryDataType(), m.cols(), m.rows()), new AST.TypeAttributes(type.type().attributes().mutable(), type.type().attributes().constant())));
    }
}
