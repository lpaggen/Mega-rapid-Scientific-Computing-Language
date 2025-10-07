package AST.Nodes.BinaryOperations.Scalar;

import AST.Nodes.DataTypes.Constant;
import AST.Nodes.Expression;
import AST.Nodes.StringNode;
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

        // Handle numeric operations
        if (numVal instanceof Constant n && denomVal instanceof Constant d) {
            return Constant.divide(n, d);
        } else if (numVal instanceof StringNode || denomVal instanceof StringNode) {
            throw new RuntimeException("Cannot divide strings.");
        }
        // If we reach here, it's an unsupported type combination
        return new Div(numVal, denomVal);
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
