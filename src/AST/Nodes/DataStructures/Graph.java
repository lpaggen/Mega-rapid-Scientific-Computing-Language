package AST.Nodes.DataStructures;

import AST.Nodes.DataTypes.FloatConstant;
import AST.Nodes.DataTypes.IntegerConstant;
import AST.Nodes.Expressions.Expression;
import Interpreter.Runtime.Environment;
import Interpreter.Tokenizer.TokenKind;
import Util.WarningLogger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Graph extends Expression {
    private HashMap<String, Node> nodes;
    private HashMap<String, Edge> edges;
    private boolean directed;
    private final boolean weighted;
    private final TokenKind weightType; // null if unweighted
    private final WarningLogger logger = new WarningLogger();
    public Graph(HashMap<String, Node> nodes, HashMap<String, Edge> edges, boolean directed, boolean weighted, TokenKind weightType) {
        this.nodes = nodes;
        this.edges = edges;
        this.directed = directed;
        this.weighted = weighted;
        this.weightType = weightType;
    }

    @Override
    public TokenKind getType(Environment env) {
        return TokenKind.GRAPH;
    }

    public TokenKind getWeightType() {
        return weightType;
    }

    // expose as a list of nodes
    public List<Node> getNodes() {
        return new ArrayList<>(nodes.values());
    }

    public List<Edge> getEdges() {
        return new ArrayList<>(edges.values());
    }

    public HashMap<String, Node> getNodeMap() {
        return nodes;
    }

    public HashMap<String, Edge> getEdgeMap() {
        return edges;
    }

    public Set<String> getNodeIDs() {
        return nodes.keySet();
    }

    public Set<String> getEdgeIDs() {
        return edges.keySet();
    }

    // this is for use in the GetMember built-in function
    public Expression getNodeOrEdgeByID(String id) {
        if (nodes.containsKey(id)) {
            return nodes.get(id);
        } else if (edges.containsKey(id)) {
            return edges.get(id);
        } else {
            throw new IllegalArgumentException("Graph has no node or edge with ID '" + id + "'. " +
                                                "Make sure the direction is correct when looking" +
                                                " for edges in directed graphs.");
        }
    }

    public boolean isDirected() {
        return directed;
    }

    public void addNode(String NodeID, Node node) {
        this.nodes.put(NodeID, node);
    }

    public void addEdge(String edgeReference, Edge edge) {
        this.edges.put(edgeReference, edge);
    }

    public void removeNode(String nodeID) {
        this.nodes.remove(nodeID);
    }

    public void removeEdge(String edgeID) {
        this.edges.remove(edgeID);
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

    public boolean containsEdge(String edgeID) {
        return edges.containsKey(edgeID);
    }

    // you can add edges, graphs, nodes to a graph, nothing else
    public static Graph add(Expression left, Expression right) {
        left = left instanceof Graph g ? g : null;
        right = right instanceof Graph g ? g : null;
        if (left != null && right != null) {
            return ((Graph) left).addGraphToGraph((Graph) right);
        } else if (left != null && right instanceof Node n) {
            return ((Graph) left).addNodeToGraph(n);
        } else if (left != null && right instanceof Edge e) {
            return ((Graph) left).addEdgeToGraph(e);
        } else if (right != null && left instanceof Node n) {
            return ((Graph) right).addNodeToGraph(n);
        } else if (right != null && left instanceof Edge e) {
            return ((Graph) right).addEdgeToGraph(e);
        }
        return null;
    }

    private Graph addGraphToGraph(Graph graph) {
        for (Node node : graph.getNodes()) {
            this.addNodeToGraph(node);
        }
        for (Edge edge : graph.getEdges()) {
            this.addEdgeToGraph(edge);
        }
        return this;
    }

    private Graph addNodeToGraph(Node node) {
        this.nodes.put(node.getId(), node);
        return this;
    }

    private Graph addEdgeToGraph(Edge edge) {
        this.edges.put(edge.getID(), edge);
        return this;
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

    public Matrix getAdjacencyMatrix() {
        int n = nodes.size();
        Expression[][] matrix = new Expression[n][n];
        List<String> nodeIDs = new ArrayList<>(nodes.keySet());
        Expression zero = new IntegerConstant(0);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = zero;
            }
        }
        for (Edge edge : edges.values()) {
            int fromIndex = nodeIDs.indexOf(edge.getFrom().getId());
            int toIndex = nodeIDs.indexOf(edge.getTo().getId());
            Expression weight = edge.getWeight() != null ? edge.getWeight() : new IntegerConstant(1);
            matrix[fromIndex][toIndex] = weight;
            if (!directed) {
                matrix[toIndex][fromIndex] = weight;
            }
        }
        return new Matrix(matrix, TokenKind.MATH);
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
        for (Node node : nodes.values()) {
            node.evaluate(env);
            Expression weight = node.getValue();
            TokenKind nodeWeightType = weight.getType(env);
            if (nodeWeightType == TokenKind.FLOAT && this.weightType == TokenKind.INTEGER) {
                double val = ((FloatConstant) weight).getValue().doubleValue();
                node.setValue(new IntegerConstant((int) val)); // truncates
                 nodeWeightType = TokenKind.INTEGER;
//                logger.addWarning(2, "Edge weight " + val + " truncated to " + (int) val + " to match graph weight type INTEGER.", -1);
//                logger.logWarningsToFile();
            }
            else if (nodeWeightType == TokenKind.INTEGER && this.weightType == TokenKind.FLOAT) {
                int val = ((IntegerConstant) weight).getValue().intValue();
                node.setValue(new FloatConstant((double) val)); // convert to float
                nodeWeightType = TokenKind.FLOAT;
            }
            if (nodeWeightType != this.weightType && this.weighted) {
                throw new IllegalArgumentException("Node weight type " + nodeWeightType +
                        " does not match graph weight type " + this.weightType);
            }
        }
        for (Edge edge : edges.values()) {
            edge.evaluate(env);
            Expression weight = edge.getWeight();
            TokenKind edgeWeightType = weight.getType(env);
            if (edgeWeightType == TokenKind.FLOAT && this.weightType == TokenKind.INTEGER) {
                double val = ((FloatConstant) weight).getValue().doubleValue();
                edge.setWeight(new IntegerConstant((int) val)); // truncates
                edgeWeightType = TokenKind.INTEGER;
//                logger.addWarning(2, "Edge weight " + val + " truncated to " + (int) val + " to match graph weight type INTEGER.", -1);
//                logger.logWarningsToFile();
            }
            else if (edgeWeightType == TokenKind.INTEGER && this.weightType == TokenKind.FLOAT) {
                int val = ((IntegerConstant) weight).getValue().intValue();
                edge.setWeight(new FloatConstant((double) val)); // convert to float
                edgeWeightType = TokenKind.FLOAT;
            }
            if (edgeWeightType != this.weightType && this.weighted) {
                throw new IllegalArgumentException("Edge weight type " + edgeWeightType +
                        " does not match graph weight type " + this.weightType);
            }
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
