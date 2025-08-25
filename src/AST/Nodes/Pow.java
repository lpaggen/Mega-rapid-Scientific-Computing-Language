package AST.Nodes;

import Interpreter.Runtime.Environment;
import Interpreter.Tokenizer.TokenKind;

public class Pow extends Expression {

    private final Expression arg, exponent;

    public Pow(Expression arg, Expression exponent) {
        this.arg = arg;
        this.exponent = exponent;
    }

    // this isn't everything we need, will adapt as we need it
    @Override
    public Expression evaluate(Environment env) {
        Expression baseExpr = arg.evaluate(env);
        Expression expExpr = exponent.evaluate(env);
        if (baseExpr instanceof Constant b && expExpr instanceof Constant e) {
            double base = b.getDoubleValue();
            double exp = e.getDoubleValue();
            if (base < 0 && exp % 1 != 0) {
                throw new ArithmeticException(
                        "Cannot raise a negative number to a non-integer power: " + base + "^" + exp
                );
            }
            if (base == 0 && exp < 0) {
                throw new ArithmeticException("Cannot raise zero to a negative power: 0^" + exp);
            } // not really worth making a switch here, could but no perf gains
            if (base == 0) return new Constant(0.0);
            if (base == 1) return new Constant(1.0);
            if (exp == 0) return new Constant(1.0);
            if (exp == 1) return new Constant(base);
            if (base == -1.0) return new Constant(exp % 2 == 0 ? 1.0 : -1.0);
            return new Constant(Math.pow(base, exp));
        }
        if (expExpr instanceof UnaryNode u && u.getOperator().getKind() == TokenKind.MINUS) {
            // handle negative exponent symbolically
            return new Div(
                    new Constant(1.0),
                    new Pow(baseExpr, u.getArg()) // remove the unary minus
            );
        }
        if (baseExpr instanceof Constant b) return new Pow(b, expExpr);
        if (expExpr instanceof Constant e && e.getDoubleValue() < 0) {
            return new Div(
                new Constant(1.0),
                new Pow(baseExpr, new Constant(-e.getDoubleValue()))
            );
        }
        if (expExpr instanceof Constant e) return new Pow(baseExpr, e);
        return new Pow(baseExpr, expExpr);
    }

    public double evaluateNumeric(Environment env) {
        double base = arg.evaluateNumeric(env);
        double exp = exponent.evaluateNumeric(env);
        return Math.pow(base, exp);
    }

    @Override
    public String toString() {
        return arg.toString() + "^" + exponent.toString();
    }

    public Expression getBase() {
        return arg;
    }

    public Expression getExponent() {
        return exponent;
    }
}
