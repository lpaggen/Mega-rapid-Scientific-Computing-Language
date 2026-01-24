package AST.Visitors.Statements;

import AST.IfNode;
import AST.ExpressionStatementNode;
import AST.FunctionDeclarationNode;
import AST.ReturnStatementNode;
import AST.VariableDeclarationNode;
import AST.VariableReassignmentNode;
import AST.WhileNode;

public class Executor implements StatementVisitor<Object> {
    @Override
    public Object visitIfStatement(IfNode stmt) {
        return null;
    }

    @Override
    public Object visitWhileStatement(WhileNode stmt) {
        return null;
    }

    @Override
    public Object visitFunctionDeclaration(FunctionDeclarationNode stmt) {
        return null;
    }

    @Override
    public Object visitReturnStatement(ReturnStatementNode stmt) {
        return null;
    }

    @Override
    public Object visitVariableDeclaration(VariableDeclarationNode stmt) {
        return null;
    }

    @Override
    public Object visitVariableReassignment(VariableReassignmentNode stmt) {
        return null;
    }

    @Override
    public Object visitExpressionStatement(ExpressionStatementNode stmt) {
        return null;
    }
}
