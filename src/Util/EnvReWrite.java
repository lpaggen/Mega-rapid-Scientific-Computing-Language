package Util;

import java.util.HashMap;
import java.util.Map;

public class EnvReWrite <K extends VariableSymbol> {
    private final Map<K, VariableSymbol> environment = new HashMap<>();

    public void declareVariable(K key, VariableSymbol value) {
        if (environment.containsKey(key)) {
            throw new IllegalArgumentException("Variable '" + key.getName() + "' already declared.");
        }
        environment.put(key, value);
    }

    public void setValue(K key, VariableSymbol value) {
        if (!environment.containsKey(key)) {
            throw new IllegalArgumentException("Variable '" + key.getName() + "' not declared.");
        }
        environment.put(key, value);
    }

    public VariableSymbol lookup(K key) {
        if (!environment.containsKey(key)) {
            throw new IllegalArgumentException("Variable '" + key.getName() + "' not declared.");
        }
        return environment.get(key);
    }

    public boolean isDeclared(K key) {
        return environment.containsKey(key);
    }

    public void showLookupTable() {
        final int typeWidth = 15;
        final int keyWidth = 20;
        final int valueWidth = 30;

        System.out.printf("%-" + typeWidth + "s%-" + keyWidth + "s%-" + valueWidth + "s%n", "TOKEN TYPE", "VARIABLE NAME", "LITERAL VALUE");
        System.out.println("-".repeat(typeWidth + keyWidth + valueWidth));

        for (Map.Entry<K, VariableSymbol> entry : environment.entrySet()) {
            K key = entry.getKey();
            VariableSymbol token = entry.getValue();

            System.out.printf(
                    "%-" + typeWidth + "s%-" + keyWidth + "s%-" + valueWidth + "s%n",
                    token.getType(),
                    key.getName(),
                    token.getValue() != null ? token.getValue().toString() : "null"
            );
        }
    }
}
