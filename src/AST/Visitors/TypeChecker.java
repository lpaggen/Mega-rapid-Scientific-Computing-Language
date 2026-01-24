package AST.Visitors;

import AST.*;

public final class TypeChecker implements TypeVisitor<Type> {

    @Override
    public Type visitListType(ListTypeNode listTypeNode) {  // list is resizeable, doesn't need a size specified
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

    @Override
    public Type visitMatrixTypeNode(MatrixTypeNode node) {
        Type elementType = node.getElementType().accept(this);
        if (node.getCols().isKnown() && node.getRows().isKnown()) {

            //TODO make the full dimension check !! -> also check if rectangular

            return new MatrixTypeNode(elementType, node.getRows(), node.getCols());
        } else if (!node.getCols().isKnown() && !node.getRows().isKnown()) {
            return node;
        }
        return null;
    }

    @Override
    public Type visitNodeTypeNode(NodeTypeNode nodeTypeNode) {
        return null;
    }

    @Override
    public Type visitScalarTypeNode(ScalarTypeNode scalarTypeNode) {
        return null;
    }

    @Override
    public Type visitStringTypeNode(StringTypeNode stringTypeNode) {
        return null;
    }

    @Override
    public Type visitFunctionTypeNode(FunctionTypeNode functionTypeNode) {
        return null;
    }

    @Override
    public Type visitVoidType(VoidTypeNode voidTypeNode) {
        return null;
    }
}
