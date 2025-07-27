package AST.Nodes;

import Interpreter.Tokenizer.Token;
import Util.EnvReWrite;
import Util.Environment;

public class Literal extends Expression {
    private final Object value;

    public Literal(Object value) {
        this.value = value;
    }

    public Object evaluate() {
        return value;
    }

    @Override
    public Object evaluate(EnvReWrite env) {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
