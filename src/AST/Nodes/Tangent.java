package AST.Nodes;

public class Tangent extends Expression {
    private final Expression arg;

    public Tangent(Expression arg) {
        this.arg = arg;
    }

    // MUST IMPLEMENT SEC, COSEC, *POWER* RULE !!!!!!!!!!
    @Override
    public Expression diff(String variable) {
        return new Product(new Sec(arg), arg.diff(variable));
    }

    @Override
    public double eval(double... values) {
        return arg.eval(values);
    }

    @Override
    public String toString() {
        return STR."tan(\{arg.toString()})";
    }
}
