//package AST.Expressions.Functions.BuiltIns.Graphs;
//
//import AST.Expressions.Functions.BuiltIns.BuiltInFunctionSymbol;
//import Semantic.Environment;
//import Lexer.TokenKind;
//
//import java.util.List;
//
//public class getAdjacency extends BuiltInFunctionSymbol {
//    public getAdjacency() {
//        super("getAdj", TokenKind.MATRIX);
//    }
//
//    @Override
//    public Object call(Environment env, List<Object> args) {
//        if (args.size() != 1)
//            throw new IllegalArgumentException("getAdj(graph) requires 1 argument.");
//
//        Object obj = args.getFirst();
//
//        if (obj instanceof AST.Nodes.DataStructures.Graph g) {
//            return g.getAdjacencyMatrix();
//        } else {
//            throw new IllegalArgumentException("getAdj requires a Graph as its argument.");
//        }
//    }
//
//    @Override
//    public TokenKind getReturnType() {
//        return TokenKind.MATRIX;
//    }
//
//    @Override
//    public String help() {
//        return """
//                getAdj(graph): Returns the adjacency matrix of the given graph.
//                      Parameters:
//                  graph - The graph for which to retrieve the adjacency matrix.
//                      Returns:
//                  A matrix representing the adjacency matrix of the graph.""";
//    }
//}
