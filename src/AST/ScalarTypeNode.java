package AST;

import Semantic.TypeVisitor;

public final class ScalarTypeNode implements Type {
    public ScalarTypeNode() {}

    @Override
    public <R> R accept(TypeVisitor<R> visitor) {
        return visitor.visitScalarTypeNode(this);
    }
}
