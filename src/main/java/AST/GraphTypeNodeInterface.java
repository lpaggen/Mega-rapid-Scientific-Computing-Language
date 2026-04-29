package AST;


import Semantic.TypeVisitor;

public final class GraphTypeNodeInterface implements TypeInterface {
    private final TypeInterface typeInterface;  // for now assume the typeInterface is the same for node weights and edge capacities
    private final boolean directed;
    private final boolean weighted;
    public GraphTypeNodeInterface(TypeInterface typeInterface, boolean directed, boolean weighted) {
        this.typeInterface = typeInterface;
        this.directed = directed;
        this.weighted = weighted;
    }

    public TypeInterface getType() {
        return typeInterface;
    }

    public boolean isDirected() {
        return directed;
    }

    public boolean isWeighted() {
        return weighted;
    }

    @Override
    public String toString() {
        return "Graph<" + typeInterface.toString() + ">" +
               (directed ? "[Directed]" : "[Undirected]") +
               (weighted ? "[Weighted]" : "[Unweighted]");
    }

    @Override
    public <R> R accept(TypeVisitor<R> visitor) {
        return visitor.visitGraphTypeNode(this);
    }
}
