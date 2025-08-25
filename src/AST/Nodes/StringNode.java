package AST.Nodes;

import Interpreter.Runtime.Environment;

public class StringNode extends Expression {
    private final String value;

    public StringNode(String value) {
        this.value = value;
    }

    @Override
    public Expression evaluate(Environment env) {
        return this;
    }

    @Override
    public String toString() {
        return "\"" + value + "\"";
    }

    public String getValue() {
        return value;
    }
}
