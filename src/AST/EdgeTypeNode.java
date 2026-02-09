package AST;

import Semantic.TypeVisitor;

public final class EdgeTypeNode implements Type {
    private final Type from;
    private final Type to;

    public EdgeTypeNode(Type from, Type to) {
        this.from = from;
        this.to = to;
    }

    public Type getFrom() {
        return from;
    }

    public Type getTo() {
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
