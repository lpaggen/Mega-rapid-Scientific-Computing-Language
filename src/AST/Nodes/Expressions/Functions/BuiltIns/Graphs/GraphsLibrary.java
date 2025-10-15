package AST.Nodes.Expressions.Functions.BuiltIns.Graphs;

import AST.Nodes.Expressions.Functions.BuiltIns.Linalg.*;
import Interpreter.Parser.Symbol;

import java.util.HashMap;

public class GraphsLibrary {
    public static final HashMap<String, Symbol> GraphsSymbols = new HashMap<>();

    static {
        GraphsSymbols.put("isBipartite", new isBipartite());
        GraphsSymbols.put("getAdj", new getAdjacency());
//        GraphsSymbols.put("matchAll", new MatchAll());
//        GraphsSymbols.put("indexOf", new IndexOf());
//        GraphsSymbols.put("dot", new Dot());
//        GraphsSymbols.put("shape", new Shape());
//        GraphsSymbols.put("T", new Transpose());
//        GraphsSymbols.put("det", new Determinant());
    }
}
