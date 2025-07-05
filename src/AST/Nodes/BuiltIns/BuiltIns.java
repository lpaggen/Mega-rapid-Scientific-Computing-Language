package AST.Nodes.BuiltIns;

import java.util.HashMap;

public class BuiltIns {
    private static final HashMap<String, BuiltInFunction> builtInFunctions = new HashMap<>();

    static { // this is the registry for built-in functions, we can just add whatever we want and use them as we normally would
        builtInFunctions.put("print", new PrintFunction());
    }

    public static BuiltInFunction getBuiltInFunction(String name) {
        return builtInFunctions.get(name);
    }

    public static boolean isBuiltInFunction(String name) {
        return builtInFunctions.containsKey(name);
    }
}
