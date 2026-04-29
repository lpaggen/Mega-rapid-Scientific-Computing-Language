package Semantic;

import AST.*;

import java.util.List;

public final class TypeCheckerVisitor implements ExpressionVisitor<Type> {
    private final Registry registry = new Registry();

    @Override
    public Type visitBraceLiteral(BraceLiteralNode node) {
        return null;
    }

    @Override
    public Type visitBracketLiteral(BracketLiteralNode node) {
        return null;
    }

    @Override
    public Type visitFunctionCall(FunctionCallNode node) {
        return null;
    }

    @Override
    public Type visitBinaryNode(BinaryNode node) {
        return null;
    }

    @Override
    public Type visitIntegerLiteral(IntegerLiteralNode node) {
        return null;
    }

    @Override
    public Type visitFloatLiteral(FloatLiteralNode node) {
        return null;
    }

    @Override
    public Type visitIncrementNode(IncrementNode node) {
        return null;
    }

    @Override
    public Type visitVariableNode(VariableNode node) {
        return null;
    }

    @Override
    public Type visitStringLiteral(StringLiteralNode node) {
        return null;
    }

    @Override
    public Type visitUnaryNode(UnaryNode node) {
        return null;
    }

    @Override
    public Type visitPrimaryNode(PrimaryNode node) {
        return null;
    }

    @Override
    public Type visitImportNode(ImportNode node) {
        return null;
    }

    @Override
    public Type visitRecordLiteral(RecordLiteralNode node) {
        return null;
    }

    @Override
    public Type visitListLiteral(ListLiteralNode node) {
        return null;
    }

    @Override
    public Type visitBooleanLiteral(BooleanLiteralNode node) {
        return null;
    }

    @Override
    public Type visitMatrixLiteralNode(MatrixLiteralNode matrixLiteralNode) {
        return null;
    }

    @Override
    public Type visitGroupingNode(GroupingNode groupingNode) {
        return null;
    }

    @Override
    public Type visitGraphNodeLiteralNode(GraphNodeLiteralNode nodeLiteralNode) {
        return null;
    }

    @Override
    public Type visitSin(Sin sin) {
        return null;
    }

    @Override
    public Type visitEdgeLiteralNode(EdgeLiteralNode edgeLiteralNode) {
        return null;
    }

    @Override
    public Type visitExp(Exp exp) {
        return null;
    }

    @Override
    public Type visitCsc(Csc csc) {
        return null;
    }

    @Override
    public Type visitListAccessNode(ListAccessNode listAccessNode) {
        return null;
    }

    @Override
    public Type visitMemberAccessNode(MemberAccessNode memberAccessNode) {
        return null;
    }

    @Override
    public Type visitLambdaFunctionNode(LambdaFunctionNode lambdaFunctionNode) {
        return null;
    }

    @Override
    public Type visitMapFunctionNode(MapFunctionNode mapFunctionNode) {
        return null;
    }

    @Override
    public Type visitAlgebraicSymbol(AlgebraicSymbol algebraicSymbol) {
        return null;
    }

    @Override
    public Type visitAlgebraicSymbolLiteral(AlgebraicSymbolLiteralNode algebraicSymbolLiteralNode) {
        return null;
    }

    @Override
    public Type visitAssignmentNode(AssignmentNode assignmentNode) {
        return null;
    }

    @Override
    public Type visitNameSpaceAccessNode(NamespaceAccessNode node) {
        List<Type> argTypes = node.args().stream()
                .map(arg -> arg.accept(this))  // Recursively check the typeInterface of each argument
                .toList();
        IntrinsicFunction fn = registry.lookupNamespaceMethod(node.namespace(), node.method());
        return fn.apply(argTypes.get(0));
    }

}
