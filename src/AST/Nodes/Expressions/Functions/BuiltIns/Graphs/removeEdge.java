package AST.Nodes.Expressions.Functions.BuiltIns.Graphs;

import AST.Nodes.Expressions.Functions.BuiltIns.BuiltInFunctionSymbol;
import Interpreter.Runtime.Environment;

import java.util.List;

public class removeEdge extends BuiltInFunctionSymbol {
    public removeEdge() {
        super("removeEdge", null);
    }

    @Override
    public Object call(Environment env, List<Object> args) {
        if (args.size() != 3) {
            throw new IllegalArgumentException("removeEdge(graph, [str] nodeID1, [str] nodeID2) requires exactly 3 arguments.");
        }
        AST.Nodes.DataStructures.Graph g = (AST.Nodes.DataStructures.Graph) args.get(0);
        String from = args.get(1).toString();
        String to = args.get(2).toString();
        g.removeEdge(from + to);
        return null;
    }
}
