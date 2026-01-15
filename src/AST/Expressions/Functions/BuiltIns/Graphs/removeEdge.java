//package AST.Expressions.Functions.BuiltIns.Graphs;
//
//import AST.Expressions.Functions.BuiltIns.BuiltInFunctionSymbol;
//import Runtime.Environment;
//
//import java.util.List;
//
//public class removeEdge extends BuiltInFunctionSymbol {
//    public removeEdge() {
//        super("removeEdge", null);
//    }
//
//    @Override
//    public Object call(Environment env, List<Object> args) {
//        if (args.size() != 3) {
//            throw new IllegalArgumentException("removeEdge(graph, [str] nodeID1, [str] nodeID2) requires exactly 3 arguments.");
//        }
//        Graph g = (Graph) args.get(0);
//        String from = args.get(1).toString();
//        String to = args.get(2).toString();
//        g.removeEdge(from + to);
//        return null;
//    }
//}
