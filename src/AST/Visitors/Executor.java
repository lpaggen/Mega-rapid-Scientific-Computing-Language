package AST.Visitors;

import AST.*;

public final class Executor implements StatementVisitor<Object> {

    @Override
    public Object visitExpressionStatement(ExpressionStatementNode stmt) {
        return null;
    }

    @Override
    public Object visitImportNode(ImportNode importNode) {
        return null;
    }

    @Override
    public Object visitFunctionDeclarationNode(FunctionDeclarationNode functionDeclarationNode) {
        return null;
    }

    @Override
    public Object visitIfNode(IfNode ifNode) {
        return null;
    }

    @Override
    public Object visitReturnStatementNode(ReturnStatementNode returnStatementNode) {
        return null;
    }

    @Override
    public Object visitVariableDeclarationNode(VariableDeclarationNode variableDeclarationNode) {
        return null;
    }

    @Override
    public Object visitVariableReassignmentNode(VariableReassignmentNode variableReassignmentNode) {
        return null;
    }

    @Override
    public Object visitWhileNode(WhileNode whileNode) {
        return null;
    }
}
