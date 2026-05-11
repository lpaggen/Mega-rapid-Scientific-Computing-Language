package AST;


public sealed interface TypeInterface extends ASTNode permits AlgebraicSymbolTypeInterface, BooleanTypeNodeInterface, EdgeTypeNodeInterface, FloatTypeInterface, FunctionTypeNodeInterface, GraphTypeNodeInterface, IntegerTypeInterface, ListTypeNodeInterface, MathTypeNodeInterface, MatrixType, ModuleTypeInterface, NodeTypeNodeInterface, ScalarTypeNodeInterface, StringTypeNodeInterface, VoidTypeNodeInterface {
}
