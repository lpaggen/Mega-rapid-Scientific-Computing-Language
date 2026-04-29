package AST;

import Semantic.TypeVisitor;

public final class EdgeTypeNodeInterface implements TypeInterface {
    private final TypeInterface from;
    private final TypeInterface to;

    public EdgeTypeNodeInterface(TypeInterface from, TypeInterface to) {
        this.from = from;
        this.to = to;
    }

    public TypeInterface getFrom() {
        return from;
    }

    public TypeInterface getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "Edge(" + from.toString() + " -> " + to.toString() + ")";
    }

    @Override
    public <R> R accept(TypeVisitor<R> visitor) {
        return visitor.visitEdgeTypeNode(this);
    }
}
