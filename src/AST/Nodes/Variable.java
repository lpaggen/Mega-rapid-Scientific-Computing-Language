package AST.Nodes;

public class Variable extends Expression {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public Expression derive(String variable) {
        // wrt itself or not depends
        return name.equals(variable) ? new Numeric(1) : new Numeric(0);
    }

    @Override
    public double eval(double... values) {
        throw new UnsupportedOperationException("unsupported..");
    }

    @Override
    public String toString() {
        return name;
    }
}
