package AST;

import Semantic.ExpressionVisitor;

public sealed interface Expression extends Cloneable, ASTNode permits AlgebraicSymbol, AlgebraicSymbolLiteralNode, BinaryNode, BooleanLiteralNode, BraceLiteralNode, BracketLiteralNode, Csc, EdgeLiteralNode, Exp, FloatLiteralNode, FunctionCallNode, GraphNodeLiteralNode, GroupingNode, IncrementNode, IntegerLiteralNode, LambdaFunctionNode, ListAccessNode, ListLiteralNode, MapFunctionNode, MatrixLiteralNode, MemberAccessNode, PrimaryNode, RecordLiteralNode, Sin, StringLiteralNode, UnaryNode, VariableNode {
    String toString();
    <R> R accept(ExpressionVisitor<R> visitor);
}
