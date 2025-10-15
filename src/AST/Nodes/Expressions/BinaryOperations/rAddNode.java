package AST.Nodes.Expressions.BinaryOperations;

import AST.Nodes.DataStructures.Graph;
import AST.Nodes.DataStructures.Matrix;
import AST.Nodes.DataTypes.Constant;
import AST.Nodes.Expressions.BinaryOperations.Scalar.Add;
import AST.Nodes.Expressions.Expression;
import AST.Nodes.Expressions.StringNode;
import AST.Nodes.Expressions.VariableNode;
import Algebra.AlgebraEngine;
import Interpreter.Runtime.Environment;
import Interpreter.Tokenizer.TokenKind;

public class rAddNode extends BinaryNode {
    public rAddNode(Expression lhs, Expression rhs) {
        super(lhs, rhs);
    }

    @Override
    public Expression evaluate(Environment env) {
        Expression leftVal = lhs.evaluate(env);
        Expression rightVal = rhs.evaluate(env);

        if (leftVal instanceof Constant l && rightVal instanceof Constant r) {
            return Constant.add(l, r);
        } else if (leftVal instanceof StringNode l && rightVal instanceof StringNode r) {
            return new StringNode(l.getValue() + r.getValue());
        } else if (leftVal instanceof StringNode l && rightVal instanceof Constant r) {
            return new StringNode(l.getValue() + r.getValue().toString());
        } else if (leftVal instanceof Constant l && rightVal instanceof StringNode r) {
            return new StringNode(l.getValue().toString() + r.getValue());
        } else if (leftVal instanceof VariableNode || rightVal instanceof VariableNode) {
            return AlgebraEngine.simplify(new Add(leftVal, rightVal));
        } else if (leftVal instanceof Matrix || rightVal instanceof Matrix) {
            return Matrix.add(leftVal, rightVal);
        } else if (leftVal instanceof Graph g && rightVal instanceof Graph h) {
            return Graph.add(g, h);
        }
        return new Add(leftVal, rightVal);
    }

    public double evaluateNumeric(Environment env) {
        return lhs.evaluateNumeric(env) + rhs.evaluateNumeric(env);
    }

    @Override
    public String toString() {
        return lhs.toString() + " + " + rhs.toString();
    }

    public TokenKind getType(Environment env) {
        return super.getType(env);
    }
}
