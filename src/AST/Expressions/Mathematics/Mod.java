//package AST.Nodes.Expressions.Mathematics;
//
//import AST.Nodes.DataTypes.Scalar;
//import AST.Nodes.Expressions.BinaryOperations.Arithmetic.ArithmeticBinaryNode;
//import AST.Expressions.Expression;
//import Semantic.ScopeStack;
//
//public class Mod extends ArithmeticBinaryNode {
//    public Mod(Expression lhs, Expression rhs) {
//        super(lhs, rhs);
//    }
//
//    public double evaluateNumeric(ScopeStack env) {
//        double leftVal = lhs.evaluateNumeric(env);
//        double rightVal = rhs.evaluateNumeric(env);
//        return leftVal % rightVal;
//    }
//
//    @Override
//    public Expression evaluate(ScopeStack env) {
//        Expression leftVal = lhs.evaluate(env);
//        Expression rightVal = rhs.evaluate(env);
//        if (leftVal instanceof Scalar l && rightVal instanceof Scalar r) {
//            int result = (int) l.getDoubleValue() % (int) r.getDoubleValue();
//            return new Scalar(result);
//        }
//        return new Mod(leftVal, rightVal);
//    }
//}
