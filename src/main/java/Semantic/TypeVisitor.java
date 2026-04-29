package Semantic;

import AST.*;

public sealed interface TypeVisitor<R> permits TypeChecker {
    R visitListType(ListTypeNodeInterface listTypeNode);
    R visitBooleanType(BooleanTypeNodeInterface booleanTypeNode);

    R visitEdgeTypeNode(EdgeTypeNodeInterface edgeTypeNode);

    R visitGraphTypeNode(GraphTypeNodeInterface graphTypeNode);

    R visitMatrixTypeNode(MatrixTypeNodeInterface matrixTypeNode);

    R visitNodeTypeNode(NodeTypeNodeInterface nodeTypeNode);

    R visitScalarTypeNode(ScalarTypeNodeInterface scalarTypeNode);

    R visitStringTypeNode(StringTypeNodeInterface stringTypeNode);

    R visitFunctionTypeNode(FunctionTypeNodeInterface functionTypeNode);
    R visitVoidType(VoidTypeNodeInterface voidTypeNode);
    R visitAlgebraicSymbolType(AlgebraicSymbolTypeInterface algebraicSymbolType);

    R visitMathType(MathTypeNodeInterface mathTypeNode);
    R visitModuleType(ModuleTypeInterface moduleType);
}
