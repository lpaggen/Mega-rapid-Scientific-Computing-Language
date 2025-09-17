package AST.Nodes.BinaryOperations.Linalg;

import AST.Nodes.BinaryOperations.Scalar.Add;
import AST.Nodes.Constant;
import AST.Nodes.DataStructures.ArrayNode;
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
        } else if (!(leftVal instanceof ArrayNode || rightVal instanceof ArrayNode)) {
            throw new UnsupportedOperationException("At least one operand must be an ArrayNode for linear algebra addition.");
        }

//        if (leftVal instanceof ArrayNode l && rightVal instanceof ArrayNode r) {
//            return ArrayNode.add(l, r);
//        } else if (leftVal instanceof ArrayNode l && rightVal instanceof Constant r) {
//            return ArrayNode.add(l, r);
//        } else if (leftVal instanceof Constant l && rightVal instanceof ArrayNode r) {
//            return ArrayNode.add(r, l);
//        }
//        return new LinalgAdd(leftVal, rightVal);
        return ArrayNode.add(leftVal, rightVal);
    }

    @Override
    public String toString() {
        return lhs.toString() + " + " + rhs.toString();
    }
}
