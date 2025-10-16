package AST.Nodes.Expressions.Mathematics;

import AST.Nodes.DataTypes.Scalar;
import AST.Nodes.Expressions.BinaryOperations.Arithmetic.ArithmeticBinaryNode;
import AST.Nodes.Expressions.BinaryOperations.Arithmetic.Div;
import AST.Nodes.Expressions.Expression;
import AST.Nodes.Expressions.UnaryNode;
import Interpreter.Runtime.Environment;
import Interpreter.Tokenizer.TokenKind;

public class Pow extends ArithmeticBinaryNode {

    public Pow(Expression lhs, Expression rhs) {
        super(lhs, rhs);
    }

    // this isn't everything we need, will adapt as we need it
    @Override
    public Expression evaluate(Environment env) {
        Expression baseExpr = lhs.evaluate(env);
        Expression expExpr = rhs.evaluate(env);
        if (baseExpr instanceof Scalar b && expExpr instanceof Scalar e) {
            double base = b.getDoubleValue();
            double exp = e.getDoubleValue();
            if (base < 0 && exp % 1 != 0) {
                throw new ArithmeticException(
                        "Cannot raise a negative number to a non-integer power: " + base + "^" + exp
                );
            }
            if (base == 0 && exp < 0) {
                throw new ArithmeticException("Cannot raise zero to a negative power: 0^" + exp);
            }
            if (base == 0) return new Scalar(0);
            if (base == 1) return new Scalar(1);
            if (exp == 0) return new Scalar(1);
            if (exp == 1) return new Scalar(base);
            if (base == -1.0) return new Scalar(exp % 2 == 0 ? 1 : -1);
            return new Scalar(Math.pow(base, exp));
        }
        if (expExpr instanceof UnaryNode u && u.getOperator().getKind() == TokenKind.MINUS) {
            // handle negative exponent symbolically
            return new Div(
                    new Scalar(1),
                    new Pow(baseExpr, u.getArg()) // remove the unary minus
            );
        }
        if (baseExpr instanceof Scalar b) return new Pow(b, expExpr);
        if (expExpr instanceof Scalar e && e.getDoubleValue() < 0) {
            return new Div(
                new Scalar(1),
                new Pow(baseExpr, new Scalar(-e.getDoubleValue()))
            );
        }
        if (expExpr instanceof Scalar e) return new Pow(baseExpr, e);
        return new Pow(baseExpr, expExpr);
    }

    public TokenKind getType(Environment env) {
        return super.getType(env);
    }

    public double evaluateNumeric(Environment env) {
        double base = lhs.evaluateNumeric(env);
        double exp = rhs.evaluateNumeric(env);
        return Math.pow(base, exp);
    }

    @Override
    public String toString() {
        return lhs.toString() + "^" + rhs.toString();
    }

    public Expression getBase() {
        return lhs;
    }

    public Expression getExponent() {
        return rhs;
    }
}
