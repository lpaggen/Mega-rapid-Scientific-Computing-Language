package AST;

import Semantic.TypeVisitor;

public sealed interface TypeInterface extends ASTNode permits AlgebraicSymbolTypeInterface, BooleanTypeNodeInterface, EdgeTypeNodeInterface, FloatTypeInterface, FunctionTypeNodeInterface, GraphTypeNodeInterface, IntegerTypeInterface, ListTypeNodeInterface, MathTypeNodeInterface, MatrixTypeNodeInterface, ModuleTypeInterface, NodeTypeNodeInterface, ScalarTypeNodeInterface, StringTypeNodeInterface, VoidTypeNodeInterface {
    <R> R accept(TypeVisitor<R> visitor);
}
