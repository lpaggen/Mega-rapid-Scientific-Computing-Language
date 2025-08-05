package AST.Nodes;

import Util.Environment;

public class PrimaryNode extends Expression {
    private final Object value;

    public PrimaryNode(Object value) {
        this.value = value;
    }

    @Override
    public Object evaluate(Environment env) {
        return value;
    }

    @Override
    public String toString() {
        return value.toString(); // can't believe i didn't implement toString before.
    }
}
