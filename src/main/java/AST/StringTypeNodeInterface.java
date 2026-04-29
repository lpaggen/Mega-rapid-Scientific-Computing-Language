package AST;

import Semantic.TypeVisitor;

public final class StringTypeNodeInterface implements TypeInterface {

    public StringTypeNodeInterface() {}

    @Override
    public <R> R accept(TypeVisitor<R> visitor) {
        return visitor.visitStringTypeNode(this);
    }
}
