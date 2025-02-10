package Util;

import Compiler.Tokenizer.TokenKind;
import DataTypes.Computable;
import DataTypes.MatrixValue;
import DataTypes.NumericValue;
import DataTypes.Value;

import java.util.HashMap;
import java.util.Map;

// this class is a new data structure for my language, it's just a triple hash map
// this is necessary because we need to keep track of what is declared and what isn't, as well as their values and types
// also we use a custom "Value" interface for the V field of the table because we need support for diff Objects
// V extends value too, as stated previously we will need a Value field to handle all our different types and structures
// to be honest i could have also used another class for the value (value of map) and access the properties i need through it
public class LookupTable<K, V extends Value, T> {

    private final Map<K, Entry<V, T>> map = new HashMap<>();

    public V getValue(K key) {
        if (!map.containsKey(key)) {
            throw new IllegalArgumentException("Variable '" + key + "' not declared");
        }
        return map.get(key).value;
    }

    public T getType(K key) {
        if (!map.containsKey(key)) {
            throw new IllegalArgumentException("Variable '" + key + "' not declared");
        }
        return map.get(key).type;
    }

    public boolean isDeclared(K key) {
        return map.containsKey(key);
    }

    public void setValue(K key, V value) {
        if (!map.containsKey(key)) {
            throw new IllegalArgumentException("Variable '" + key + "' not declared.");
        }
        Entry<V, T> entry = map.get(key);
        entry.value = value; // update value
    }

    public void declareVariable(K key, T type) {
        if (map.containsKey(key)) {
            throw new IllegalArgumentException("Variable '" + key + "' already declared."); // not sure if we use this, counter-intuitive really
        }
        map.put(key, new Entry<>(null, type)); // can initialize with empty value, which is good
    }

    public void assignValueToLookupTable(K key, Object value, T type) {
        if (!isDeclared(key)) {
            declareVariable(key, type); // declare the variable in case it doesn't exist yet, it does not need a value and can be initialized blank
        }

        V inferredValue = inferValue(value, type);
        setValue(key, inferredValue);
    }

    // there is a problem currently when initializing a variable with no value -- current check works fine it seems
    private V inferValue(Object value, T type) { // some further checks need to be done, i don't know how safe this is
        if (value == null) { // this means we declare a variable with no value, which obviously works
            return null;
        } else if (type.equals(TokenKind.INTEGER)) {
            return (V) new NumericValue((Integer) value);
        } else if (type.equals(TokenKind.FLOAT)) {
            return (V) new NumericValue((Float) value);
        } else if (type.equals(TokenKind.MATRIX)) {
            return (V) new MatrixValue((Computable[][]) value);
        }
        throw new IllegalArgumentException("Could not infer value of '" + type + "', check for errors");
    }

    // this i don't understand, was chatGPT work, i don't specialize in formatting Java strings
    public void showLookupTable() {
        // Define column widths for alignment
        final int typeWidth = 10;
        final int keyWidth = 15;
        final int valueWidth = 30;

        // Print the header
        System.out.printf("%-" + typeWidth + "s%-" + keyWidth + "s%-" + valueWidth + "s%n", "TYPE", "KEY", "VALUE");
        System.out.println("-".repeat(typeWidth + keyWidth + valueWidth));

        // Print each entry
        for (Map.Entry<K, Entry<V, T>> entry : map.entrySet()) {
            K key = entry.getKey();
            T type = entry.getValue().type;
            V value = entry.getValue().value;

            // Format and print the row
            System.out.printf(
                    "%-" + typeWidth + "s%-" + keyWidth + "s%-" + valueWidth + "s%n",
                    type,
                    key,
                    value != null ? value.toString() : "null"
            );
        }
    }

    private static class Entry<V, T> {
        private V value;
        private T type;

        public Entry(V value, T type) {
            this.value = value;
            this.type = type;
        }
    }
}
