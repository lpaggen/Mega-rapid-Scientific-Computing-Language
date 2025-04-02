package AST.Nodes;

import Interpreter.Tokenizer.Token;
import Util.LookupTable;

public class primaryNode extends Expression {
    private final Object value;

    public primaryNode(Object value) {
        this.value = value;
    }

    @Override
    public Object evaluate(LookupTable<String, Token> env) {
        return value;
    }

    @Override
    public String toString() {
        return "";
    }
}
