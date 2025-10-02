package AST.Nodes.Functions.BuiltIns.Linalg;

import AST.Nodes.Functions.BuiltIns.StandardLib.*;
import Interpreter.Parser.Symbol;

import java.util.HashMap;

public class LinalgLibrary {
    public static final HashMap<String, Symbol> LinalgSymbols = new HashMap<>();

    // this is where we store all the built-in functions of the standard library
    // things like print, input, etc.
    static {
        LinalgSymbols.put("matchAny", new MatchAny());
        LinalgSymbols.put("get", new Get());
        LinalgSymbols.put("matchAll", new MatchAll());
        LinalgSymbols.put("indexOf", new IndexOf());
        LinalgSymbols.put("dot", new Dot());
        LinalgSymbols.put("shape", new Shape());
        LinalgSymbols.put("T", new Transpose());
        LinalgSymbols.put("det", new Determinant());
    }
}
