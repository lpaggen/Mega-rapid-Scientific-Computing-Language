package AST;


import AST.Type;
import AST.Visitors.TypeVisitor;

public final class GraphTypeNode implements Type {
    private final Type type;  // for now assume the type is the same for node weights and edge capacities
    private final boolean directed;
    private final boolean weighted;
    public GraphTypeNode(Type type, boolean directed, boolean weighted) {
        this.type = type;
        this.directed = directed;
        this.weighted = weighted;
    }

    public Type getType() {
        return type;
    }

    public boolean isDirected() {
        return directed;
    }

    public boolean isWeighted() {
        return weighted;
    }

    @Override
    public String toString() {
        return "Graph<" + type.toString() + ">" +
               (directed ? "[Directed]" : "[Undirected]") +
               (weighted ? "[Weighted]" : "[Unweighted]");
    }

    @Override
    public <R> R accept(TypeVisitor<R> visitor) {
        return visitor.visitGraphTypeNode(this);
    }
}
