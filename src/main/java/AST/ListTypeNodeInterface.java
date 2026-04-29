package AST;

import Semantic.TypeVisitor;

public final class ListTypeNodeInterface implements TypeInterface {
    private TypeInterface elementTypeInterface;
    public ListTypeNodeInterface(TypeInterface elementTypeInterface) {
        this.elementTypeInterface = elementTypeInterface;
    }

    public TypeInterface getElementType() {
        return elementTypeInterface;
    }

    @Override
    public String toString() {
        return "List<" + elementTypeInterface.toString() + ">";
    }

    @Override
    public <R> R accept(TypeVisitor<R> visitor) {
        return visitor.visitListType(this);
    }
}
