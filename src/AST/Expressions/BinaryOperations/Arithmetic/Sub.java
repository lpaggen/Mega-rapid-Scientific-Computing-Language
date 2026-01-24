//package AST.Nodes.Expressions.BinaryOperations.Arithmetic;
//
//import AST.Nodes.DataStructures.Edge;
//import AST.Nodes.DataStructures.Graph;
//import AST.Nodes.DataStructures.Matrix;
//import AST.Nodes.DataStructures.Node;
//import AST.Nodes.DataTypes.Scalar;
//import AST.Expressions.Expression;
//import AST.Expressions.StringNode;
//import AST.Expressions.VariableNode;
//import Semantic.Environment;
//import Lexer.TokenKind;
//import Algebra.AlgebraEngine;
//
//// NOTE: Assumes ArithmeticBinaryNode is defined in the AST.Nodes.Expressions.BinaryOperations.Arithmetic package
//public class Sub extends ArithmeticBinaryNode {
//    public Sub(Expression lhs, Expression rhs) {
//        super(lhs, rhs);
//    }
//
//    @Override
//    public Expression evaluate(Environment env) {
//        Expression leftVal = lhs.evaluate(env);
//        Expression rightVal = rhs.evaluate(env);
//
//        if (leftVal instanceof Scalar l && rightVal instanceof Scalar r) {
//            return Scalar.subtract(l, r);
//        }
//        else if (leftVal instanceof VariableNode || rightVal instanceof VariableNode) {
//            return AlgebraEngine.simplify(new Sub(leftVal, rightVal));
//        }
//        else if (leftVal instanceof Matrix || rightVal instanceof Matrix) {
//            return Matrix.sub(leftVal, rightVal);
//        }
//        // errors
//        else if (leftVal instanceof StringNode || rightVal instanceof StringNode) {
//            throw new UnsupportedOperationException("The subtraction operator (-) is not supported for string types.");
//        }
//        else if (leftVal instanceof Graph || rightVal instanceof Graph) {
//            return Graph.sub(leftVal, rightVal);
//        }
//        else if (leftVal instanceof Node || rightVal instanceof Node) {
//            throw new UnsupportedOperationException("The subtraction operator (-) is not supported for nodes.");
//        }
//        else if (leftVal instanceof Edge || rightVal instanceof Edge) {
//            throw new UnsupportedOperationException("The subtraction operator (-) is not supported for edges.");
//        }
//        else if (leftVal instanceof Graph l && rightVal instanceof Node r) {
//            return Graph.sub(l, r);
//        }
//        else if (leftVal instanceof Node l && rightVal instanceof Graph r) {
//            throw new UnsupportedOperationException("Cannot subtract graph from node");
//        }
//        else if (leftVal instanceof Graph l && rightVal instanceof Edge r) {
//            return Graph.sub(l, r);
//        }
//        else if (leftVal instanceof Edge l && rightVal instanceof Graph r) {
//            throw new UnsupportedOperationException("Cannot subtract edge from node");
//        }
//        return new Sub(leftVal, rightVal);
//    }
//
//    public double evaluateNumeric(Environment env) {
//        return lhs.evaluateNumeric(env) - rhs.evaluateNumeric(env);
//    }
//
//    @Override
//    public String toString() {
//        return lhs.toString() + " - " + rhs.toString();
//    }
//
//    public TokenKind getType(Environment env) {
//        return super.getType(env);
//    }
//}
