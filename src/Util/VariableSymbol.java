package Util;

import Interpreter.Tokenizer.TokenKind;

// this will eventually replace the Token in the Environment, as it also will allow for functions to be stored in the env
// also, functions do have names, and return types, so this should work. they don't however have values, so no constructor
// --> VariableSymbol is a more general class that can be used for both variables and functions !!
public class VariableSymbol extends Symbol {
    private Object value; // this is the value of the variable, can be null if not set

    public VariableSymbol(String name, TokenKind type, Object value) {
        super(name, type);
        this.value = value; // we would be using VariableSymbol for function parameters, no need for their own class
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
