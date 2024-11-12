package AST.Nodes;

public class Cot extends Expression {

    private final Expression arg;

    public Cot(Expression arg) {
        this.arg = arg;
    }

    @Override
    public Expression diff(String variable) {
        return new Product(new Constant(-1), new Power(new Cosec(arg), new Constant(2)));
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
