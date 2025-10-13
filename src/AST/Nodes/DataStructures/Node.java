package AST.Nodes.DataStructures;

import AST.Nodes.Expressions.Expression;
import Interpreter.Runtime.Environment;
import Interpreter.Tokenizer.TokenKind;

import java.util.List;

public class Node extends Expression {
    private Expression value;
    private List<Edge> edges;
    private List<Node> neighbors;
    private final String id;

    public Node(Expression value, String id) {
        this.value = value;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Expression getValue() {
        return value;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public List<Node> getNeighbors() {
        return neighbors;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public void setNeighbors(List<Node> neighbors) {
        this.neighbors = neighbors;
    }

    public void addEdge(Edge edge) {
        this.edges.add(edge);
    }

    public void addNeighbor(Node neighbor) {
        this.neighbors.add(neighbor);
    }

    public void removeEdge(Edge edge) {
        this.edges.remove(edge);
    }

    public void removeNeighbor(Node neighbor) {
        this.neighbors.remove(neighbor);
    }

    public int getDegree() {
        return (this.edges != null) ? this.edges.size() : 0;
    }

    public boolean isAdjacent(Node node) {
        return neighbors.contains(node);
    }

    public boolean hasEdge(Edge edge) {
        return edges.contains(edge);
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

    public boolean isConnected(Node node) {
        return isAdjacent(node);
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

    public TokenKind getType() {
        // return value.getType();
        return TokenKind.NODE;
    }

    @Override
    public Expression evaluate(Environment env) {
        value = value.evaluate(env);
        return this;
    }

    @Override
    public String toString() {
        String valStr = (value != null) ? value.toString() : "null";
        return String.format("Node(%s, value=%s)", id, valStr);
    }
}
