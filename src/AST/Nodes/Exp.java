package AST.Nodes;

public class Exp extends Expression {
    private final Expression arg;

    public Exp(Expression arg) {
        this.arg = arg;
    }

    @Override
    public Expression derive(String variable) {
        // chain rule of e, should be correct
        return new Multiply(arg.derive(variable), new Exp(arg));
    }

    @Override
    public double eval(double... values) {
        return Math.exp(arg.evaluate(values));
    }

    @Override
    public String toString() {
        return STR."e**\{arg.toString()}";
    }
}
