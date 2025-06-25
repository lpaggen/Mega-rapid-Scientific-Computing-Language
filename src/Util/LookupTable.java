package Util;

import Interpreter.Tokenizer.Token;
import Interpreter.Tokenizer.TokenKind;

import java.util.HashMap;
import java.util.Map;

public class LookupTable<K, V extends Token> {
    private final Map<K, V> map = new HashMap<>();

    public TokenKind getType(K key) {
        if (!map.containsKey(key)) {
            throw new IllegalArgumentException("Variable '" + key + "' not declared");
        }
        return map.get(key).getKind();
    }

    public String getLexeme(K key) {
        if (!map.containsKey(key)) {
            throw new IllegalArgumentException("Variable '" + key + "' not declared");
        }
        return map.get(key).getLexeme();
    }

    public Object getLiteral(K key) {
        if (!map.containsKey(key)) {
            throw new IllegalArgumentException("Variable '" + key + "' not declared");
        }
        return map.get(key).getLiteral();
    }

    // this method is used to declare a variable in the lookup table
    public void declareVariable(K key, V value) {
        map.put(key, value);
    }

    // this method is used to change the value of a variable in the lookup table
    public void setValue(K key, V value) {
        if (!map.containsKey(key)) {
            throw new IllegalArgumentException("Variable '" + key + "' not declared.");
        }
        map.put(key, value);
    }

    public boolean isDeclared(K key) {
        return map.containsKey(key);
    }

    // this method simply prints the lookup table to the console, it is used for debugging
    public void showLookupTable() {
        final int typeWidth = 15;
        final int keyWidth = 20;
        final int valueWidth = 30;

        System.out.printf("%-" + typeWidth + "s%-" + keyWidth + "s%-" + valueWidth + "s%n", "TOKEN TYPE", "VARIABLE NAME", "LITERAL VALUE");
        System.out.println("-".repeat(typeWidth + keyWidth + valueWidth));

        for (Map.Entry<K, V> entry : map.entrySet()) {
            K key = entry.getKey();
            V token = entry.getValue();

            System.out.printf(
                    "%-" + typeWidth + "s%-" + keyWidth + "s%-" + valueWidth + "s%n",
                    token.getKind(),
                    key.toString(),
                    token.getLiteral() != null ? token.getLiteral().toString() : "null"
            );
        }
    }
}
