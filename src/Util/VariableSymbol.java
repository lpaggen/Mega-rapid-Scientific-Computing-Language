package Util;

import Interpreter.Tokenizer.TokenKind;

// this will eventually replace the Token in the Environment, as it also will allow for functions to be stored in the env
// also, functions do have names, and return types, so this should work. they don't however have values, so no constructor
public class VariableSymbol {
    private final String name;
    private final TokenKind type; // this should be an enum, but for now a string will do
    private Object value; // this should be a more complex type, but for now an object will do
    private final int line;

    public VariableSymbol(String name, TokenKind type, Object value, int line) {
        this.name = name;
        this.type = type;
        this.line = line;
    }

    public String getName() {
        return name;
    }

    public TokenKind getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public int getLine() {
        return line;
    }
}
