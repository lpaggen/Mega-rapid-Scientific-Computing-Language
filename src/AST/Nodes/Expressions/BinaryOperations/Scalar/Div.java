package AST.Nodes.Expressions.BinaryOperations.Scalar;

import AST.Nodes.DataTypes.Constant;
import AST.Nodes.Expressions.Expression;
import AST.Nodes.Expressions.StringNode;
import Interpreter.Runtime.Environment;
import Interpreter.Tokenizer.TokenKind;

public class Div extends ArithmeticBinaryNode {

    public Div(Expression lhs, Expression rhs) {
        super(lhs, rhs);
    }

    @Override
    public Expression evaluate(Environment env) {
        Expression numVal = lhs.evaluate(env);
        Expression denomVal = rhs.evaluate(env);

        if (numVal instanceof Constant n && denomVal instanceof Constant d) {
            return Constant.divide(n, d);  // guaranteed numeric Constant
        }

        throw new RuntimeException(
                "Division operands must be numeric constants. Got: "
                        + numVal.getClass().getSimpleName() + " / "
                        + denomVal.getClass().getSimpleName()
        );
    }



    @Override
    public TokenKind getType(Environment env) {
        return super.getType(env);
    }

    public double evaluateNumeric(Environment env) {
        return lhs.evaluateNumeric(env) / rhs.evaluateNumeric(env);
    }

    @Override
    public String toString() {
        return lhs.toString() + " / " + rhs.toString();
    }

    public Expression getNum() {
        return lhs;
    }

    public Expression getDenom() {
        return rhs;
    }
}
