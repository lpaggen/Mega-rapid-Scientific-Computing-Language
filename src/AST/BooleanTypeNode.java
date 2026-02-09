package AST;

import Semantic.TypeVisitor;

public final class BooleanTypeNode implements Type {
    public BooleanTypeNode() {}
    @Override
    public String toString() {
        return "Bool";
    }

    @Override
    public <R> R accept(TypeVisitor<R> visitor) {
        return visitor.visitBooleanType(this);
    }
}
