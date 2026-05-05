package Semantic;

import AST.*;

public final class ExpressionDispatchVisitor implements StatementVisitor<Void> { // TODO fill in every visit method, this is just a skeleton
    private final ExpressionTypeVisitor expressionTypeVisitor;

    public ExpressionDispatchVisitor(ExpressionTypeVisitor expressionTypeVisitor) {
        this.expressionTypeVisitor = expressionTypeVisitor;
    }

    @Override
    public Void visitExpressionStatement(ExpressionStatementNode stmt) {
        stmt.expression().accept(expressionTypeVisitor);
        return null;
    }

    @Override
    public Void visitImportNode(ImportNode importNode) {
        return null;
    }

    @Override
    public Void visitFunctionDeclarationNode(FunctionDeclarationNode functionDeclarationNode) {
        return null;
    }

    @Override
    public Void visitIfNode(IfNode node) {
        return null;
    }

    @Override
    public Void visitReturnStatementNode(ReturnStatementNode node) {
        if (node.returnValue() != null) {
            node.returnValue().accept(expressionTypeVisitor);
        }
        return null;
    }

    @Override
    public Void visitVariableDeclarationNode(VariableDeclarationNode node) {
        if (node.initializer() != null) {
            node.initializer().accept(expressionTypeVisitor);
        }
        return null;
    }

    @Override
    public Void visitVariableReassignmentNode(VariableReassignmentNode variableReassignmentNode) {
        return null;
    }

    @Override
    public Void visitWhileNode(WhileNode whileNode) {
        return null;
    }

    @Override
    public Void visitClaimStatementNode(ClaimStatementNode claimStatementNode) {
        return null;
    }
}
