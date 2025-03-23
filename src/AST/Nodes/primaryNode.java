package AST.Nodes;

public class primaryNode extends Expression {
    private final Object value;

    public primaryNode(Object value) {
        this.value = value;
    }

    @Override
    public Object evaluate() {
        return 0;
    }

    @Override
    public String toString() {
        return "";
    }
}
