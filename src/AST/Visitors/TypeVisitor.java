package AST.Visitors;

import AST.*;

public sealed interface TypeVisitor<R> permits TypeChecker {
    R visitListType(ListTypeNode listTypeNode);
    R visitBooleanType(BooleanTypeNode booleanTypeNode);

    R visitEdgeTypeNode(EdgeTypeNode edgeTypeNode);

    R visitGraphTypeNode(GraphTypeNode graphTypeNode);

    R visitMatrixTypeNode(MatrixTypeNode matrixTypeNode);

    R visitNodeTypeNode(NodeTypeNode nodeTypeNode);

    R visitScalarTypeNode(ScalarTypeNode scalarTypeNode);

    R visitStringTypeNode(StringTypeNode stringTypeNode);

    R visitFunctionTypeNode(FunctionTypeNode functionTypeNode);
    R visitVoidType(VoidTypeNode voidTypeNode);
}
