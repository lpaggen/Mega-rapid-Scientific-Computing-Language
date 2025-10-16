package AST.Nodes.Expressions.BinaryOperations.Arithmetic;

import AST.Nodes.DataStructures.Edge;
import AST.Nodes.DataStructures.Graph;
import AST.Nodes.DataStructures.Matrix;
import AST.Nodes.DataStructures.Node;
import AST.Nodes.DataTypes.Scalar;
import AST.Nodes.Expressions.Expression;
import AST.Nodes.Expressions.StringNode;
import AST.Nodes.Expressions.VariableNode;
import Interpreter.Runtime.Environment;
import Interpreter.Tokenizer.TokenKind;
import Algebra.AlgebraEngine;

public class Add extends ArithmeticBinaryNode {
    public Add(Expression lhs, Expression rhs) {
        super(lhs, rhs);
    }

    // this must eventually handle ALL combinations of types, and throw the relevant errors etc.
    // but it needs to be cleaned, there's probably duplicates
    @Override
    public Expression evaluate(Environment env) {
        Expression leftVal = lhs.evaluate(env);
        Expression rightVal = rhs.evaluate(env);

        if (leftVal instanceof Scalar l && rightVal instanceof Scalar r) {
            return Scalar.add(l, r);
        } else if (leftVal instanceof StringNode l && rightVal instanceof StringNode r) {
            return new StringNode(l.getValue() + r.getValue());
        } else if (leftVal instanceof StringNode l && rightVal instanceof Scalar r) {
            return new StringNode(l.getValue() + r.getValue().toString());
        } else if (leftVal instanceof Scalar l && rightVal instanceof StringNode r) {
            return new StringNode(l.getValue().toString() + r.getValue());
        } else if (leftVal instanceof VariableNode || rightVal instanceof VariableNode) {  // this should not be taking VariableNode
            return AlgebraEngine.simplify(new Add(leftVal, rightVal));
        } else if (leftVal instanceof Matrix || rightVal instanceof Matrix) {
            return Matrix.add(leftVal, rightVal);
        } else if (leftVal instanceof Graph g && rightVal instanceof Graph h) {
            return Graph.add(g, h);
        } else if (leftVal instanceof Graph && rightVal instanceof StringNode) {
            throw new UnsupportedOperationException("Cannot add a string to a graph.");
        } else if (leftVal instanceof StringNode && rightVal instanceof Graph) {
            throw new UnsupportedOperationException("Cannot add a string to a graph.");
        } else if (leftVal instanceof Graph l && rightVal instanceof Edge r) {
            return Graph.add(l, r);
        } else if (leftVal instanceof Edge l && rightVal instanceof Graph r) {
            return Graph.add(r, l);
        } else if (leftVal instanceof Graph g && rightVal instanceof Node n) {
            return Graph.add(g, n);
        } else if (leftVal instanceof Node n && rightVal instanceof Graph g) {
            return Graph.add(g, n);
        } else if (leftVal instanceof Graph g && rightVal instanceof Matrix) {
            throw new UnsupportedOperationException("Cannot add a matrix to a graph.");
        } else if (leftVal instanceof Matrix && rightVal instanceof Graph h) {
            throw new UnsupportedOperationException("Cannot add a matrix to a graph.");
        } else if (leftVal instanceof Edge && rightVal instanceof Scalar) {
            throw new UnsupportedOperationException("Cannot add a scalar to an edge.");
        } else if (leftVal instanceof Scalar && rightVal instanceof Edge) {
            throw new UnsupportedOperationException("Cannot add a scalar to an edge.");
        } else if (leftVal instanceof Edge && rightVal instanceof StringNode) {
            throw new UnsupportedOperationException("Cannot add a string to an edge.");
        } else if (leftVal instanceof StringNode && rightVal instanceof Edge) {
            throw new UnsupportedOperationException("Cannot add a string to an edge.");
        } else if (leftVal instanceof Edge l && rightVal instanceof Node r) {
            throw new UnsupportedOperationException("Cannot add a node to an edge. Consider creating a graph with the node and edge instead.");
        } else if (leftVal instanceof Node l && rightVal instanceof Edge r) {
            throw new UnsupportedOperationException("Cannot add a node to an edge. Consider creating a graph with the node and edge instead.");
        } else if (leftVal instanceof Edge && rightVal instanceof Matrix) {
            throw new UnsupportedOperationException("Cannot add a matrix to an edge.");
        } else if (leftVal instanceof Matrix && rightVal instanceof Edge) {
            throw new UnsupportedOperationException("Cannot add a matrix to an edge.");
        } else if (leftVal instanceof Node && rightVal instanceof Scalar) {
            throw new UnsupportedOperationException("Cannot add a scalar to a node.");
        } else if (leftVal instanceof Scalar && rightVal instanceof Node) {
            throw new UnsupportedOperationException("Cannot add a scalar to a node.");
        } else if (leftVal instanceof Node && rightVal instanceof StringNode) {
            throw new UnsupportedOperationException("Cannot add a string to a node.");
        } else if (leftVal instanceof StringNode && rightVal instanceof Node) {
            throw new UnsupportedOperationException("Cannot add a string to a node.");
        }
        //else if (leftVal instanceof Node l && rightVal instanceof Node r) {
        //return new Graph(l, r);  // create a graph with the two nodes
        //}
        else if (leftVal instanceof Matrix && rightVal instanceof Scalar) {
            return Matrix.add(leftVal, rightVal);
        } else if (leftVal instanceof Scalar && rightVal instanceof Matrix) {
            return Matrix.add(leftVal, rightVal);
        } else if (leftVal instanceof Matrix && rightVal instanceof StringNode) {
            throw new UnsupportedOperationException("Cannot add a string to a matrix.");
        } else if (leftVal instanceof StringNode && rightVal instanceof Matrix) {
            throw new UnsupportedOperationException("Cannot add a string to a matrix.");
        } else if (leftVal instanceof Node && rightVal instanceof Matrix) {
            throw new UnsupportedOperationException("Cannot add a matrix to a node.");
        } else if (leftVal instanceof Matrix && rightVal instanceof Node) {
            throw new UnsupportedOperationException("Cannot add a matrix to a node.");
        } else if (leftVal instanceof Graph && rightVal instanceof Scalar) {
            throw new UnsupportedOperationException("Cannot add a scalar to a graph.");
        } else if (leftVal instanceof Scalar && rightVal instanceof Graph) {
            throw new UnsupportedOperationException("Cannot add a scalar to a graph.");
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
