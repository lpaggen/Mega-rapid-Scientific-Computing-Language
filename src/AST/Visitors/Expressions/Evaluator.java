package AST.Visitors.Expressions;

import AST.*;
import AST.BinaryNode;
import AST.ImportNode;
import AST.FunctionCallNode;
import AST.LambdaFunctionNode;
import AST.MapFunctionNode;
import AST.Exp;
import AST.Csc;
import AST.Sin;
import AST.BraceLiteralNode;
import AST.BracketLiteralNode;
import AST.RecordLiteralNode;
import AST.EdgeLiteralNode;
import AST.GraphNodeLiteralNode;
import AST.MatrixLiteralNode;

public class Evaluator implements ExpressionVisitor<Object> {
    @Override
    public Object visitBraceLiteral(BraceLiteralNode node) {
        return null;
    }

    @Override
    public Object visitBracketLiteral(BracketLiteralNode node) {
        return null;
    }

    @Override
    public Object visitFunctionCall(FunctionCallNode node) {
        return null;
    }

    @Override
    public Object visitBinaryNode(BinaryNode node) {
        Object l = node.getLeft().accept(this);
        Object r = node.getRight().accept(this);
        return null;
    }

    @Override
    public Object visitIntegerLiteral(IntegerLiteralNode node) {
        return null;
    }

    @Override
    public Object visitFloatLiteral(FloatLiteralNode node) {
        return null;
    }

    @Override
    public Object visitIncrementNode(IncrementNode node) {
        return null;
    }

    @Override
    public Object visitVariableNode(VariableNode node) {
        return null;
    }

    @Override
    public Object visitStringLiteral(StringLiteralNode node) {
        return null;
    }

    @Override
    public Object visitUnaryNode(UnaryNode node) {
        return null;
    }

    @Override
    public Object visitPrimaryNode(PrimaryNode node) {
        return null;
    }

    @Override
    public Object visitImportNode(ImportNode node) {
        return null;
    }

    @Override
    public Object visitRecordLiteral(RecordLiteralNode node) {
        return null;
    }

    @Override
    public Object visitListLiteral(ListLiteralNode node) {
        return null;
    }

    @Override
    public Object visitBooleanLiteral(BooleanLiteralNode node) {
        return null;
    }

    @Override
    public Object visitMatrixLiteralNode(MatrixLiteralNode matrixLiteralNode) {
        return null;
    }

    @Override
    public Object visitGroupingNode(GroupingNode groupingNode) {
        return null;
    }

    @Override
    public Object visitGraphNodeLiteralNode(GraphNodeLiteralNode nodeLiteralNode) {
        return null;
    }

    @Override
    public Object visitSin(Sin sin) {
        return null;
    }

    @Override
    public Object visitEdgeLiteralNode(EdgeLiteralNode edgeLiteralNode) {
        return null;
    }

    @Override
    public Object visitExp(Exp exp) {
        return null;
    }

    @Override
    public Object visitCsc(Csc csc) {
        return null;
    }

    @Override
    public Object visitListAccessNode(ListAccessNode listAccessNode) {
        return null;
    }

    @Override
    public Object visitMemberAccessNode(MemberAccessNode memberAccessNode) {
        return null;
    }

    @Override
    public Object visitLambdaFunctionNode(LambdaFunctionNode lambdaFunctionNode) {
        return null;
    }

    @Override
    public Object visitMapFunctionNode(MapFunctionNode mapFunctionNode) {
        return null;
    }
}
