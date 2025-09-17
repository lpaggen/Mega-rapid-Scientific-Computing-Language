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
//        LinalgSymbols.put("countMatches", new Type());
//        LinalgSymbols.put("noneMatch", new Cast()); // i had a cool int() etc version but it conflicts with reserved keywords like int, float, etc.
    }
}
