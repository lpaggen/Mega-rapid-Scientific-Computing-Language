package AST.Nodes.Expressions.Functions.BuiltIns.Graphs;

import AST.Nodes.Expressions.Functions.BuiltIns.BuiltInFunctionSymbol;
import Interpreter.Runtime.Environment;
import Interpreter.Tokenizer.TokenKind;

import java.util.List;

public class getAdjacency extends BuiltInFunctionSymbol {
    public getAdjacency() {
        super("getAdj", TokenKind.MATRIX);
    }

    @Override
    public Object call(Environment env, List<Object> args) {
        if (args.size() != 1)
            throw new IllegalArgumentException("getAdj(graph) requires 1 argument.");

        Object obj = args.getFirst();

        if (obj instanceof AST.Nodes.DataStructures.Graph g) {
            return g.getAdjacencyMatrix();
        } else {
            throw new IllegalArgumentException("getAdj requires a Graph as its argument.");
        }
    }
}
