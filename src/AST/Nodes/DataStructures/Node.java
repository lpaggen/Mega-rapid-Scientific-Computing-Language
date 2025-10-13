package AST.Nodes.DataStructures;

import AST.Nodes.DataTypes.IntegerConstant;
import AST.Nodes.Expressions.Expression;
import Interpreter.Runtime.Environment;
import Interpreter.Tokenizer.TokenKind;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Node extends Expression {
    private Expression value;
    private HashMap<String, Edge> edges;
    private HashMap<String, Node> neighbors;
    private final String id;

    public Node(Expression value, String id) {
        this.value = value;
        this.id = id;
        this.edges = new HashMap<>();
        this.neighbors = new HashMap<>();
    }

    public Node(String id) {
        this.id = id;
        this.edges = new HashMap<>();
        this.neighbors = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public Expression getValue() {
        if (value != null) {
            return value;
        }
        throw new IllegalStateException("Nodes of unweighted graph have no value.");
    }

    public HashMap<String, Edge> getEdges() {
        return edges;
    }

    public List<Edge> listEdges() {
        return new ArrayList<>(edges.values());
    }

    public HashMap<String, Node> getNeighbors() {
        return neighbors;
    }

    public List<Node> listNeighbors() {
        return new ArrayList<>(neighbors.values());
    }

    public void setEdges(HashMap<String, Edge> edges) {
        this.edges = edges;
    }

    public void setNeighbors(HashMap<String, Node> neighbors) {
        this.neighbors = neighbors;
    }

    public void addEdge(String edgeID, Edge edge) {
        this.edges.put(edgeID, edge);
    }

    public void addNeighbor(String neighborID, Node neighbor) {
        this.neighbors.put(neighborID, neighbor);
    }

    public void removeEdge(String edgeID) {
        this.edges.remove(edgeID);
    }

    public void removeNeighbor(String neighborID) {
        this.neighbors.remove(neighborID);
    }

    public IntegerConstant getDegree() {
        return new IntegerConstant((this.edges != null) ? this.edges.size() : 0);
    }

    public boolean isAdjacent(String nodeID) {
        return neighbors.containsKey(nodeID);
    }

    public boolean hasEdge(String edgeID) {
        return edges.containsKey(edgeID);
    }

    public boolean hasValue() {
        return value != null;
    }

    public boolean isIsolated() {
        return edges.isEmpty();
    }

    public boolean isLeaf() {
        return edges.size() == 1;
    }

    public boolean isConnected(String nodeID) {
        return isAdjacent(nodeID);
    }

    public void setValue(Expression value) {
        this.value = value; // value is final
    }

    // not so simple either
    public void clearEdges() {
        this.edges.clear();
    }

    // not so simple, need to also remove the adjacent edges from the graph
    public void clearNeighbors() {
        this.neighbors.clear();
    }

    @Override
    public TokenKind getType(Environment env) {
        return value.getType(env);
    }

    @Override
    public Expression evaluate(Environment env) {
        if (value != null) {
            value = value.evaluate(env);
        }
        return this;
    }

    @Override
    public String toString() {
        String valStr = (value != null) ? value.toString() : "null";
        return String.format("Node(%s, value=%s)", id, valStr);
    }
}
