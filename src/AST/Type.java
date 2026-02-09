package AST;

import Semantic.TypeVisitor;

public sealed interface Type extends ASTNode permits AlgebraicSymbolType, BooleanTypeNode, EdgeTypeNode, FunctionTypeNode, GraphTypeNode, ListTypeNode, MathTypeNode, MatrixTypeNode, NodeTypeNode, ScalarTypeNode, StringTypeNode, VoidTypeNode {
    <R> R accept(TypeVisitor<R> visitor);
}
