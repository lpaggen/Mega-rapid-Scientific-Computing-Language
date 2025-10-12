package AST.Nodes.DataStructures;

import AST.Nodes.Expressions.Expression;
import Interpreter.Runtime.Environment;
import Util.ErrorHandler;

public class Edge extends Expression {
    private final Node from;
    private final Node to;
    private Expression weight;
    private boolean directed;
    public Edge(Node from, Node to, Expression weight, boolean directed) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Edge nodes must both be non-null, Node objects. Got from: " + from + ", to: " + to + ". Please make sure to initialize both nodes before creating an edge.");
        }
        this.from = from;
        this.to = to;
        this.weight = weight;
        this.directed = directed;
    }

    public Node getFrom() {
        return from;
    }

    public Node getTo() {
        return to;
    }

    public Expression getWeight() {
        return weight;
    }

    public void setWeight(Expression weight) {
        this.weight = weight;
    }

    public boolean hasWeight() {
        return weight != null;
    }

    public boolean connects(Node node) {
        return from.equals(node) || to.equals(node);
    }

    public boolean isDirected() {
        return directed;
    }

    public void setDirected(boolean directed) {
        this.directed = directed;
    }

    @Override
    public Expression evaluate(Environment env) {
        return null;
    }

    @Override
    public String toString() {
        return "Edge(from: " + from.toString() + ", to: " + to.toString() + (weight != null ? ", weight: " + weight.toString() : "") + ")";
    }

    private void throwError(String message) {
        throw new ErrorHandler("Edge", -1, message, "Please ensure both nodes are valid Node objects.");
    }
}
