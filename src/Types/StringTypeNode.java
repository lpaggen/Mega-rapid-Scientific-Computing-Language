package Types;

public class StringTypeNode extends TypeNode {
    private final String value;

    public StringTypeNode(String value) {
        super("String");
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "\"" + value + "\"";
    }
}
