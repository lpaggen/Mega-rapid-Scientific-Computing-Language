package Semantic;

import AST.*;
import AST.Metadata.Functions.ParamNode;

import java.util.ArrayList;
import java.util.List;

public final class SymbolTableBuilder implements StatementVisitor<Void> {
    public final SymbolTable symbolTable;
    private final List<String> errors;

    public SymbolTableBuilder(List<String> errors) {
        this.symbolTable = new SymbolTable();
        this.errors = errors;
    }

    public void printErrors() {
        if (errors.isEmpty()) {
            System.out.println("No semantic errors found.");
        } else {
            System.out.println("Semantic Errors:");
            for (String error : errors) {
                System.out.println(error);
            }
        }
    }

    public void build(List<Statement> ast) { // to use in main
        for (Statement stmt : ast) {
            try {
                stmt.accept(this);
            } catch (RuntimeException e) {
                errors.add(e.getMessage());
            }
        }
    }

    @Override
    public Void visitVariableDeclarationNode(VariableDeclarationNode node) {
        try {
            symbolTable.declare(node.name(), node.type(), node.isMutable());
        } catch (RuntimeException e) {
            errors.add("Line " + node.line() + ": " + e.getMessage());
        }
        return null;
    }

    @Override
    public Void visitFunctionDeclarationNode(FunctionDeclarationNode node) {
        try {
            symbolTable.declare(node.getName(), node.getReturnType(), false);
        } catch (RuntimeException e) {
            errors.add("Line " + node.line() + ": " + e.getMessage());
            return null;
        }

        symbolTable.pushScope(); // new scope for function body
        for (ParamNode param : node.getParameters()) {
            try {
                symbolTable.declare(param.name(), param.type(), false);
            } catch (RuntimeException e) {
                errors.add("Line " + node.line() + ": " + e.getMessage());
            }
        }

        for (Statement stmt : node.getBody().body()) {
            stmt.accept(this);
        }

        symbolTable.popScope();
        return null;
    }

    @Override
    public Void visitIfNode(IfNode node) {
        symbolTable.pushScope();
        for (Statement stmt : node.thenBranch()) {
            stmt.accept(this);
        }
        symbolTable.popScope();

        if (node.elseBranch() != null) {
            symbolTable.pushScope();
            for (Statement stmt : node.elseBranch()) {
                stmt.accept(this);
            }
            symbolTable.popScope();
        }

        return null;
    }

    @Override
    public Void visitWhileNode(WhileNode node) {
        symbolTable.pushScope();
        for (Statement stmt : node.getBody()) {
            stmt.accept(this);
        }
        symbolTable.popScope();
        return null;
    }

    // Pass-through visitors (no symbols declared)
    @Override
    public Void visitExpressionStatement(ExpressionStatementNode stmt) {
        return null;
    }

    @Override
    public Void visitClaimStatementNode(ClaimStatementNode node) {
        return null;
    }

    @Override
    public Void visitReturnStatementNode(ReturnStatementNode node) {
        return null;
    }

    @Override
    public Void visitVariableReassignmentNode(VariableReassignmentNode node) {
        return null;
    }

    @Override
    public Void visitImportNode(ImportNode node) {
        // TODO: Handle module imports
        symbolTable.declare(node.alias() != null ? node.alias() : node.moduleName(), node.getType(), false);
        return null;
    }
}
