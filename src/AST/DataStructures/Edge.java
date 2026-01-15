//package AST.Nodes.DataStructures;
//
//import AST.Expressions.Expression;
//import Runtime.Environment;
//import Lexer.TokenKind;
//import Util.ErrorHandler;
//
//public class Edge extends Expression {
//    private final Node from;
//    private final Node to;
//    private Expression weight;
//    private boolean directed;
//    public Edge(Node from, Node to, Expression weight, boolean directed) {
//        if (from == null || to == null) {
//            throw new IllegalArgumentException("Edge nodes must both be non-null, Node objects. Got from: " + from + ", to: " + to + ". Please make sure to initialize both nodes before creating an edge.");
//        }
//        this.from = from;
//        this.to = to;
//        this.weight = weight;
//        this.directed = directed;
//    }
//
//    public Node getFrom() {
//        return from;
//    }
//
//    public Node getTo() {
//        return to;
//    }
//
//    public Expression getWeight() {
//        if (weight == null) {
//            throw new IllegalStateException("Edges of unweighted graph have no weight.");
//        }
//        return weight;
//    }
//
//    public void setWeight(Expression weight) {
//        this.weight = weight;
//    }
//
//    public boolean hasWeight() {
//        return weight != null;
//    }
//
//    public boolean connects(Node node) {
//        return from.equals(node) || to.equals(node);
//    }
//
//    public boolean isDirected() {
//        return directed;
//    }
//
//    public void setDirected(boolean directed) {
//        this.directed = directed;
//    }
//
//    @Override
//    public Expression evaluate(Environment env) {
//        weight = (weight != null) ? weight.evaluate(env) : null;
//        return this;
//    }
//
//    public TokenKind getType(Environment env) {
//        return TokenKind.EDGE;
//    }
//
//    public boolean isWeighted() {
//        return weight != null;
//    }
//
//    public String getID() {
//        return from.getId() +  to.getId();
//    }
//
//    public TokenKind getWeightType(Environment env) {
//        if (weight == null) {
//            throw new IllegalStateException("Edges of unweighted graph have no weight type.");
//        }
//        return weight.getType(env);
//    }
//
//    @Override
//    public String toString() {
//        return "Edge(from: " + from + ", to: " + to + (weight != null ? ", weight: " + weight : "") + ")";
//    }
//
//    private void throwError(String message) {
//        throw new ErrorHandler("Edge", -1, message, "Please ensure both nodes are valid Node objects.");
//    }
//}
