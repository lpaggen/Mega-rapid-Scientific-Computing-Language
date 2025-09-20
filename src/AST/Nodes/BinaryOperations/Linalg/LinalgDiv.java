package AST.Nodes.BinaryOperations.Linalg;

import AST.Nodes.DataStructures.Vector;
import AST.Nodes.DataStructures.Vector;
import AST.Nodes.Expression;
import Interpreter.Runtime.Environment;

public class LinalgDiv extends LinalgBinaryNode {
    public LinalgDiv(Expression lhs, Expression rhs) {
        super(lhs, rhs);
    }

    @Override
    public Expression evaluate(Environment env) {
        Expression leftVal = lhs.evaluate(env);
        Expression rightVal = rhs.evaluate(env);

        if (leftVal instanceof Vector l && rightVal instanceof Vector r) {
            return Vector.div(l, r);
        }
        return new LinalgDiv(leftVal, rightVal);
    }

    @Override
    public String toString() {
        return lhs.toString() + " / " + rhs.toString();
    }
}
