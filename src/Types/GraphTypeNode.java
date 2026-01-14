package Types;


public class GraphTypeNode extends TypeNode {
    private final TypeNode nodeWeightType;
    private final TypeNode edgeCapacityType;
    private final boolean directed;
    private final boolean weighted;
    public GraphTypeNode(TypeNode nodeWeightType, TypeNode edgeCapacityType, boolean directed, boolean weighted) {
        super("Graph");
        this.nodeWeightType = nodeWeightType;
        this.edgeCapacityType = edgeCapacityType;
        this.directed = directed;
        this.weighted = weighted;
    }

    public TypeNode getNodeWeightType() {
        return nodeWeightType;
    }

    public TypeNode getEdgeCapacityType() {
        return edgeCapacityType;
    }

    public boolean isDirected() {
        return directed;
    }

    public boolean isWeighted() {
        return weighted;
    }

    @Override
    public String toString() {
        return "Graph<" + nodeWeightType.toString() + ", " + edgeCapacityType.toString() + ">" +
               (directed ? "[Directed]" : "[Undirected]") +
               (weighted ? "[Weighted]" : "[Unweighted]");
    }
}
