package AST.Expressions.Functions.BuiltIns.StandardLib;

import Parser.Symbol;

import java.util.HashMap;
import java.util.Set;

// this could also be contained in the parser itself, maybe this is cleaner however?
public class StandardLibrary {
    public static final HashMap<String, Symbol> builtInSymbols = new HashMap<>();

    // this is where we store all the built-in functions of the standard library
    // things like print, input, etc.
    static {
//        builtInSymbols.put("print", new PrintFunction());
//        builtInSymbols.put("time", new Time());
//        builtInSymbols.put("vars", new Vars());
//        builtInSymbols.put("type", new Type());
//        builtInSymbols.put("cast", new Cast()); // i had a cool int() etc version but it conflicts with reserved keywords like int, float, etc.
//        builtInSymbols.put("getMember", new GetMember());
//        builtInSymbols.put("help", new help());
    }

    public static boolean isBuiltInFunction(String name) {
        return builtInSymbols.containsKey(name);
    }

    public static Set<String> getBuiltInFunctionNames() {
        return builtInSymbols.keySet();
    }

    public static Symbol getBuiltInFunction(String name) {
        return builtInSymbols.get(name);
    }

    public static void addBuiltInFunction(String name, Symbol function) {
        builtInSymbols.put(name, function);
    }

    public static void removeBuiltInFunction(String name) {
        builtInSymbols.remove(name);
    }
}
