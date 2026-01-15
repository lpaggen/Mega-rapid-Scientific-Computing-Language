package Types;


public class GraphTypeNode extends TypeNode {
    private final TypeNode type;  // for now assume the type is the same for node weights and edge capacities
    private final boolean directed;
    private final boolean weighted;
    public GraphTypeNode(TypeNode type, boolean directed, boolean weighted) {
        super("Graph");
        this.type = type;
        this.directed = directed;
        this.weighted = weighted;
    }

    public TypeNode getType() {
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
}
