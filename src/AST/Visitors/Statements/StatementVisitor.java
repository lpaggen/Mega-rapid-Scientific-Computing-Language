package AST.Visitors.Statements;

import AST.*;
import AST.VariableDeclarationNode;
import AST.VariableReassignmentNode;

public interface StatementVisitor<R> {
    R visitIfStatement(IfNode stmt);
    R visitWhileStatement(WhileNode stmt);
    R visitFunctionDeclaration(FunctionDeclarationNode stmt);
    R visitReturnStatement(ReturnStatementNode stmt);
    R visitVariableDeclaration(VariableDeclarationNode stmt);
    R visitVariableReassignment(VariableReassignmentNode stmt);
    R visitExpressionStatement(ExpressionStatementNode stmt);
}
