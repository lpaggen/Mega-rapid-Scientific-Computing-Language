package AST.Nodes;

// handling negation through its own node instead of having new Prod(new Constant(-1), x)
public class Negate extends Expression {

    private final Expression arg;

    public Negate(Expression arg) {
        this.arg = arg;
    }


    @Override
    public Expression derive(String variable) {
        return new Product(new Numeric(-1), arg);
    }

    @Override
    public double eval(double... values) {
        return -1 * arg.evaluate(); // fix later
    }

    @Override
    public String toString() {
        return "-" + arg.toString();
    }
}
