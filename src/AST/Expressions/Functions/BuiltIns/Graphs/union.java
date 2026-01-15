//package AST.Nodes.Expressions.Functions.BuiltIns.Graphs;
//
//import AST.Nodes.Conditional.BooleanNode;
//import AST.Nodes.DataStructures.Graph;
//import AST.Expressions.Expression;
//import AST.BuiltIns.Functions.Expressions.BuiltInFunctionSymbol;
//import AST.Expressions.StringNode;
//import Runtime.Environment;
//import Lexer.TokenKind;
//
//import java.util.List;
//
//public class union extends BuiltInFunctionSymbol {
//    public union() {
//        super("union", TokenKind.GRAPH);
//    }
//
//    @Override
//    public Object call(Environment env, List<Object> args) {
//        if (args.size() < 2 || args.size() > 5) {
//            throw new IllegalArgumentException("union(graph1, graph2) requires 2 arguments: graph1, graph2, and 3 optional arguments: [optional] disjointMerge, [optional] nodeStrategy, [optional] edgeStrategy.");
//        }
//        Expression g1 = (Expression) args.get(0);
//        Expression g2 = (Expression) args.get(1);
//        boolean disjointMerge = args.size() >= 3 && args.get(2) instanceof BooleanNode d && d.getValue();
//        String nodeStrategy = args.size() >= 4 && args.get(3) instanceof StringNode ns ? ns.getValue() : "left";  // default left merge
//        String edgeStrategy = args.size() == 5 && args.get(4) instanceof StringNode es ? es.getValue() : "left";
//        System.out.println("Union called with disjointMerge=" + disjointMerge + ", nodeStrategy=" + nodeStrategy + ", edgeStrategy=" + edgeStrategy);
//        if (args.get(0) == null || args.get(1) == null) {
//            throw new IllegalArgumentException("Graph arguments to union cannot be null.");
//        }
//        if (g1 instanceof Graph && g2 instanceof Graph) {
//            return ((Graph) g1).union((Graph) g2, disjointMerge, nodeStrategy, edgeStrategy);
//        }
//        else {
//            throw new IllegalArgumentException("union requires two Graphs as its arguments. Got " +
//                    g1.evaluate(env).getClass().getSimpleName() + " and " +
//                    g2.evaluate(env).getClass().getSimpleName());
//        }
//    }
//
//    @Override
//    public String toString() {
//        return "Built-in function: union";
//    }
//}
