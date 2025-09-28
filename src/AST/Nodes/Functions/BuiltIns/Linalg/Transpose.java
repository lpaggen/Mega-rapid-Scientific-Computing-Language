package AST.Nodes.Functions.BuiltIns.Linalg;

import AST.Nodes.Functions.BuiltIns.BuiltInFunctionSymbol;
import Interpreter.Tokenizer.TokenKind;

public class Transpose extends BuiltInFunctionSymbol {
    public Transpose() {
        super("T", TokenKind.VECTOR);
    }

    @Override
    public Object call(Interpreter.Runtime.Environment env, java.util.List<Object> args) {
        if (args.size() != 1) {
            throw new IllegalArgumentException("Transpose function requires exactly one argument.");
        }
        Object arg = args.getFirst();
        if (arg instanceof AST.Nodes.DataStructures.Matrix mat) {
            System.out.println("Transposing a Matrix");
            return mat.transpose();
        } else if (arg instanceof AST.Nodes.DataStructures.Vector vec) {
            System.out.println("Transposing a Vector");
            return vec.transpose();
        } else {
            throw new IllegalArgumentException("Transpose function requires a Vector-like as an argument.");
        }
    }
}
