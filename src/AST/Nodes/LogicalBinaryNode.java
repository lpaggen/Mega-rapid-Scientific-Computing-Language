package AST.Nodes;

import Interpreter.Tokenizer.Token;
import Util.Environment;

// i am making this a subclass of BinaryNode, we could just leave everything in BinaryNode but this makes things clearer
public abstract class LogicalBinaryNode extends BinaryNode {
    public LogicalBinaryNode(Expression lhs, Expression rhs) {
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
