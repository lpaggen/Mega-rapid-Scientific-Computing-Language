public class Product extends Expression {
    private final Expression left, right;

    public Product(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Expression diff(String variable) {
        // follows the basic rules of diff, power rule
        return new Sum(
                new Product(left.diff(variable), right),
                new Product(left, right.diff(variable))
        );
    }

    @Override
    public double eval(double... values) {
        return left.eval(values) * right.eval(values);
    }

    @Override
    public String toString() {
        return STR."(\{left.toString()} * \{right.toString()})";
    }
}
