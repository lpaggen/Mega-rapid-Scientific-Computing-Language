package Semantic;

import AST.*;

import java.util.List;

public final class ExpressionDispatchVisitor implements StatementVisitor<Statement> { // TODO fill in every visit method, this is just a skeleton
    private final ExpressionTypeVisitor expressionTypeVisitor;
    private final ConstraintStore constraintStore;
    private final SymbolTable symbolTable;

    public void visit(List<Statement> stmt) {
        for (Statement s : stmt) {
            s.accept(this);
        }
    }

    public ExpressionDispatchVisitor(ExpressionTypeVisitor expressionTypeVisitor, ConstraintStore constraintStore, SymbolTable symbolTable) {
        this.expressionTypeVisitor = expressionTypeVisitor;
        this.constraintStore = constraintStore;
        this.symbolTable = symbolTable;
    }

    @Override
    public Statement visitExpressionStatement(ExpressionStatementNode stmt) {
        return new ExpressionStatementNode(stmt.expression());
    }

    @Override
    public Statement visitImportNode(ImportNode importNode) {
        return null;
    }

    @Override
    public Statement visitFunctionDeclarationNode(FunctionDeclarationNode functionDeclarationNode) {
        return null;
    }

    @Override
    public Statement visitIfNode(IfNode node) {
        return null;
    }

    @Override
    public Statement visitReturnStatementNode(ReturnStatementNode node) {
        if (node.returnValue() != null) {
            node.returnValue().accept(expressionTypeVisitor);
        }
        return null;
    }

    @Override
    public Statement visitVariableDeclarationNode(VariableDeclarationNode node) {
        if (node.initializer() == null) {
            return node; // No initializer, so no type constraints to check
        }
        TypeInterface actual = node.initializer().accept(expressionTypeVisitor).typeInterface();
        TypeInterface declared = node.type().typeInterface();
        if (declared instanceof MatrixTypeNodeInterface expected) {
            if (actual instanceof MatrixTypeNodeInterface found) {
                System.out.println("Adding equality constraints for matrix dimensions: expected " + expected.rows() + "x" + expected.cols() + ", found " + found.rows() + "x" + found.cols());
                constraintStore.addEqualityConstraint(
                        expected.rows(),
                        found.rows()
                );
                constraintStore.addEqualityConstraint(
                        expected.cols(),
                        found.cols()
                );
            } else {
                throw new RuntimeException("Type mismatch: expected a matrix type, but got " + actual + " at line " + node.line());
            }
        }
        return node;
    }

    @Override
    public Statement visitVariableReassignmentNode(VariableReassignmentNode variableReassignmentNode) {
        return null;
    }

    @Override
    public Statement visitWhileNode(WhileNode whileNode) {
        return null;
    }

    @Override
    public Statement visitClaimStatementNode(ClaimStatementNode claimStatementNode) {
        return null;
    }
}
