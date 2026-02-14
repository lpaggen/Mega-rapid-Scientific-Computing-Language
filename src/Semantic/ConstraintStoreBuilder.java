package Semantic;

import AST.*;
import AST.Metadata.Containers.BinaryDimension;
import AST.Metadata.Containers.Dimension;
import AST.Metadata.Containers.KnownDimension;
import AST.Metadata.Containers.SymbolicDimension;

import java.io.ObjectStreamClass;
import java.util.List;

/**
 * ConstraintStoreBuilder collects "claim" statements at compile-time and populates the ConstraintStore.
 */
public final class ConstraintStoreBuilder implements StatementVisitor<Void> {
    private final ConstraintStore constraintStore;
    private final List<String> errors;
    private final SymbolTable symbolTable;  // TODO: contains info like int x = 5; maybe we should use this!

    public ConstraintStoreBuilder(List<String> errors, SymbolTable symbolTable) {
        this.constraintStore = new ConstraintStore();
        this.errors = errors;
        this.symbolTable = symbolTable;
    }

    public ConstraintStore collect(List<Statement> ast) {
        for (Statement statement : ast) {
            statement.accept(this);
        }
        return constraintStore;
    }

    public void printErrors() {
        if (errors.isEmpty()) {
            System.out.println("No constraint errors found.");
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

        if (!(claim instanceof BinaryNode bin)) {
            System.out.println("claim is not a binary expression: " + claim);
            errors.add("claim expression must be a binary expression");
            return null;
        }

        // TODO -> future build must support full SMT / at least bounds and intervals
        // for this demo, i only prove that this can save against classic linalg errors
        if (!(bin.getOperator() != null && !bin.getOperator().equals("=="))) {
            errors.add("claim expression must have '==' operator");
        }

        try {
            Dimension left = extractDimension(bin.getLeft());
            Dimension right = extractDimension(bin.getRight());
            constraintStore.addEqualityConstraint(left, right);
        } catch (IllegalArgumentException e) {
            errors.add("Invalid claim: " + e.getMessage());
        }

        return null;
    }

    private Dimension extractDimension(Expression expr) {
        if (expr instanceof VariableNode var) {
            String name = var.getName();
            TypeInfo info = symbolTable.lookup(name);
            if (info == null) {
                throw new IllegalArgumentException("Variable " + name + " not found");
            }
            if (!(info.type() instanceof MathTypeNode)) {
                errors.add("Variable " + name + " is not a math type");
            }
            return new SymbolicDimension(name);
        }

        if (expr instanceof IntegerLiteralNode lit) {
            return new KnownDimension(lit.getValue());
        }

        if (expr instanceof BinaryNode bin) {
            Dimension left = extractDimension(bin.getLeft());  // recurse until base
            Dimension right = extractDimension(bin.getRight());
            Operators op = bin.getOperator();
            return new BinaryDimension(left, right, op);
        }

        throw new IllegalArgumentException("Cannot extract dimension from: " + expr);
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
