package Semantic;

import AST.*;
import AST.VariableDeclarationNode;
import AST.VariableReassignmentNode;

public sealed interface StatementVisitor<R> permits ConstraintStoreBuilder, Executor, SymbolTableBuilder {
    R visitExpressionStatement(ExpressionStatementNode stmt);
    R visitImportNode(ImportNode importNode);
    R visitFunctionDeclarationNode(FunctionDeclarationNode functionDeclarationNode);
    R visitIfNode(IfNode ifNode);
    R visitReturnStatementNode(ReturnStatementNode returnStatementNode);
    R visitVariableDeclarationNode(VariableDeclarationNode variableDeclarationNode);
    R visitVariableReassignmentNode(VariableReassignmentNode variableReassignmentNode);
    R visitWhileNode(WhileNode whileNode);
    R visitClaimStatementNode(ClaimStatementNode claimStatementNode);
}
