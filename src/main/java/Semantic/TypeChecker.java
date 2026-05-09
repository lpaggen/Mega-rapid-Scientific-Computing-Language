package Semantic;

import AST.*;
import AST.Metadata.Containers.Dimension;
import AST.Metadata.Containers.KnownDimension;
import AST.Metadata.Containers.MatrixShape;
import com.microsoft.z3.IntExpr;

import java.util.List;

public final class TypeChecker implements TypeVisitor {
    public final Registry registry;
    public final ConstraintStore constraintStore;
    public final SymbolTable symbolTable;
    public TypeChecker(Registry registry, ConstraintStore constraintStore, SymbolTable symbolTable) {
        this.registry = registry;
        this.constraintStore = constraintStore;
        this.symbolTable = symbolTable;
    }

    public void isValid(Type type, Expression node) {
        MatrixShape shape = inferMatrixShape((MatrixLiteralNode) node);
        Dimension rows = shape.rows();
        Dimension cols = shape.cols();
        if (type.typeInterface() instanceof MatrixTypeNodeInterface m) {
            Dimension expectedRows = m.rows();
            Dimension expectedCols = m.cols();
            constraintStore.impliesEqual(rows, expectedRows);
            constraintStore.impliesEqual(cols, expectedCols);
        } else {
            throw new RuntimeException("Expected a matrix type, but got " + type);
        }
    }

    private MatrixShape inferMatrixShape(MatrixLiteralNode node) {
        int numRows = node.rows().size();
        int numCols = node.rows().getFirst().size();
        for (List<Expression> row : node.rows()) {
            if (row.size() != numCols) {
                throw new RuntimeException("All rows in a matrix literal must have the same number of columns");
            }
        }
        return new MatrixShape(new KnownDimension(numRows), new KnownDimension(numCols));
    }

    @Override
    public Object visitListType(ListTypeNodeInterface listTypeNode) {
        return null;
    }

    @Override
    public Object visitBooleanType(BooleanTypeNodeInterface booleanTypeNode) {
        return null;
    }

    @Override
    public Object visitEdgeTypeNode(EdgeTypeNodeInterface edgeTypeNode) {
        return null;
    }

    @Override
    public Object visitGraphTypeNode(GraphTypeNodeInterface graphTypeNode) {
        return null;
    }

    @Override
    public Object visitMatrixTypeNode(MatrixTypeNodeInterface matrixTypeNode) {
        return null;
    }

    @Override
    public Object visitNodeTypeNode(NodeTypeNodeInterface nodeTypeNode) {
        return null;
    }

    @Override
    public Object visitScalarTypeNode(ScalarTypeNodeInterface scalarTypeNode) {
        return null;
    }

    @Override
    public Object visitStringTypeNode(StringTypeNodeInterface stringTypeNode) {
        return null;
    }

    @Override
    public Object visitFunctionTypeNode(FunctionTypeNodeInterface functionTypeNode) {
        return null;
    }

    @Override
    public Object visitVoidType(VoidTypeNodeInterface voidTypeNode) {
        return null;
    }

    @Override
    public Object visitAlgebraicSymbolType(AlgebraicSymbolTypeInterface algebraicSymbolType) {
        return null;
    }

    @Override
    public Object visitMathType(MathTypeNodeInterface mathTypeNode) {
        return null;
    }

    @Override
    public Object visitModuleType(ModuleTypeInterface moduleType) {
        return null;
    }

    @Override
    public Object visitIntegerType(IntegerTypeInterface integerTypeInterface) {
        return null;
    }

    @Override
    public Object visitFloatType(FloatTypeInterface floatTypeInterface) {
        return floatTypeInterface.accept(this);
    }
}
