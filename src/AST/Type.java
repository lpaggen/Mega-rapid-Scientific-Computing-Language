package AST;

import AST.Visitors.TypeVisitor;

public sealed interface Type extends ASTNode permits BooleanTypeNode, EdgeTypeNode, FunctionTypeNode, GraphTypeNode, ListTypeNode, MatrixTypeNode, NodeTypeNode, ScalarTypeNode, StringTypeNode, VoidTypeNode {
    <R> R accept(TypeVisitor<R> visitor);
}
