package AST.Nodes.BinaryOperations.Linalg;

import AST.Nodes.DataStructures.Matrix;
import AST.Nodes.Expression;
import AST.Nodes.StringNode;
import Interpreter.Runtime.Environment;

public class LinalgSub extends LinalgBinaryNode {
    public LinalgSub(Expression lhs, Expression rhs) {
        super(lhs, rhs);
    }

    @Override
    public Expression evaluate(Environment env) {
        Expression leftVal = lhs.evaluate(env);
        Expression rightVal = rhs.evaluate(env);

        if (leftVal instanceof StringNode || rightVal instanceof StringNode) {
            throw new UnsupportedOperationException("Cannot perform subtraction on String types in linear algebra operations.");
        } else if (!(leftVal instanceof Matrix || rightVal instanceof Matrix)) {
            throw new UnsupportedOperationException("At least one operand must be an Array for linear algebra subtraction.");
        }

        return Matrix.sub(leftVal, rightVal);
    }

    @Override
    public String toString() {
        return lhs.toString() + " - " + rhs.toString();
    }
}
