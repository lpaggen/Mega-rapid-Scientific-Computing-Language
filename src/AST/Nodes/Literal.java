package AST.Nodes;

import Interpreter.Tokenizer.Token;
import Util.LookupTable;

public class Literal extends Expression {
    private final Object value;

    public Literal(Object value) {
        this.value = value;
    }

    public Object evaluate() {
        return value;
    }

    @Override
    public Object evaluate(LookupTable<String, Token> env) {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
