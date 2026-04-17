package AST;

import Semantic.TypeVisitor;

public final class VoidTypeNode implements Type {
    @Override
    public String toString() {
        return "void";
    }

    @Override
    public <R> R accept(TypeVisitor<R> visitor) {
        return visitor.visitVoidType(this);
    }
}
