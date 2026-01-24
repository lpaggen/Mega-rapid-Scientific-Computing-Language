package AST;

import AST.Visitors.ExpressionVisitor;

public sealed interface Expression extends Cloneable, ASTNode permits BinaryNode, BooleanLiteralNode, BraceLiteralNode, BracketLiteralNode, Csc, EdgeLiteralNode, Exp, FloatLiteralNode, FunctionCallNode, GraphNodeLiteralNode, GroupingNode, IncrementNode, IntegerLiteralNode, LambdaFunctionNode, ListAccessNode, ListLiteralNode, MapFunctionNode, MatrixLiteralNode, MemberAccessNode, PrimaryNode, RecordLiteralNode, Sin, StringLiteralNode, UnaryNode, VariableNode {
    String toString();
    <R> R accept(ExpressionVisitor<R> visitor);
}
