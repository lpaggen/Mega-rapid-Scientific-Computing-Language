//package AST.Nodes.Expressions.BinaryOperations.Arithmetic;
//
//import AST.BinaryOperations.Expressions.BinaryNode;
//import AST.Expressions.Expression;
//import Semantic.ScopeStack;
//import Lexer.TokenKind;
//
//public abstract class ArithmeticBinaryNode extends BinaryNode {
//
//    public ArithmeticBinaryNode(Expression lhs, Expression rhs) {
//        super(lhs, TokenKind.PLUS, rhs);
//    }
//
////    @Override
////    public Expression evaluate(ScopeStack env) {
////        return this;
////    }
//
//    @Override
//    public TokenKind getType(ScopeStack env) {
//        TokenKind leftType = lhs.getType(env);
//        TokenKind rightType = rhs.getType(env);
//        if (leftType == TokenKind.SCALAR && rightType == TokenKind.SCALAR) {
//            return TokenKind.SCALAR;
//        }
//        if (leftType == TokenKind.MATH || rightType == TokenKind.MATH) {
//            return TokenKind.MATH;
//        }
//        throw new RuntimeException("Type error in arithmetic operation: " + leftType + " and " + rightType);
//    }
//
//    @Override
//    public String toString() {
//        return null;
//    }
//}
