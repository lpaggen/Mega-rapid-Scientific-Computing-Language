package AST.Nodes.BinaryOperations.Linalg;

import AST.Nodes.DataStructures.Matrix;
import AST.Nodes.Expression;
import Interpreter.Runtime.Environment;

public class LinalgSub extends LinalgBinaryNode {
    public LinalgSub(Expression lhs, Expression rhs) {
        super(lhs, rhs);
    }

    @Override
    public Expression evaluate(Environment env) {
        Expression leftVal = lhs.evaluate(env);
        Expression rightVal = rhs.evaluate(env);

        if (leftVal instanceof Matrix l && rightVal instanceof Matrix r) {
            return Matrix.sub(l, r);
        }
        return new LinalgSub(leftVal, rightVal);
    }

    @Override
    public String toString() {
        return lhs.toString() + " - " + rhs.toString();
    }
}
