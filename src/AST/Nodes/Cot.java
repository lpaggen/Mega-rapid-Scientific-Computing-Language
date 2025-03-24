package AST.Nodes;

public class Cot extends Expression {

    private final Expression arg;

    public Cot(Expression arg) {
        this.arg = arg;
    }

    @Override
    public Expression derive(String variable) {
        return new Multiply(new Numeric(-1), new Power(new Cosec(arg), new Numeric(2)));
    }

    @Override
    public double eval(double... values) {
        return 0;
    }

    @Override
    public String toString() {
        return "wip";
    }
}
