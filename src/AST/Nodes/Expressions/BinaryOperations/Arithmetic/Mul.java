package AST.Nodes.Expressions.BinaryOperations.Arithmetic;

import AST.Nodes.DataStructures.Edge;
import AST.Nodes.DataStructures.Graph;
import AST.Nodes.DataStructures.Matrix;
import AST.Nodes.DataStructures.Node;
import AST.Nodes.DataTypes.Constant;
import AST.Nodes.Expressions.Expression;
import AST.Nodes.Expressions.StringNode;
import AST.Nodes.Expressions.VariableNode;
import Interpreter.Runtime.Environment;
import Interpreter.Tokenizer.TokenKind;
import Algebra.AlgebraEngine;

// NOTE: Assumes ArithmeticBinaryNode is defined in the AST.Nodes.Expressions.BinaryOperations.Arithmetic package
public class Mul extends ArithmeticBinaryNode {
    public Mul(Expression lhs, Expression rhs) {
        super(lhs, rhs);
    }

    @Override
    public Expression evaluate(Environment env) {
        Expression leftVal = lhs.evaluate(env);
        Expression rightVal = rhs.evaluate(env);

        // 1. Scalar Multiplication (Constant * Constant)
        if (leftVal instanceof Constant l && rightVal instanceof Constant r) {
            return Constant.multiply(l, r);
        }

        // 2. Algebraic Simplification (should not evaluate if a VariableNode is present)
        else if (leftVal instanceof VariableNode || rightVal instanceof VariableNode) {
            return AlgebraEngine.simplify(new Mul(leftVal, rightVal));
        }

        // 3. Matrix Operations (Matrix * Matrix, Matrix * Scalar, Scalar * Matrix)
        else if (leftVal instanceof Matrix || rightVal instanceof Matrix) {
            // NOTE: Matrix.multiply should handle all valid combinations (scalar/matrix, matrix/scalar, matrix/matrix)
            return Matrix.mul(leftVal, rightVal);
        }

        // --- Error Handling for Unsupported Types ---

        // Disallow String operations
        else if (leftVal instanceof StringNode || rightVal instanceof StringNode) {
            throw new UnsupportedOperationException("The multiplication operator (*) is not supported for string types.");
        }

        // Disallow Graph, Node, or Edge operations
        else if (leftVal instanceof Graph || rightVal instanceof Graph) {
            throw new UnsupportedOperationException("The multiplication operator (*) is not supported for graphs.");
        }
        else if (leftVal instanceof Node || rightVal instanceof Node) {
            throw new UnsupportedOperationException("The multiplication operator (*) is not supported for nodes.");
        }
        else if (leftVal instanceof Edge || rightVal instanceof Edge) {
            throw new UnsupportedOperationException("The multiplication operator (*) is not supported for edges.");
        }

        // Fallback
        return new Mul(leftVal, rightVal);
    }

    public double evaluateNumeric(Environment env) {
        return lhs.evaluateNumeric(env) * rhs.evaluateNumeric(env);
    }

    @Override
    public String toString() {
        return lhs.toString() + " * " + rhs.toString();
    }

    public TokenKind getType(Environment env) {
        return super.getType(env);
    }

}
