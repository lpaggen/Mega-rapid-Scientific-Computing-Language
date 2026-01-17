package Types;

public class ListTypeNode extends TypeNode {
    private TypeNode elementType;
    public ListTypeNode(TypeNode elementType) {
        super("List");
        this.elementType = elementType;
    }

    public TypeNode getElementType() {
        return elementType;
    }

    @Override
    public String toString() {
        return "List<" + elementType.toString() + ">";
    }
}
