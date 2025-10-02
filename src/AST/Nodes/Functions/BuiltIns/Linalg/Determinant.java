package AST.Nodes.Functions.BuiltIns.Linalg;

import AST.Nodes.Functions.BuiltIns.BuiltInFunctionSymbol;
import Interpreter.Runtime.Environment;

import java.util.List;

public class Determinant extends BuiltInFunctionSymbol {
    public Determinant() {
        super("det", null); // Return type can be defined as needed
    }

    @Override
    public Object call(Environment env, List<Object> args) {
        // check params
        if (args.size() != 1) {
            throw new IllegalArgumentException("Determinant function requires exactly one argument.");
        }
        Object arg = args.getFirst();
        if (arg instanceof AST.Nodes.DataStructures.Matrix mat) {
            System.out.println("Calculating Determinant of a Matrix");
            return mat.determinant();
        }
        throw new IllegalArgumentException("Determinant function requires a Matrix as an argument.");
    }
}
