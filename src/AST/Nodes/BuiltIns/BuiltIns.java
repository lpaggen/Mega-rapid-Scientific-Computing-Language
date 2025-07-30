package AST.Nodes.BuiltIns;

import AST.Nodes.FunctionNode;
import Interpreter.Tokenizer.TokenKind;

import java.util.HashMap;
import java.util.function.Function;

// this could also be contained in the parser itself, maybe this is cleaner however?
public class BuiltIns {
    // we treat built ins as functions, so we can use them as we normally would, i just like to save them at execution
    private static final HashMap<String, FunctionNode> builtInFunctions = new HashMap<>();

    public static FunctionNode getBuiltInFunction(String name) {
        return builtInFunctions.get(name);
    }

    public static boolean isBuiltInFunction(String name) {
        return builtInFunctions.containsKey(name);
    }

    public static void registerBuiltInFunction(String name, FunctionNode function) {
        if (builtInFunctions.containsKey(name)) {
            throw new IllegalArgumentException("Built-in function '" + name + "' is already registered.");
        }
        builtInFunctions.put(name, function);
    }

    public static void initializeBuiltIns() {
        // Register built-in functions here
        registerBuiltInFunction("print", new PrintFunction());
        // Add more built-in functions as needed
    }

    public static void clearBuiltIns() {
        builtInFunctions.clear();
    }

    public static HashMap<String, FunctionNode> getBuiltInFunctions() {
        return builtInFunctions;
    }

    public static void printBuiltIns() {
        System.out.println("Registered Built-in Functions:");
        for (String name : builtInFunctions.keySet()) {
            System.out.println("- " + name);
        }
    }
}
