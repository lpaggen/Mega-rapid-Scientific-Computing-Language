package AST.Nodes;

import Interpreter.Tokenizer.Token;
import Util.EnvReWrite;
import Util.Environment;

public class primaryNode extends Expression {
    private final Object value;

    public primaryNode(Object value) {
        this.value = value;
    }

    @Override
    public Object evaluate(EnvReWrite env) {
        return value;
    }

    @Override
    public String toString() {
        return "";
    }
}
