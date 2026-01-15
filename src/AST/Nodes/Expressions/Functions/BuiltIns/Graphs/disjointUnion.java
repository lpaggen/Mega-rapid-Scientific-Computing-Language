package AST.Nodes.Expressions.Functions.BuiltIns.Graphs;

import AST.Nodes.DataStructures.Graph;
import AST.Nodes.Expressions.Expression;
import AST.Nodes.Expressions.Functions.BuiltIns.BuiltInFunctionSymbol;
import Runtime.Environment;

import java.util.List;

public class disjointUnion extends BuiltInFunctionSymbol {
    public disjointUnion() {
        super("disjointUnion", null);
    }

    @Override
    public Object call(Environment env, List<Object> args) {
        if (args.size() < 2 || args.size() > 4) {
            throw new IllegalArgumentException("union(graph1, graph2) requires 2 arguments: graph1, graph2, [optional] nodeStrategy, [optional] edgeStrategy.");
        }
        Expression g1 = (Expression) args.get(0);
        Expression g2 = (Expression) args.get(1);
        // set default strategy to left, expect something for proper use however
        String nodeStrategy = args.size() >= 3 && args.get(2) != null ? args.get(2).toString() : "left";
        String edgeStrategy = args.size() == 4 && args.get(3) != null ? args.get(3).toString() : "left";
        if (args.get(0) == null || args.get(1) == null) {
            throw new IllegalArgumentException("Arguments to union cannot be null.");
        }
        if (g1 instanceof Graph && g2 instanceof Graph) {
            return ((Graph) g1).union((Graph) g2, true, nodeStrategy, edgeStrategy);
        }
        else {
            throw new IllegalArgumentException("union requires two Graphs as its arguments. Got " +
                    g1.evaluate(env).getClass().getSimpleName() + " and " +
                    g2.evaluate(env).getClass().getSimpleName());
        }
    }

    @Override
    public String toString() {
        return "Built-in function: disjointUnion";
    }
}
