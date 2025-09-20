package AST.Nodes.Functions.BuiltIns.Linalg;

import AST.Nodes.Conditional.BooleanNode;
import AST.Nodes.DataStructures.Array;
import AST.Nodes.DataStructures.Vector;
import AST.Nodes.Expression;
import AST.Nodes.Functions.BuiltIns.BuiltInFunctionSymbol;
import Interpreter.Runtime.Environment;

import java.util.List;

public class Dot extends BuiltInFunctionSymbol {

    public Dot() {
        super("dot", null);  // we need a NUMERIC class eventually, to handle all these cases
    }

    @Override
    public Object call(Environment env, List<Object> args) {
        if (args.size() != 2) {
            throw new IllegalArgumentException("dot product requires exactly two arguments.");
        }
        Object m1 = args.get(0);
        Object m2 = args.get(1);
        if (!(m1 instanceof Vector ar1) || !(m2 instanceof Vector ar2)) {
            throw new IllegalArgumentException("dot product requires both arguments to be Matrix-like.");
        }
        if (ar1.length() != ar2.length()) {
            throw new IllegalArgumentException("dot product requires both arguments to be the same size.");
        }
        Expression[][] output = new Expression[ar2.length()][ar1.length()];
        for (Expression x : ar1.getElements()) {
            for (Expression y : ar2.getElements()) {
                if (!(x.equals(y))) {
                    return new BooleanNode(false);
                }
            }
        }
        return new BooleanNode(true);
    }
}
