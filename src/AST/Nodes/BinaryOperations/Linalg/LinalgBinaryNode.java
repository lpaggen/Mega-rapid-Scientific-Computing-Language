package AST.Nodes.BinaryOperations.Linalg;

import AST.Nodes.BinaryOperations.BinaryNode;
import AST.Nodes.Expression;
import Interpreter.Runtime.Environment;
import Interpreter.Tokenizer.TokenKind;

public abstract class LinalgBinaryNode extends BinaryNode {
    public LinalgBinaryNode(Expression lhs, Expression rhs) {
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

        // this isn't right, but it should do just fine until I need it
        if (leftType == TokenKind.MATRIX && rightType == TokenKind.MATRIX) {
            return TokenKind.MATRIX;
        } else if (leftType == TokenKind.VECTOR && rightType == TokenKind.VECTOR) {
            return TokenKind.VECTOR;
        }
        throw new RuntimeException("Type error in linear algebra operation: " + leftType + " and " + rightType);
    }

    @Override
    public String toString() {
        return null;
    }
}
