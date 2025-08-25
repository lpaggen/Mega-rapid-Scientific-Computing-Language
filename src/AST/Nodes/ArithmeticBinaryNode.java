package AST.Nodes;

import Interpreter.Runtime.Environment;

public abstract class ArithmeticBinaryNode extends BinaryNode {

    public ArithmeticBinaryNode(Expression lhs, Expression rhs) {
        super(lhs, rhs);
    }

    @Override
    public Expression evaluate(Environment env) {
        return null;
    }

    @Override
    public String toString() {
        return null;
    }
}
