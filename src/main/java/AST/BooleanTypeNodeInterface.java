package AST;

import Semantic.TypeVisitor;

public final class BooleanTypeNodeInterface implements TypeInterface {
    public BooleanTypeNodeInterface() {}
    @Override
    public String toString() {
        return "Bool";
    }

    @Override
    public <R> R accept(TypeVisitor<R> visitor) {
        return visitor.visitBooleanType(this);
    }
}
