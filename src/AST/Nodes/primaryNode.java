package AST.Nodes;

// here we might run into an issue, true or false statements obviously can't be derived, will make a fix later
public class primaryNode extends Expression {
    private final Object value;

    public primaryNode(Object value) {
        this.value = value;
    }

    @Override
    public Expression derive(String variable) {
        return null;
    }

    @Override
    public double evaluate() {
        return 0;
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public Expression simplify() {
        return null;
    }
}
