package AST;

import Semantic.TypeVisitor;

public final class ListTypeNode implements Type {
    private Type elementType;
    public ListTypeNode(Type elementType) {
        this.elementType = elementType;
    }

    public Type getElementType() {
        return elementType;
    }

    @Override
    public String toString() {
        return "List<" + elementType.toString() + ">";
    }

    @Override
    public <R> R accept(TypeVisitor<R> visitor) {
        return visitor.visitListType(this);
    }
}
