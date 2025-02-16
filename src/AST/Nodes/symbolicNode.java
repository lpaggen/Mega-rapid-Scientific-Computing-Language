package AST.Nodes;

public class symbolicNode extends Expression {
    private final String name;

    public symbolicNode(String name) {
        this.name = name;
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
        return this;
    }
}
