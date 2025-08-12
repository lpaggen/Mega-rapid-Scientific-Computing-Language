package AST.Nodes;

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
    public Expression evaluate(Environment env) {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
