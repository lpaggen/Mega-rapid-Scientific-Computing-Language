package AST;

public class Quotient extends Expression {
    private final Expression left, right;

    public Quotient(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Expression diff(String variable) {
        // quotient rule
        return new Sum( // just handle the diff as a product of -1 and sum
                new Product(left.diff(variable), left),
                new Product(new Constant(-1), new Product(right.diff(variable), left))
        );
    }

    @Override
    public double eval(double... values) {
        return 0;
    }

    @Override
    public String toString() {
        // this is not correct, need to fix.
        return STR."(\{left.toString()} * \{right.toString()})" + " - " + STR."(\{left.toString()} * \{right.toString()})";
    }
}
