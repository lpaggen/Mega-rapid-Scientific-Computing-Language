package AST;

import Semantic.TypeVisitor;

public final class IntegerTypeInterface implements TypeInterface {

    @Override
    public String toString() {
        return "int";
    }

    @Override
    public <R> R accept(TypeVisitor<R> visitor) {
        return visitor.visitIntegerType(this);
    }
}
