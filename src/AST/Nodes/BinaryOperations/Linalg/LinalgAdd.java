package AST.Nodes.BinaryOperations.Linalg;

import AST.Nodes.DataStructures.Array;
import AST.Nodes.DataStructures.Vector;
import AST.Nodes.Expression;
import AST.Nodes.StringNode;
import Interpreter.Runtime.Environment;

public class LinalgAdd extends LinalgBinaryNode {
    public LinalgAdd(Expression lhs, Expression rhs) {
        super(lhs, rhs);
    }

    @Override
    public Expression evaluate(Environment env) {
        Expression leftVal = lhs.evaluate(env);
        Expression rightVal = rhs.evaluate(env);

        if (leftVal instanceof StringNode || rightVal instanceof StringNode) {
            throw new UnsupportedOperationException("Cannot perform addition on String types in linear algebra operations.");
        } else if (!(leftVal instanceof Vector || rightVal instanceof Vector)) {
            throw new UnsupportedOperationException("At least one operand must be an Array for linear algebra addition.");
        }

        return Vector.add(leftVal, rightVal);
    }

    @Override
    public String toString() {
        return lhs.toString() + " + " + rhs.toString();
    }
}
