//package AST.Nodes.Expressions.Functions.BuiltIns.Graphs;
//
//import AST.BuiltIns.Functions.Expressions.BuiltInFunctionSymbol;
//
//public class removeNode extends BuiltInFunctionSymbol {
//    public removeNode() {
//        super("removeNode", null);
//    }
//
//    @Override
//    public Object call(Semantic.ScopeStack env, java.util.List<Object> args) {
//        if (args.size() != 2) {
//            throw new IllegalArgumentException("removeNode(graph, [str] nodeID) requires exactly 2 arguments.");
//        }
//        AST.Nodes.DataStructures.Graph g = (AST.Nodes.DataStructures.Graph) args.get(0);
//        String nodeID = args.get(1).toString();
//        g.removeIncidentEdges(nodeID);
//        return null;
//    }
//}
