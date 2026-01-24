package AST;

import AST.Visitors.StatementVisitor;

public sealed interface Statement extends ASTNode permits ExpressionStatementNode, ImportNode, FunctionDeclarationNode, IfNode, ReturnStatementNode, VariableDeclarationNode, VariableReassignmentNode, WhileNode {
    <R> R accept(StatementVisitor<R> visitor);
}
