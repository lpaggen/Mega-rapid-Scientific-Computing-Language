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
        if (map.containsKey(key)) {
            throw new IllegalArgumentException("Variable '" + key + "' already declared.");
        }
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

    public void showLookupTable(Map<String, Token> symbolTable) {
        // Define column widths for alignment
        final int typeWidth = 15;
        final int keyWidth = 20;
        final int valueWidth = 30;

        // Print the header
        System.out.printf("%-" + typeWidth + "s%-" + keyWidth + "s%-" + valueWidth + "s%n", "TOKEN TYPE", "VARIABLE NAME", "LITERAL VALUE");
        System.out.println("-".repeat(typeWidth + keyWidth + valueWidth));

        // Print each entry from the symbol table
        for (Map.Entry<String, Token> entry : symbolTable.entrySet()) {
            String key = entry.getKey();
            Token token = entry.getValue();

            // Format and print the row
            System.out.printf(
                    "%-" + typeWidth + "s%-" + keyWidth + "s%-" + valueWidth + "s%n",
                    token.getKind(),               // TokenKind (e.g., INTEGER, FLOAT)
                    key,                      // Variable name (declared at runtime)
                    token.getLiteral() != null ? token.getLiteral().toString() : "null"  // Literal value
            );
        }
    }
}
