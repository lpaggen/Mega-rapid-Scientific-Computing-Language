package AST.Nodes.BuiltIns;

import AST.Nodes.FunctionNode;

import java.util.HashMap;
import java.util.function.Function;

// this could also be contained in the parser itself, maybe this is cleaner however?
public class BuiltIns {
    // we treat built ins as functions, so we can use them as we normally would, i just like to save them at execution
    private static final HashMap<String, FunctionNode> builtInFunctions = new HashMap<>();

    static { // this is the registry for built-in functions, we can just add whatever we want and use them as we normally would
        builtInFunctions.put("print", new PrintFunction());
    }

    public static FunctionNode getBuiltInFunction(String name) {
        return builtInFunctions.get(name);
    }

    public static boolean isBuiltInFunction(String name) {
        return builtInFunctions.containsKey(name);
    }
}
