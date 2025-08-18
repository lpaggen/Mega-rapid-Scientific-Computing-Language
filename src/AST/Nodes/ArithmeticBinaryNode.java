package AST.Nodes;

import Util.Environment;

public abstract class ArithmeticBinaryNode extends BinaryNode {

    public ArithmeticBinaryNode(Expression lhs, Expression rhs) {
        super(lhs, rhs);
    }

    @Override
    public Object evaluate(Environment env) {
        return null;
    }

    @Override
    public String toString() {
        return null;
    }
}
