package AST.Nodes.BinaryOperations.Linalg;

import AST.Nodes.DataStructures.Array;
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

        if (leftVal instanceof Array l && rightVal instanceof Array r) {
            System.out.println("Adding two arrays");
            return Array.div(l, r);
        }
        return new LinalgDiv(leftVal, rightVal);
    }

    @Override
    public String toString() {
        return lhs.toString() + " / " + rhs.toString();
    }
}
