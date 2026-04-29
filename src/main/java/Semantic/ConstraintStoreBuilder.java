package Semantic;

import AST.*;
import AST.Metadata.Containers.Dimension;

import java.util.List;

/**
 * ConstraintStoreBuilder collects "claim" statements at compile-time and populates the ConstraintStore.
 */
public final class ConstraintStoreBuilder implements StatementVisitor<Void> {
    private final ConstraintStore constraintStore;
    private final List<String> errors;
    private final DimensionLowerer dimensionLowerer = new DimensionLowerer();

    public ConstraintStoreBuilder(List<String> errors, SymbolTable symbolTable) {
        this.constraintStore = new ConstraintStore();
        this.errors = errors;
    }

    public void collect(List<Statement> ast) {
        for (Statement statement : ast) {
            statement.accept(this);
        }
    }

    public void printErrors() {
        if (errors.isEmpty()) {
            System.out.println("No constraint errors found.");
            System.out.println("Constraints are satisfiable: " + constraintStore.isSatisfied());
        } else {
            System.out.println("Constraint Errors:");
            for (String error : errors) {
                System.out.println(error);
            }
        }
    }

    @Override
    public Void visitClaimStatementNode(ClaimStatementNode node) {
        Expression claim = node.claimExpression();
        if (claim instanceof AssignmentNode expr) {  // ex: claim x = 2 * y + 3
            constraintStore.addEqualityConstraint(extractDimension(new VariableNode(expr.variableName())), extractDimension(expr.value()));
        } else if (claim instanceof BinaryNode bin) {
            switch (bin.getOperator()) {
                case GT -> constraintStore.addGreaterThanConstraint(extractDimension(bin.getLeft()), extractDimension(bin.getRight()));
                case LT -> constraintStore.addLessThanConstraint(extractDimension(bin.getLeft()), extractDimension(bin.getRight()));
                case GTE -> constraintStore.addGreaterEqualConstraint(extractDimension(bin.getLeft()), extractDimension(bin.getRight()));
                case LTE -> constraintStore.addLessEqualConstraint(extractDimension(bin.getLeft()), extractDimension(bin.getRight()));
                default -> errors.add("Unsupported operator in claim: " + bin.getOperator());
            }
        } else {
            errors.add("Unsupported operation in claim: " + claim);
        }
        return null;
    }

    private Dimension extractDimension(Expression expr) {
        return expr.accept(dimensionLowerer);
    }

    @Override
    public Void visitFunctionDeclarationNode(FunctionDeclarationNode node) {
        for (Statement stmt : node.getBody().body()) {
            stmt.accept(this);
        }
        return null;
    }

    @Override
    public Void visitIfNode(IfNode node) {
        for (Statement stmt : node.thenBranch()) {
            stmt.accept(this);
        }
        for (Statement stmt : node.elseBranch()) {
            stmt.accept(this);
        }
        return null;
    }

    @Override
    public Void visitWhileNode(WhileNode node) {
        for (Statement stmt : node.getBody()) {
            stmt.accept(this);
        }
        return null;
    }

    @Override
    public Void visitExpressionStatement(ExpressionStatementNode stmt) {
        return null;
    }

    @Override
    public Void visitImportNode(ImportNode importNode) {
        return null;
    }

    @Override
    public Void visitReturnStatementNode(ReturnStatementNode returnStatementNode) {
        return null;
    }

    @Override
    public Void visitVariableDeclarationNode(VariableDeclarationNode variableDeclarationNode) {
        return null;
    }

    @Override
    public Void visitVariableReassignmentNode(VariableReassignmentNode variableReassignmentNode) {
        return null;
    }
}
