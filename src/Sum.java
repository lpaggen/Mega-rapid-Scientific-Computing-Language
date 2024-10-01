public class Sum extends Expression {
    private final Expression left, right;

    public Sum(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Expression diff(String variable) {
        return new Sum(left.diff(variable), right.diff(variable));
    }

    @Override
    public double eval(double... values) {
        return left.eval(values) + right.eval(values);
    }

    @Override
    public String toString() {
        return STR."(\{left.toString()} + \{right.toString()})";
    }
}
