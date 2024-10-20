package Expressions;

import java.util.Objects;

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
        // somehow could introduce a switch in here, unsure of how to do this exactly.
        // -> we need a way to have the "Expressions.Expression" class here ..?
        if (Objects.equals(left.toString(), "1.0")) {
            return STR."(\{right.toString()})";
        } else if (Objects.equals(right.toString(), "1.0")) {
            return STR."(\{left.toString()})";
        } else if (Objects.equals(right.toString(), "0.0") || Objects.equals(left.toString(), "0.0")) {
            return null; // or something else, null etc
        } else if (Objects.equals(right.toString(), "-1.0")) {
            return STR."-\{left.toString()}";
        } else if (Objects.equals(left.toString(), "-1.0")) {
            return STR."-\{right.toString()}";
        }
        else {
            // here we want to get rid of the "*" but we cannot do so yet
            // the issue is the - NEEDS to be at the front, and we also need to account for
            // multiple - signs -> +
            return STR."(\{left.toString()}*\{right.toString()})";
        }
    }
}
