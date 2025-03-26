package AST.Nodes;

import Interpreter.Tokenizer.Token;
import Util.LookupTable;

public class BooleanNode extends Expression {
    private final boolean value;

    public BooleanNode(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public Object evaluate(LookupTable<String, Token> env) {
        return this;
    }

    @Override
    public String toString() {
        return value ? "true" : "false";
    }

    public Constant toNumeric() {
        return new Constant(value ? 1.0 : 0.0);
    }
}
