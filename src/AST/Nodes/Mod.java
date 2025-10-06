package AST.Nodes;

import AST.Nodes.BinaryOperations.Scalar.ArithmeticBinaryNode;
import AST.Nodes.DataTypes.Constant;
import AST.Nodes.DataTypes.IntegerConstant;
import Interpreter.Runtime.Environment;

public class Mod extends ArithmeticBinaryNode {
    public Mod(Expression lhs, Expression rhs) {
        super(lhs, rhs);
    }

    public double evaluateNumeric(Environment env) {
        double leftVal = lhs.evaluateNumeric(env);
        double rightVal = rhs.evaluateNumeric(env);
        return leftVal % rightVal;
    }

    @Override
    public Expression evaluate(Environment env) {
        Expression leftVal = lhs.evaluate(env);
        Expression rightVal = rhs.evaluate(env);
        if (leftVal instanceof Constant l && rightVal instanceof Constant r) {
            int result = (int) l.getDoubleValue() % (int) r.getDoubleValue();
            return new IntegerConstant(result);
        }
        return new Mod(leftVal, rightVal);
    }
}
