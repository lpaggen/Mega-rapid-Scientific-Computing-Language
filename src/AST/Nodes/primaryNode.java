package AST.Nodes;

import Util.Environment;

public class primaryNode extends Expression {
    private final Object value;

    public primaryNode(Object value) {
        this.value = value;
    }

    @Override
    public Object evaluate(Environment env) {
        return value;
    }

    @Override
    public String toString() {
        return "";
    }
}
