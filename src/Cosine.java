public class Cosine extends Expression {
    private final Expression arg;

    public Cosine(Expression arg) {
        this.arg = arg;
    }

    @Override
    public Expression diff(String variable) {
        // cosine chain rule
        return new Product(new Constant(-1), new Product(new Sine(arg), arg.diff(variable)));
    }

    @Override
    public double eval(double... values) {
        return Math.cos(arg.eval(values));
    }
    @Override
    public String toString() {
        return null;
    }
}
