//package AST.Nodes.Expressions.BinaryOperations.Arithmetic;
//
//import AST.Nodes.DataStructures.Edge;
//import AST.Nodes.DataStructures.Graph;
//import AST.Nodes.DataStructures.Node;
//import AST.Expressions.Expression;
//import AST.Expressions.StringNode;
//import AST.Expressions.VariableNode;
//import Semantic.ScopeStack;
//import Lexer.TokenKind;
//import Algebra.AlgebraEngine;
//
//// NOTE: Assumes ArithmeticBinaryNode is defined in the AST.Nodes.Expressions.BinaryOperations.Arithmetic package
//public class Div extends ArithmeticBinaryNode {
//    public Div(Expression lhs, Expression rhs) {
//        super(lhs, rhs);
//    }
//
//    @Override
//    public Expression evaluate(ScopeStack env) {
//        Expression leftVal = lhs.evaluate(env);
//        Expression rightVal = rhs.evaluate(env);
//        if (leftVal instanceof Scalar l && rightVal instanceof Scalar r) {
//            if (r.getDoubleValue() == 0.0) {
//                throw new ArithmeticException("Division by zero error.");
//            }
//            return Scalar.divide(l, r);
//        }
//        else if (leftVal instanceof VariableNode || rightVal instanceof VariableNode) {
//            return AlgebraEngine.simplify(new Div(leftVal, rightVal));
//        }
//        else if (leftVal instanceof Matrix || rightVal instanceof Matrix) {
//            return Matrix.div(leftVal, rightVal);
//        }
//
//        // handle unsupported types
//        else if (leftVal instanceof StringNode || rightVal instanceof StringNode) {
//            throw new UnsupportedOperationException("The division operator (/) is not supported for string types.");
//        }
//        else if (leftVal instanceof Graph || rightVal instanceof Graph) {
//            throw new UnsupportedOperationException("The division operator (/) is not supported for graphs.");
//        }
//        else if (leftVal instanceof Node || rightVal instanceof Node) {
//            throw new UnsupportedOperationException("The division operator (/) is not supported for nodes.");
//        }
//        else if (leftVal instanceof Edge || rightVal instanceof Edge) {
//            throw new UnsupportedOperationException("The division operator (/) is not supported for edges.");
//        }
//        return new Div(leftVal, rightVal);
//    }
//
//    public double evaluateNumeric(ScopeStack env) {
//        double rhsVal = rhs.evaluateNumeric(env);
//        if (rhsVal == 0.0) {
//            throw new ArithmeticException("Division by zero during numeric evaluation.");
//        }
//        return lhs.evaluateNumeric(env) / rhsVal;
//    }
//
//    public Expression getNum() {
//        return lhs;
//    }
//
//    public Expression getDenom() {
//        return rhs;
//    }
//
//    @Override
//    public String toString() {
//        return lhs.toString() + " / " + rhs.toString();
//    }
//
//    public TokenKind getType(ScopeStack env) {
//        return super.getType(env);
//    }
//}
