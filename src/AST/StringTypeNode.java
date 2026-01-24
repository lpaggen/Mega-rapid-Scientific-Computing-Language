package AST;

import AST.Type;
import AST.Visitors.TypeVisitor;

public final class StringTypeNode implements Type {
    private final String value;

    public StringTypeNode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "\"" + value + "\"";
    }

    @Override
    public <R> R accept(TypeVisitor<R> visitor) {
        return visitor.visitStringTypeNode(this);
    }
}
