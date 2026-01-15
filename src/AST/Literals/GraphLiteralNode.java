package AST.Literals;

import AST.Expressions.Expression;

import java.util.HashMap;

public final class GraphLiteralNode extends Expression {
    private final HashMap<String, Expression> nodes;
    private final HashMap<String, HashMap<String, Expression>> edges;  // in this sense, edges are {A -> {B, weight}}

    public GraphLiteralNode(HashMap<String, Expression> nodes, HashMap<String, HashMap<String, Expression>> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

    public HashMap<String, Expression> getNodes() {
        return nodes;
    }

    public HashMap<String, HashMap<String, Expression>> getEdges() {
        return edges;
    }

    @Override
    public String toString() {
        return "GraphLiteralNode{" +
                "nodes=" + nodes +
                ", edges=" + edges +
                '}';
    }
}
