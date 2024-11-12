package AST.Nodes;

// handling negation through its own node instead of having new Prod(new Constant(-1), x)
public class Negate extends Expression {

    private final Expression arg;

    public Negate(Expression arg) {
        this.arg = arg;
    }


    @Override
    public Expression diff(String variable) {
        return new Product(new Constant(-1), arg);
    }

    @Override
    public double eval(double... values) {
        return -1 * arg.eval(); // fix later
    }

    @Override
    public String toString() {
        return "-" + arg.toString();
    }
}
