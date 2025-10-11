package AST.Nodes.Expressions.BinaryOperations.Linalg;

import AST.Nodes.Expressions.BinaryOperations.BinaryNode;
import AST.Nodes.Expressions.Expression;
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
        // maybe it works, tbh, it's not causing any issues
        if (leftType == TokenKind.MATRIX || rightType == TokenKind.MATRIX) {
            return TokenKind.MATRIX;
        }
        // vector is deprecated, need to just consider Constant and Matrix
        // and know if Constant is INTEGER or FLOAT
        throw new UnsupportedOperationException("Linalg operations currently only support MATRIX types.");
    }

    @Override
    public String toString() {
        return null;
    }
}
