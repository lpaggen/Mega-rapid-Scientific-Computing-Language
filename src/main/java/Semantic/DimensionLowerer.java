package Semantic;

import AST.*;
import AST.Metadata.Containers.BinaryDimension;
import AST.Metadata.Containers.Dimension;
import AST.Metadata.Containers.KnownDimension;
import AST.Metadata.Containers.SymbolicDimension;

public final class DimensionLowerer implements ExpressionVisitor<Dimension> {
    @Override
    public Dimension visitGroupingNode(GroupingNode groupingNode) {
        return groupingNode.getValue().accept(this);
    }

    @Override
    public Dimension visitBinaryNode(BinaryNode node) {
        Dimension leftDim = node.getLeft().accept(this);
        Dimension rightDim = node.getRight().accept(this);
        return new BinaryDimension(leftDim, rightDim, node.getOperator());
    }

    @Override
    public Dimension visitIntegerLiteral(IntegerLiteralNode node) {
        return new KnownDimension(node.getValue());
    }

    @Override
    public Dimension visitUnaryNode(UnaryNode node) {
        Dimension argDim = node.getArg().accept(this);
        if (node.getOperator().equals(Operators.SUB)) {
            return new BinaryDimension(new KnownDimension(0), argDim, Operators.SUB);
        }
        return argDim;
    }

    @Override
    public Dimension visitBraceLiteral(BraceLiteralNode node) {
        return null;
    }

    @Override
    public Dimension visitBracketLiteral(BracketLiteralNode node) {
        return null;
    }

    @Override
    public Dimension visitFunctionCall(FunctionCallNode node) {
        return null;
    }

    @Override
    public Dimension visitFloatLiteral(FloatLiteralNode node) {
        return null;
    }

    @Override
    public Dimension visitIncrementNode(IncrementNode node) {
        return null;
    }

    @Override
    public Dimension visitVariableNode(VariableNode node) {
        return new SymbolicDimension(node.getName());
    }

    @Override
    public Dimension visitStringLiteral(StringLiteralNode node) {
        return null;
    }

    @Override
    public Dimension visitPrimaryNode(PrimaryNode node) {
        return null;
    }

    @Override
    public Dimension visitImportNode(ImportNode node) {
        return null;
    }

    @Override
    public Dimension visitRecordLiteral(RecordLiteralNode node) {
        return null;
    }

    @Override
    public Dimension visitListLiteral(ListLiteralNode node) {
        return null;
    }

    @Override
    public Dimension visitBooleanLiteral(BooleanLiteralNode node) {
        return null;
    }

    @Override
    public Dimension visitMatrixLiteralNode(MatrixLiteralNode matrixLiteralNode) {
        return null;
    }

    @Override
    public Dimension visitGraphNodeLiteralNode(GraphNodeLiteralNode nodeLiteralNode) {
        return null;
    }

    @Override
    public Dimension visitSin(Sin sin) {
        return null;
    }

    @Override
    public Dimension visitEdgeLiteralNode(EdgeLiteralNode edgeLiteralNode) {
        return null;
    }

    @Override
    public Dimension visitExp(Exp exp) {
        return null;
    }

    @Override
    public Dimension visitCsc(Csc csc) {
        return null;
    }

    @Override
    public Dimension visitListAccessNode(ListAccessNode listAccessNode) {
        return null;
    }

    @Override
    public Dimension visitMemberAccessNode(MemberAccessNode memberAccessNode) {
        return null;
    }

    @Override
    public Dimension visitLambdaFunctionNode(LambdaFunctionNode lambdaFunctionNode) {
        return null;
    }

    @Override
    public Dimension visitMapFunctionNode(MapFunctionNode mapFunctionNode) {
        return null;
    }

    @Override
    public Dimension visitAlgebraicSymbol(AlgebraicSymbol algebraicSymbol) {
        return null;
    }

    @Override
    public Dimension visitAlgebraicSymbolLiteral(AlgebraicSymbolLiteralNode algebraicSymbolLiteralNode) {
        return null;
    }

    @Override
    public Dimension visitAssignmentNode(AssignmentNode assignmentNode) {
        return null;
    }

    @Override
    public Dimension visitNameSpaceAccessNode(NamespaceAccessNode nameSpaceAccessNode) {
        return null;
    }
}
