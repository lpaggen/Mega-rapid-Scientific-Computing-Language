package AST.Nodes.Expressions.BinaryOperations;

import AST.Nodes.Expressions.Expression;
import Interpreter.Runtime.Environment;

// i am making this a subclass of BinaryNode, we could just leave everything in BinaryNode but this makes things clearer
public abstract class LogicalBinaryNode extends BinaryNode {
    public LogicalBinaryNode(Expression lhs, Expression rhs) {
        super(lhs, rhs);
    }

    @Override
    public Expression evaluate(Environment env) {
        return null; // can't call on this
    }

    @Override
    public String toString() {
        return null;
    }
}
