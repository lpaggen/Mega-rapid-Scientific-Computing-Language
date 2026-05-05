package Semantic;

import AST.*;
import AST.Metadata.Containers.KnownDimension;

import java.util.List;

public final class TypeChecker {
    public void check(TypeInterface typeInterface) {

    }

    public Type infer(Expression expression) {
        return null;
    }

    public boolean isValid(Expression expression) {
        return false;
    }

//    public void check(List<TypeInterface> typeNodes) {
//        for (TypeInterface typeNode : typeNodes) {
//            typeNode.accept(this);
//        }
//    }
//
//    @Override
//    public TypeInterface visitListType(ListTypeNodeInterface listTypeNode) {  // list is resizeable, doesn't need a size specified, vector will ask a size
//        TypeInterface elementTypeInterface = listTypeNode.getElementType().accept(this);
//        if (elementTypeInterface instanceof VoidTypeNodeInterface) {
//            throw new RuntimeException("List cannot have element typeInterface void");
//        }
//        return new ListTypeNodeInterface(elementTypeInterface);
//    }
//
//    @Override
//    public TypeInterface visitBooleanType(BooleanTypeNodeInterface booleanTypeNode) {
//        return new BooleanTypeNodeInterface();
//    }
//
//    @Override
//    public TypeInterface visitEdgeTypeNode(EdgeTypeNodeInterface edgeTypeNode) {
//        return null;
//    }
//
//    @Override
//    public TypeInterface visitGraphTypeNode(GraphTypeNodeInterface graphTypeNode) {
//        return null;
//    }
//
//    @Override
//    public TypeInterface visitMatrixTypeNode(MatrixTypeNodeInterface node) {
//        TypeInterface elementTypeInterface = node.elementTypeInterface().accept(this);
//        if (elementTypeInterface instanceof VoidTypeNodeInterface) {
//            throw new RuntimeException("Matrix cannot have element type void");
//        }
//        constraintStore.addGreaterThanConstraint(node.rows(), new KnownDimension(0));  // enforce positive dimensions
//        constraintStore.addGreaterThanConstraint(node.cols(), new KnownDimension(0));
//        return new MatrixTypeNodeInterface(elementTypeInterface, node.rows(), node.cols());
//    }
//
//    @Override
//    public TypeInterface visitNodeTypeNode(NodeTypeNodeInterface nodeTypeNode) {
//        return new NodeTypeNodeInterface();
//    }
//
//    @Override
//    public TypeInterface visitScalarTypeNode(ScalarTypeNodeInterface scalarTypeNode) {
//        return new ScalarTypeNodeInterface();
//    }
//
//    @Override
//    public TypeInterface visitStringTypeNode(StringTypeNodeInterface stringTypeNode) {
//        return new StringTypeNodeInterface();
//    }
//
//    @Override
//    public TypeInterface visitFunctionTypeNode(FunctionTypeNodeInterface functionTypeNode) {
//        return null;
//    }
//
//    @Override
//    public TypeInterface visitVoidType(VoidTypeNodeInterface voidTypeNode) {
//        return new VoidTypeNodeInterface();
//    }
//
//    @Override
//    public TypeInterface visitAlgebraicSymbolType(AlgebraicSymbolTypeInterface algebraicSymbolType) {
//        return new AlgebraicSymbolTypeInterface();
//    }
//
//    @Override
//    public TypeInterface visitMathType(MathTypeNodeInterface mathTypeNode) {
//        return null;
//    }
//
//    @Override
//    public TypeInterface visitModuleType(ModuleTypeInterface moduleType) {
//        return null;
//    }
}
