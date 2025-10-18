package AST.Nodes.Expressions.Functions.BuiltIns.Graphs;

import AST.Nodes.DataStructures.Graph;
import AST.Nodes.DataStructures.Node;
import AST.Nodes.Expressions.Expression;
import AST.Nodes.Expressions.Functions.BuiltIns.BuiltInFunctionSymbol;
import AST.Nodes.Expressions.StringNode;
import Interpreter.Runtime.Environment;
import Interpreter.Tokenizer.TokenKind;

import java.util.List;

public class addNode extends BuiltInFunctionSymbol {
    public addNode() {
        super("addNode", TokenKind.VOID);
    }

    @Override
    public Object call(Environment env, List<Object> args) {
        if (args.size() < 2 || args.size() > 3) {
            throw new IllegalArgumentException("addNode(graph, [str] nodeID, [optional, expr] value) requires at least 2 arguments, and takes at most 3 arguments.");
        }
        Graph g = (Graph) args.get(0);
        StringNode nodeID = (StringNode) args.get(1);
        Object value = args.get(2);
        g.addNode(nodeID.getValue(), new Node((Expression) value, nodeID.getValue()));
        return null;
    }
}
