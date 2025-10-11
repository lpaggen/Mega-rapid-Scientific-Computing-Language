package AST.Nodes.Expressions.BinaryOperations.Linalg;

import AST.Nodes.DataStructures.Matrix;
import AST.Nodes.Expressions.Expression;
import AST.Nodes.Expressions.StringNode;
import Interpreter.Runtime.Environment;

public class LinalgMul extends LinalgBinaryNode {
    public LinalgMul(Expression lhs, Expression rhs) {
        super(lhs, rhs);
    }

    @Override
    public Expression evaluate(Environment env) {
        Expression leftVal = lhs.evaluate(env);
        Expression rightVal = rhs.evaluate(env);

        if (leftVal instanceof StringNode || rightVal instanceof StringNode) {
            throw new UnsupportedOperationException("Cannot perform multiplication on String types in linear algebra operations.");
        } else if (!(leftVal instanceof Matrix || rightVal instanceof Matrix)) {
            throw new UnsupportedOperationException("At least one operand must be an Array for linear algebra multiplication.");
        }

        return Matrix.mul(leftVal, rightVal);
    }

    @Override
    public String toString() {
        return lhs.toString() + " * " + rhs.toString();
    }
}
