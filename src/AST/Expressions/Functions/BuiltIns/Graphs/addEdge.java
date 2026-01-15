//package AST.Nodes.Expressions.Functions.BuiltIns.Graphs;
//
//import AST.Nodes.DataStructures.Edge;
//import AST.Nodes.DataStructures.Graph;
//import AST.Nodes.DataStructures.Node;
//import AST.Expressions.Expression;
//import AST.BuiltIns.Functions.Expressions.BuiltInFunctionSymbol;
//import Runtime.Environment;
//
//import java.util.List;
//
//public class addEdge extends BuiltInFunctionSymbol {
//    public addEdge() {
//        super("addEdge", null);
//    }
//
//    @Override
//    public Object call(Environment env, List<Object> args) {
//        if (args.size() < 3 || args.size() > 4) {
//            throw new IllegalArgumentException("addEdge(graph, [str] nodeID1, [str] nodeID2, [optional, expr]) requires at least 3 arguments, and takes at most 4 arguments.");
//        }
//        Graph g = (Graph) args.getFirst();
//        String from = args.get(1).toString();
//        String to = args.get(2).toString();
//        Object value = null;
//        if (args.size() == 4) {
//            value = args.get(3);
//        }
//        Edge edgeToAdd = new Edge((Node) g.getNodeOrEdgeByID(from), (Node) g.getNodeOrEdgeByID(to), (Expression) value, g.isDirected());
//        g.addEdge(edgeToAdd.getID(), edgeToAdd);
//        return null;
//    }
//}
