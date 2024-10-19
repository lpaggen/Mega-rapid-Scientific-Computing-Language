public class Exp extends Expression{
    private final Expression arg;

    public Exp(Expression arg) {
        this.arg = arg;
    }

    @Override
    public Expression diff(String variable) {
        // chain rule of e, should be correct
        return new Product(arg.diff(variable), new Exp(arg));
    }

    @Override
    public double eval(double... values) {
        return Math.exp(arg.eval(values));
    }

    @Override
    public String toString() {
        return STR."e**\{arg.toString()}";
    }
}
