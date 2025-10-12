package AST.Nodes.DataStructures;

import AST.Nodes.Expressions.Expression;
import Interpreter.Runtime.Environment;

import java.util.List;

public class Node extends Expression {
    private Expression value;
    private List<Edge> edges;
    private List<Node> neighbors;
    public Node(Expression value) {
        this.value = value;
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
        return edges.size();
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

    @Override
    public Expression evaluate(Environment env) {
        return null;
    }

    @Override
    public String toString() {
        return null;
    }
}
