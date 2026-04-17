package AST;

import Semantic.StatementVisitor;

public sealed interface Statement extends ASTNode permits ClaimStatementNode, ExpressionStatementNode, FunctionDeclarationNode, IfNode, ImportNode, ReturnStatementNode, VariableDeclarationNode, VariableReassignmentNode, WhileNode {
    <R> R accept(StatementVisitor<R> visitor);
}
