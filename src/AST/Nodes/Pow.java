package AST.Nodes;

import AST.Nodes.BinaryOperations.Scalar.ArithmeticBinaryNode;
import AST.Nodes.BinaryOperations.Scalar.Div;
import AST.Nodes.DataTypes.Constant;
import AST.Nodes.DataTypes.FloatConstant;
import AST.Nodes.DataTypes.IntegerConstant;
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
        boolean raw = (baseExpr instanceof Constant b && b.isRaw())
                || (expExpr instanceof Constant e && e.isRaw());
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
            }
            if (base == 0) return new IntegerConstant(0, raw);
            if (base == 1) return new IntegerConstant(1, raw);
            if (exp == 0) return new IntegerConstant(1, raw);
            if (exp == 1) return new FloatConstant(base, raw);
            if (base == -1.0) return new IntegerConstant(exp % 2 == 0 ? 1 : -1, raw);
            return new FloatConstant(Math.pow(base, exp), raw);
        }
        if (expExpr instanceof UnaryNode u && u.getOperator().getKind() == TokenKind.MINUS) {
            // handle negative exponent symbolically
            return new Div(
                    new IntegerConstant(1, raw),
                    new Pow(baseExpr, u.getArg()) // remove the unary minus
            );
        }
        if (baseExpr instanceof Constant b) return new Pow(b, expExpr);
        if (expExpr instanceof Constant e && e.getDoubleValue() < 0) {
            return new Div(
                new IntegerConstant(1, raw),
                new Pow(baseExpr, new FloatConstant(-e.getDoubleValue(), raw))
            );
        }
        if (expExpr instanceof Constant e) return new Pow(baseExpr, e);
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
