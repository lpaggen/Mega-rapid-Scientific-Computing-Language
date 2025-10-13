package AST.Nodes.DataStructures;

import AST.Nodes.Expressions.Expression;
import Interpreter.Runtime.Environment;
import Interpreter.Tokenizer.TokenKind;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Graph extends Expression {
    private HashMap<String, Node> nodes;
    private List<Edge> edges;
    private boolean directed;
    private boolean weighted;
    public Graph(HashMap<String, Node> nodes, List<Edge> edges, boolean directed, boolean weighted) {
        this.nodes = nodes;
        this.edges = edges;
        this.directed = directed;
        this.weighted = weighted;
    }

    @Override
    public TokenKind getType(Environment env) {
        return TokenKind.GRAPH;
    }

    // expose as a list of nodes
    public List<Node> getNodes() {
        return new ArrayList<>(nodes.values());
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public Set<String> getNodeIDs() {
        return nodes.keySet();
    }

    public Node getNodeByID(String nodeID) {
        try {
            if (!nodes.containsKey(nodeID)) {
                throw new IllegalArgumentException("Graph has no node with ID '" + nodeID + "'.");
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return null;
        }
        return nodes.get(nodeID);
    }

    public boolean isDirected() {
        return directed;
    }

    public void addNode(String NodeID, Node node) {
        this.nodes.put(NodeID, node);
    }

    public void addEdge(Edge edge) {
        this.edges.add(edge);
    }

    public void removeNode(Node node) {
        this.nodes.remove(node);
    }

    public void removeEdge(Edge edge) {
        this.edges.remove(edge);
    }

    public int getNodeCount() {
        return nodes.size();
    }

    public int getEdgeCount() {
        return edges.size();
    }

    public boolean containsNode(String nodeID) {
        return nodes.containsKey(nodeID);
    }

    public boolean containsEdge(Edge edge) {
        return edges.contains(edge);
    }

    public void clear() {
        this.nodes.clear();
        this.edges.clear();
    }

    public void setDirected(boolean directed) {
        this.directed = directed;
    }

    public boolean isEmpty() {
        return nodes.isEmpty() && edges.isEmpty();
    }

    public boolean hasNodes() {
        return !nodes.isEmpty();
    }

    public boolean hasEdges() {
        return !edges.isEmpty();
    }

    public boolean isCyclic() {
        // Placeholder for cycle detection logic
        return false;
    }

    public boolean isConnected() {
        // Placeholder for connectivity check logic
        return false;
    }

    public boolean isTree() {
        // Placeholder for tree check logic
        return false;
    }

    public boolean isBipartite() {
        // Placeholder for bipartiteness check logic
        return false;
    }

    public boolean isPlanar() {
        // Placeholder for planarity check logic
        return false;
    }

    public boolean isComplete() {
        // Placeholder for completeness check logic
        return false;
    }

    public boolean isEulerian() {
        // Placeholder for Eulerian path/circuit check logic
        return false;
    }

    public boolean isHamiltonian() {
        // Placeholder for Hamiltonian path/circuit check logic
        return false;
    }

    public boolean isWeighted() {
        return weighted;
    }

    public boolean isDirectedAcyclicGraph() {
        return directed && !isCyclic();
    }

    public List<Node> getNeighbors(Node node) {
        // Placeholder for neighbor retrieval logic
        return null;
    }

    public List<Edge> getIncidentEdges(Node node) {
        // Placeholder for incident edge retrieval logic
        return null;
    }

    public int getDegree(Node node) {
        // Placeholder for degree calculation logic
        return 0;
    }

    public int getInDegree(Node node) {
        // Placeholder for in-degree calculation logic
        return 0;
    }

    public int getOutDegree(Node node) {
        // Placeholder for out-degree calculation logic
        return 0;
    }

    public List<Graph> getConnectedComponents() {
        // Placeholder for connected component retrieval logic
        return null;
    }

    public List<Graph> getStronglyConnectedComponents() {
        // Placeholder for strongly connected component retrieval logic
        return null;
    }

    public List<Graph> getWeaklyConnectedComponents() {
        // Placeholder for weakly connected component retrieval logic
        return null;
    }

    public Graph transpose() {
        // Placeholder for graph transposition logic
        return null;
    }

    public Graph clone() {
        // Placeholder for graph cloning logic
        return null;
    }

    @Override
    public Expression evaluate(Environment env) {
        // Evaluate nodes and edges if they have expressions
        for (Node node : nodes.values()) {
            node.evaluate(env);
        }
        for (Edge edge : edges) {
            edge.evaluate(env);
        }
        return this;
    }

    @Override
    public String toString() {
        return "Graph(nodes: " + nodes.size() + ", edges: " + edges.size() + ", directed: " + directed + ", weighted: " + weighted + ")" +
                "\nNodes: " + nodes +
                "\nEdges: " + edges;
    }
}
