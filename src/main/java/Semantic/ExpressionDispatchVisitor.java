package Semantic;

import AST.*;
import AST.Metadata.Containers.Dimension;

import java.util.List;

public final class ExpressionDispatchVisitor implements StatementVisitor<Statement> { // TODO fill in every visit method, this is just a skeleton
    private final TypeChecker typeChecker;
    private final ConstraintStore constraintStore;
    private final SymbolTable symbolTable;

    public void visit(List<Statement> stmt) {
        for (Statement s : stmt) {
            s.accept(this);
        }
    }

    public ExpressionDispatchVisitor(TypeChecker typeChecker, ConstraintStore constraintStore, SymbolTable symbolTable) {
        this.typeChecker = typeChecker;
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
            node.returnValue().accept(typeChecker);
        }
        return null;
    }

    @Override
    public Statement visitVariableDeclarationNode(VariableDeclarationNode node) {
        if (node.initializer() == null) {
            return node;  // want to maybe include some sort of "uninitialized" type here to catch cases where the variable is used before being initialized
        }
        TypeInterface actual = node.initializer().accept(typeChecker).type().typeInterface();
        TypeInterface declared = node.type().typeInterface();
        if (declared instanceof MatrixType expected) {
            if (actual instanceof MatrixType found) {
                Dimension expected_rows = DimensionLowerer.fold(expected.rows());
                Dimension expected_cols = DimensionLowerer.fold(expected.cols());
                Dimension found_rows = DimensionLowerer.fold(found.rows());
                Dimension found_cols = DimensionLowerer.fold(found.cols());
                constraintStore.addEqualityConstraint(
                        expected_rows,
                        found_rows
                );
                constraintStore.addEqualityConstraint(
                        expected_cols,
                        found_cols
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
