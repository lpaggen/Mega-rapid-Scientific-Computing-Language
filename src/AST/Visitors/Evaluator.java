package AST.Visitors;

import AST.Expressions.*;
import AST.Expressions.BinaryOperations.BinaryNode;
import AST.Expressions.Functions.BuiltIns.ImportNode;
import AST.Expressions.Functions.FunctionCallNode;
import AST.Expressions.Mathematics.Exp;
import AST.Expressions.Mathematics.Trigonometry.Csc;
import AST.Expressions.Mathematics.Trigonometry.Sin;
import AST.Literals.*;
import AST.Literals.Abstract.BraceBlockNode;
import AST.Literals.Abstract.BracketLiteralNode;
import AST.Literals.Abstract.RecordLiteralNode;
import AST.Literals.Graph.EdgeLiteralNode;
import AST.Literals.Graph.GraphNodeLiteralNode;
import AST.Literals.Linalg.MatrixLiteralNode;
import AST.Statements.Functions.FunctionDeclNode;
import AST.Statements.Functions.ParamNode;
import AST.Statements.Functions.ReturnStatementNode;
import AST.Statements.VariableDeclarationNode;
import Types.ScalarTypeNode;

public class Evaluator implements ExpressionVisitor<Object> {
    @Override
    public Object visitBraceLiteral(BraceBlockNode node) {
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
    public Object visitFunctionDeclaration(FunctionDeclNode node) {
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
    public Object visitVariableDeclarationNode(VariableDeclarationNode node) {
        return null;
    }

    @Override
    public Object visitReturnNode(ReturnStatementNode node) {
        return null;
    }

    @Override
    public Object visitParameterNode(ParamNode node) {
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
}
