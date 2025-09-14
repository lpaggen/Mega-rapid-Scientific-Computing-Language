package AST.Nodes.BinaryOperations.Linalg;

import AST.Nodes.DataStructures.ArrayNode;
import AST.Nodes.Expression;
import Interpreter.Runtime.Environment;

public class LinalgMul extends LinalgBinaryNode {
    public LinalgMul(Expression lhs, Expression rhs) {
        super(lhs, rhs);
    }

    @Override
    public Expression evaluate(Environment env) {
        Expression leftVal = lhs.evaluate(env);
        Expression rightVal = rhs.evaluate(env);

        if (leftVal instanceof ArrayNode l && rightVal instanceof ArrayNode r) {
            System.out.println("Multiplying two arrays");
            return ArrayNode.mul(l, r);
        }
        return new LinalgAdd(leftVal, rightVal);
    }

    @Override
    public String toString() {
        return lhs.toString() + " * " + rhs.toString();
    }
}
