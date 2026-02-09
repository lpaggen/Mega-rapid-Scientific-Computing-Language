package Semantic;

import AST.*;

public final class TypeChecker implements TypeVisitor<Type> {

    @Override
    public Type visitListType(ListTypeNode listTypeNode) {  // list is resizeable, doesn't need a size specified, vector will ask a size
        Type elementType = listTypeNode.getElementType().accept(this);
        if (elementType instanceof VoidTypeNode) {
            throw new RuntimeException("List cannot have element type void");
        }
        return new ListTypeNode(elementType);
    }

    @Override
    public Type visitBooleanType(BooleanTypeNode booleanTypeNode) {
        return new BooleanTypeNode();
    }

    @Override
    public Type visitEdgeTypeNode(EdgeTypeNode edgeTypeNode) {
        return null;
    }

    @Override
    public Type visitGraphTypeNode(GraphTypeNode graphTypeNode) {
        return null;
    }

    // TODO matrix type checking, check if inner types are compatible, check dimensions if possible, etc.
    @Override
    public Type visitMatrixTypeNode(MatrixTypeNode node) {
        Type elementType = node.getElementType().accept(this);
        if (elementType instanceof VoidTypeNode) {
            throw new RuntimeException("Matrix cannot have element type void");
        }
        return new MatrixTypeNode(elementType, node.getRows(), node.getCols());
    }

    @Override
    public Type visitNodeTypeNode(NodeTypeNode nodeTypeNode) {
        return new NodeTypeNode();
    }

    @Override
    public Type visitScalarTypeNode(ScalarTypeNode scalarTypeNode) {
        return new ScalarTypeNode();
    }

    @Override
    public Type visitStringTypeNode(StringTypeNode stringTypeNode) {
        return new StringTypeNode();
    }

    @Override
    public Type visitFunctionTypeNode(FunctionTypeNode functionTypeNode) {
        return null;
    }

    @Override
    public Type visitVoidType(VoidTypeNode voidTypeNode) {
        return new VoidTypeNode();
    }

    @Override
    public Type visitAlgebraicSymbolType(AlgebraicSymbolType algebraicSymbolType) {
        return new AlgebraicSymbolType();
    }

    @Override
    public Type visitMathType(MathTypeNode mathTypeNode) {
        return null;
    }
}
