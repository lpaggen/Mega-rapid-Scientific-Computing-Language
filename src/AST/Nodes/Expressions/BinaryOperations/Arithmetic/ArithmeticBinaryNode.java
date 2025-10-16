package AST.Nodes.Expressions.BinaryOperations.Arithmetic;

import AST.Nodes.Expressions.BinaryOperations.BinaryNode;
import AST.Nodes.Expressions.Expression;
import Interpreter.Runtime.Environment;
import Interpreter.Tokenizer.TokenKind;

public abstract class ArithmeticBinaryNode extends BinaryNode {

    public ArithmeticBinaryNode(Expression lhs, Expression rhs) {
        super(lhs, rhs);
    }

    @Override
    public Expression evaluate(Environment env) {
        return this;
    }

    @Override
    public TokenKind getType(Environment env) {
        TokenKind leftType = lhs.getType(env);
        TokenKind rightType = rhs.getType(env);
        if (leftType == TokenKind.SCALAR && rightType == TokenKind.SCALAR) {
            return TokenKind.SCALAR;
        }
        if (leftType == TokenKind.MATH || rightType == TokenKind.MATH) {
            return TokenKind.MATH;
        }
        throw new RuntimeException("Type error in arithmetic operation: " + leftType + " and " + rightType);
    }

    @Override
    public String toString() {
        return null;
    }
}
