package AST;

import Semantic.TypeVisitor;

public sealed interface TypeInterface extends ASTNode permits AlgebraicSymbolTypeInterface, BooleanTypeNodeInterface, EdgeTypeNodeInterface, FunctionTypeNodeInterface, GraphTypeNodeInterface, ListTypeNodeInterface, MathTypeNodeInterface, MatrixTypeNodeInterface, ModuleTypeInterface, NodeTypeNodeInterface, ScalarTypeNodeInterface, StringTypeNodeInterface, VoidTypeNodeInterface {
    <R> R accept(TypeVisitor<R> visitor);
}
