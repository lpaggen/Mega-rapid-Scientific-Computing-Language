package AST.Nodes;

import Interpreter.Runtime.Environment;

public class PrimaryNode extends Expression {
    private final Expression value;

    public PrimaryNode(Expression value) {
        this.value = value;
    }

    @Override
    public Expression evaluate(Environment env) {
        return value;
    }

    @Override
    public String toString() {
        return value.toString(); // can't believe i didn't implement toString before.
    }
}
