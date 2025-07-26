package Util;

import java.util.HashMap;
import java.util.Map;

public class EnvReWrite<V extends VariableSymbol> {
    private final Map<String, V> environment = new HashMap<>();

    public void declareVariable(String key, V value) {
        if (environment.containsKey(key)) {
            throw new IllegalArgumentException("Variable '" + key + "' already declared.");
        }
        environment.put(key, value);
    }

    public void setValue(String key, V value) {
        if (!environment.containsKey(key)) {
            throw new IllegalArgumentException("Variable '" + key + "' not declared.");
        }
        environment.put(key, value);
    }

    public V lookup(String key) {
        if (!environment.containsKey(key)) {
            throw new IllegalArgumentException("Variable '" + key + "' not declared.");
        }
        return environment.get(key);
    }

    public boolean isDeclared(String key) {
        return environment.containsKey(key);
    }

    public void showLookupTable() {
        final int typeWidth = 15;
        final int keyWidth = 20;
        final int valueWidth = 30;

        System.out.printf("%-" + typeWidth + "s%-" + keyWidth + "s%-" + valueWidth + "s%n", "TOKEN TYPE", "VARIABLE NAME", "LITERAL VALUE");
        System.out.println("-".repeat(typeWidth + keyWidth + valueWidth));

        for (Map.Entry<String, V> entry : environment.entrySet()) {
            String key = entry.getKey();
            V variableSymbol = entry.getValue();

            System.out.printf(
                    "%-" + typeWidth + "s%-" + keyWidth + "s%-" + valueWidth + "s%n",
                    variableSymbol.getType(),
                    key,
                    variableSymbol.getValue() != null ? variableSymbol.getValue().toString() : "null"
            );
        }
    }
}
