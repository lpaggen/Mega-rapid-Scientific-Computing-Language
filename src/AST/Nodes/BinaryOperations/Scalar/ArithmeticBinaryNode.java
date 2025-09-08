package AST.Nodes.BinaryOperations.Scalar;

import AST.Nodes.BinaryOperations.BinaryNode;
import AST.Nodes.Expression;
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
        if (leftType == TokenKind.INTEGER && rightType == TokenKind.INTEGER) {
            return TokenKind.INTEGER;
        }
        if (leftType == TokenKind.FLOAT && rightType == TokenKind.FLOAT) {
            return TokenKind.FLOAT;
        }
        if ((leftType == TokenKind.INTEGER && rightType == TokenKind.FLOAT) ||
            (leftType == TokenKind.FLOAT && rightType == TokenKind.INTEGER)) {
            return TokenKind.FLOAT;
        }
        throw new RuntimeException("Type error in arithmetic operation: " + leftType + " and " + rightType);
    }

    @Override
    public String toString() {
        return null;
    }
}
