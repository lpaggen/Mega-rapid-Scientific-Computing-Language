package AST.Nodes.BuiltIns;

import Util.FunctionSymbol;
import Util.Symbol;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

// this could also be contained in the parser itself, maybe this is cleaner however?
public class BuiltIns {
    public static final Set<BuiltInFunctionSymbol> builtInSymbols = Set.of(
            new PrintFunction()
            // add other built-ins here
    );

    // need a method to load all the built ins into the environment
    // maybe this is self contained in the parser
    public static void initializeBuiltIns(Map<String, BuiltInFunctionSymbol> environment) {
        for (BuiltInFunctionSymbol symbol : builtInSymbols) {
            environment.put(symbol.getName(), symbol);
        }
    }

    public static boolean isBuiltInFunction(String name) {
        return builtInFunctionsRegistry.contains(name);
    }

    public static Set<String> builtInFunctionsRegistry = Set.of(
            "print"
            // add other built-ins here
    );

    public static Map<String, Symbol> getModuleMap(String moduleName) {
        // here we can just define everything, obviously i will move this to a file later
        Map<String, Symbol> moduleMap = new HashMap<>();
        if (moduleName.equals("builtins")) { // the comment above means we should not have everything built here, rather just ready to load
            for (BuiltInFunctionSymbol symbol : builtInSymbols) {
                moduleMap.put("print", symbol);
            }
        }
        return moduleMap;
    }
}
