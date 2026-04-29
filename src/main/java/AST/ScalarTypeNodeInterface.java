package AST;

import Semantic.TypeVisitor;

public final class ScalarTypeNodeInterface implements TypeInterface {
    public ScalarTypeNodeInterface() {}

    @Override
    public <R> R accept(TypeVisitor<R> visitor) {
        return visitor.visitScalarTypeNode(this);
    }
}
