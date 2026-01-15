package Types;

public class EdgeTypeNode extends TypeNode {
    private final TypeNode from;
    private final TypeNode to;

    public EdgeTypeNode(TypeNode from, TypeNode to) {
        super("Edge");
        this.from = from;
        this.to = to;
    }

    public TypeNode getFrom() {
        return from;
    }

    public TypeNode getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "Edge(" + from.toString() + " -> " + to.toString() + ")";
    }
}
