package AST;

public sealed interface Statement extends ASTNode permits ExpressionStatementNode, ImportNode, FunctionDeclarationNode, IfNode, ReturnStatementNode, VariableDeclarationNode, VariableReassignmentNode, WhileNode {}  // don't think much is needed here
