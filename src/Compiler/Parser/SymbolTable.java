package Compiler.Parser;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    private final Map<String, Object> symbols = new HashMap<>();

    public void setVariable(String name, Object value) {
        symbols.put(name, value);
    }

    public Object getVariable(String name) {
        if (!symbols.containsKey(name)) {
            throw new RuntimeException("Undefined variable: " + name);
        }
        return symbols.get(name);
    }

    public boolean hasVariable(String name) {
        return symbols.containsKey(name);
    }
}
