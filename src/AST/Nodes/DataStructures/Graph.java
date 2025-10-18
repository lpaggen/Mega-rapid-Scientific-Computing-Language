package AST.Nodes.DataStructures;

import AST.Nodes.DataTypes.Scalar;
import AST.Nodes.Expressions.Expression;
import Interpreter.Runtime.Environment;
import Interpreter.Tokenizer.TokenKind;
import Util.WarningLogger;

import java.util.*;

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

    // here we also somehow need to check the resulting edges
    // if a node is removed, all edges connected to it must also be removed
    public Graph removeNode(String nodeID) {
        this.nodes.remove(nodeID);
        return this;
    }

    public Graph removeEdge(String edgeID) {
        if (!edges.containsKey(edgeID)) {
            throw new IllegalArgumentException("Graph has no edge with ID '" + edgeID + "'. Cannot remove edge.");
        }
        this.edges.remove(edgeID);
        return this;
    }

    // should remove all edges incident to the node!
    public void removeIncidentEdges(String nodeID) {
        if (!nodes.containsKey(nodeID)) {
            throw new IllegalArgumentException("Graph has no node with ID '" + nodeID + "'. Cannot remove incident edges.");
        }
        edges.values().removeIf(edge -> edge.getFrom().getId().equals(nodeID) || edge.getTo().getId().equals(nodeID));
        nodes.remove(nodeID);
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
    // addition operator does not allow duplicates, see union for that
    public static Graph add(Expression left, Expression right) {
        if (left == null || right == null) {
            throw new IllegalArgumentException("Cannot add null to a graph.");
        } else if (left instanceof Graph g && right instanceof Node n) {
            return (g).addNodeToGraph(n, false, false, "left");  // strategy doesn't matter here
        } else if (left instanceof Graph g && right instanceof Edge e) {
            return (g).addEdgeToGraph(e, false, false, "left");
        } else if (right instanceof Graph g && left instanceof Node n) {
            return (g).addNodeToGraph(n, false, false, "left");
        } else if (right instanceof Graph g && left instanceof Edge e) {
            return (g).addEdgeToGraph(e, false, false, "left");
        } else if (left instanceof Graph g && right instanceof Graph h) {
            return (g).addGraphToGraph(h, false, false, "left", "left");
        } else {
            throw new IllegalArgumentException("Can only add nodes, edges, or graphs to a graph. Got: " + left + " and " + right);
        }
    }

    public static Graph sub(Expression left, Expression right) {
        if (left == null || right == null) {
            throw new IllegalArgumentException("Cannot subtract null from a graph.");
        }
        else if (left instanceof Graph && right instanceof Graph) {  // what do we do ...
            throw new IllegalArgumentException("Subtracting one graph from another is ambiguous. Please specify edges/nodes to remove.");
        }
        else if (left instanceof Graph g && right instanceof Edge e) {
            return g.removeEdge(e.getID());
        }
        else if (left instanceof Graph g && right instanceof Node n) {
            return g.removeNode(n.getId());
        }
        else if (right instanceof Graph g && left instanceof Edge e) {
            return g.removeEdge(e.getID());
        }
        else if (right instanceof Graph g && left instanceof Node n) {
            return g.removeNode(n.getId());
        }
        else {
            throw new IllegalArgumentException("Can only subtract nodes or edges from a graph. Got: " + left + " and " + right);
        }
    }

    private Graph addGraphToGraph(Graph graph, boolean tolerateDuplicates, boolean disjointUnion, String nodeStrategy, String edgeStrategy) {
        for (Node node : graph.getNodes()) {
            this.addNodeToGraph(node, tolerateDuplicates, disjointUnion, nodeStrategy);
        }
        for (Edge edge : graph.getEdges()) {
            this.addEdgeToGraph(edge, tolerateDuplicates, disjointUnion, edgeStrategy);
        }
        return this;
    }

    private Graph addNodeToGraph(Node node, boolean tolerateDuplicates, boolean disjointUnion, String nodeStrategy) {
        Node nodeToAdd = node;
        if (this.containsNode(node.getId()) && !tolerateDuplicates) {
            throw new IllegalArgumentException("Add operation is ambiguous for graph which already contains a node with ID '" + node.getId() + "'. Node IDs must be unique within a graph. \nPlease see union for merging graphs with duplicate node IDs.");
        }
        else if (this.containsNode(node.getId()) && tolerateDuplicates && disjointUnion) {
            String newID = node.getId();
            int counter = 1;
            while (this.containsNode(newID)) {
                newID = node.getId() + "_" + counter;
                counter++;
            }
            nodeToAdd = new Node(node.getValue(), newID);
            this.nodes.put(nodeToAdd.getId(), nodeToAdd);  // add renamed node in case disjoint union
        }
        if (this.isWeighted() && node.getValue() == null) {
            nodeToAdd = new Node(new Scalar(1), node.getId());  // init with default weight of 1
        }
        else if (this.isWeighted() && node.getValue() != null) {
            Number mergedWeight = getMergeStrategy(
                    ((Scalar) this.nodes.get(node.getId()).getValue()).getValue(),
                    ((Scalar) node.getValue()).getValue(),
                    nodeStrategy);
            nodeToAdd = new Node(new Scalar(mergedWeight), node.getId());
        }
        System.out.println("Adding node to graph: " + nodeToAdd.getId());
        this.nodes.put(nodeToAdd.getId(), nodeToAdd);
        return this;
    }

    // will clean this in future build, this is impossible to read
    private Graph addEdgeToGraph(Edge edge, boolean tolerateDuplicates, boolean disjointUnion, String edgeStrategy) {
        Edge edgeToAdd = edge;
        if (this.containsEdge(edge.getID()) && !tolerateDuplicates) {
            throw new IllegalArgumentException("Graph already contains an edge with ID '" + edge.getID() + "'. Edge IDs must be unique within a graph.");
        }
        else if (this.containsEdge(edge.getID()) && tolerateDuplicates && disjointUnion) {
            String newFromID = edge.getFrom().getId();
            String newToID = edge.getTo().getId();
            int counter = 1;
            while (this.containsEdge(newFromID + newToID)) {
                newFromID = newFromID + "_" + counter;
                newToID = newToID + "_" + counter;
                counter++;
            }
            Node newFrom = new Node(edge.getFrom().getValue(), newFromID);
            Node newTo = new Node(edge.getTo().getValue(), newToID);
            edgeToAdd = new Edge(newFrom, newTo, edge.getWeight(), edge.isDirected());
            System.out.println("Renaming edge from " + edge.getID() + " to " + edgeToAdd.getID() + " for disjoint union.");
            this.edges.put(edgeToAdd.getID(), edgeToAdd);  // add renamed edge in case disjoint union
        }
        if (this.isWeighted() && edge.getWeight() == null) {
            edgeToAdd = new Edge(edge.getFrom(), edge.getTo(), new Scalar(1), edge.isDirected());  // init with default weight of 1
        }
        else if (this.isWeighted() && edge.getWeight() != null && this.containsEdge(edge.getID())) {
            Number mergedWeight = getMergeStrategy(
                    ((Scalar) this.edges.get(edge.getID()).getWeight()).getValue(),
                    ((Scalar) edge.getWeight()).getValue(),
                    edgeStrategy);
            edgeToAdd = new Edge(edge.getFrom(), edge.getTo(), new Scalar(mergedWeight), edge.isDirected());
        }
        if (!this.containsNode(edge.getFrom().getId()) || !this.containsNode(edge.getTo().getId())) {
            throw new IllegalArgumentException("Both nodes of the edge must be present in the graph before adding the edge. Missing node(s) in edge: " + edge);
        }
        if (this.isDirected() && !edge.isDirected()) {
            edgeToAdd = new Edge(edge.getFrom(), edge.getTo(), edge.getWeight(), true); // make edge directed
            Edge edgeToAdd2 = new Edge(edge.getTo(), edge.getFrom(), edge.getWeight(), true); // add reverse edge for directed graph
            this.edges.put(edgeToAdd2.getID(), edgeToAdd2);
        } else if (!this.isDirected() && edge.isDirected()) {
            throw new IllegalArgumentException("Cannot add a directed edge to an undirected graph. Edge: " + edge);
        }
        this.edges.put(edgeToAdd.getID(), edgeToAdd);
        return this;
    }

    private Number getMergeStrategy(Number a, Number b, String how) {
        switch (how) {
            case "left" -> {
                return a;
            }
            case "right" -> {
                return b;
            }
            case "sum" -> {
                return a.doubleValue() + b.doubleValue();
            }
            case "max" -> {
                return Math.max(a.doubleValue(), b.doubleValue());
            }
            case "min" -> {
                return Math.min(a.doubleValue(), b.doubleValue());
            }
            case "avg" -> {
                return (a.doubleValue() + b.doubleValue()) / 2.0;
            }
            default -> throw new IllegalArgumentException("Unknown graph union merge strategy: " + how);
        }
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
        Expression zero = new Scalar(0);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = zero;
            }
        }
        for (Edge edge : edges.values()) {
            int fromIndex = nodeIDs.indexOf(edge.getFrom().getId());
            int toIndex = nodeIDs.indexOf(edge.getTo().getId());
            Expression weight = edge.getWeight() != null ? edge.getWeight() : new Scalar(1);
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

    public int getMaxDegree() {
        // Placeholder for max degree calculation logic
        return 0;
    }

    public int getMinDegree() {
        // Placeholder for min degree calculation logic
        return 0;
    }

    public double getAverageDegree() {
        // Placeholder for average degree calculation logic
        return 0.0;
    }

    public double getDensity() {
        // Placeholder for density calculation logic
        return 0.0;
    }

    public int getDiameter() {
        // Placeholder for diameter calculation logic
        return 0;
    }

    public double getClusteringCoefficient() {
        // Placeholder for clustering coefficient calculation logic
        return 0.0;
    }

    public List<List<Node>> getAllPaths(Node start, Node end) {
        // Placeholder for path retrieval logic
        return null;
    }

    public List<Node> getShortestPath(Node start, Node end) {
        // Placeholder for shortest path retrieval logic
        return null;
    }

    // union tolerates the duplicates, merges the two graphs
    // disjointUnion renames nodes/edges if there are duplicates
    // strategy is to keep weights from left, right, or sum, etc
    public Graph union(Graph other, boolean disjointUnion, String nodeStrategy, String edgeStrategy) {
        Graph result = this.clone();
        return result.addGraphToGraph(other, true, disjointUnion, nodeStrategy, edgeStrategy);
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
        HashMap<String, Node> newNodes = new HashMap<>();
        for (Node node : nodes.values()) {
            newNodes.put(node.getId(), new Node(node.getValue(), node.getId()));
        }
        HashMap<String, Edge> newEdges = new HashMap<>();
        for (Edge edge : edges.values()) {
            Node newFrom = newNodes.get(edge.getFrom().getId());
            Node newTo = newNodes.get(edge.getTo().getId());
            newEdges.put(edge.getID(), new Edge(newFrom, newTo, edge.getWeight(), edge.isDirected()));
        }
        return new Graph(newNodes, newEdges, directed, weighted, weightType);
    }

    @Override
    public Expression evaluate(Environment env) {
        for (Node node : nodes.values()) {
            node.evaluate(env);
            Expression weight = node.getValue();
            TokenKind nodeWeightType = weight.getType(env);
//            if (nodeWeightType == TokenKind.FLOAT && this.weightType == TokenKind.INTEGER) {
//                double val = ((Scalar) weight).getValue().doubleValue();
//                node.setValue(new Scalar((int) val)); // truncates
//                 nodeWeightType = TokenKind.INTEGER;
////                logger.addWarning(2, "Edge weight " + val + " truncated to " + (int) val + " to match graph weight type INTEGER.", -1);
////                logger.logWarningsToFile();
//            }
//            else if (nodeWeightType == TokenKind.INTEGER && this.weightType == TokenKind.FLOAT) {
//                int val = ((Scalar) weight).getValue().intValue();
//                node.setValue(new Scalar((double) val)); // convert to float
//                nodeWeightType = TokenKind.FLOAT;
//            }
            if (nodeWeightType != this.weightType && this.weighted) {
                throw new IllegalArgumentException("Node weight type " + nodeWeightType +
                        " does not match graph weight type " + this.weightType);
            }
        }
        for (Edge edge : edges.values()) {
            edge.evaluate(env);
            Expression weight = edge.getWeight();
            TokenKind edgeWeightType = weight.getType(env);
//            if (edgeWeightType == TokenKind.FLOAT && this.weightType == TokenKind.INTEGER) {
//                double val = ((Scalar) weight).getValue().doubleValue();
//                edge.setWeight(new Scalar((int) val)); // truncates
//                edgeWeightType = TokenKind.INTEGER;
////                logger.addWarning(2, "Edge weight " + val + " truncated to " + (int) val + " to match graph weight type INTEGER.", -1);
////                logger.logWarningsToFile();
//            }
//            else if (edgeWeightType == TokenKind.INTEGER && this.weightType == TokenKind.FLOAT) {
//                int val = ((Scalar) weight).getValue().intValue();
//                edge.setWeight(new Scalar((double) val)); // convert to float
//                edgeWeightType = TokenKind.FLOAT;
//            }
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
