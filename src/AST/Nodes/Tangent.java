package AST.Nodes;

public class Tangent extends Expression {
    private final Expression arg;

    public Tangent(Expression arg) {
        this.arg = arg;
    }

    // MUST IMPLEMENT SEC, COSEC, *POWER* RULE !!!!!!!!!!
    @Override
    public Expression derive(String variable) {
        return new Multiply(new Sec(arg), arg.derive(variable));
    }

    @Override
    public double eval(double... values) {
        return arg.evaluate(values);
    }

    @Override
    public String toString() {
        return STR."tan(\{arg.toString()})";
    }
}
