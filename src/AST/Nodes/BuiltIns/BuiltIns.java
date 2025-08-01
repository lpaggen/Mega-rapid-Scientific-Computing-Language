package AST.Nodes.BuiltIns;

import AST.Nodes.FunctionNode;
import Interpreter.Tokenizer.TokenKind;
import Util.Environment;
import Util.FunctionSymbol;
import Util.Symbol;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

// this could also be contained in the parser itself, maybe this is cleaner however?
public class BuiltIns {
    public static final Map<String, FunctionNode> builtInSymbols = new HashMap<>();

    static {
        builtInSymbols.put("print", new PrintFunction(new Environment()));
        // add other built-ins here
    }

    public static void registerAllInto(Environment env) {
        for (Map.Entry<String, FunctionNode> entry : builtInSymbols.entrySet()) {
            env.declareSymbol(entry.getKey(), new FunctionSymbol(entry.getValue()));
        }
    }

    public static boolean isBuiltInFunction(String name) {
        return builtInSymbols.containsKey(name);
    }

    public static FunctionNode getBuiltInFunction(String name) {
        return builtInSymbols.get(name);
    }
}
