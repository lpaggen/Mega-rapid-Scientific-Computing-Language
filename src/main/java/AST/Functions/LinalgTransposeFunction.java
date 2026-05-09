package AST.Functions;

public final class LinalgTransposeFunction implements IntrinsicFunction {
    @Override
    public AST.Type apply(AST.Type type, Semantic.ConstraintStore constraintStore) {
        if (!(type.typeInterface() instanceof AST.MatrixTypeNodeInterface m)) {
            throw new RuntimeException("linalg::transpose expects a matrix argument, got " + type);
        }
        return new AST.Type(new AST.MatrixTypeNodeInterface(m.entryDataType(), m.cols(), m.rows()), new AST.TypeAttributes(type.attributes().mutable(), type.attributes().constant()));
    }
}
