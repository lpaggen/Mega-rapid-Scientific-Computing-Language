package AST.Nodes.Conditional;

import AST.Nodes.DataTypes.Constant;
import AST.Nodes.DataTypes.IntegerConstant;
import AST.Nodes.Expression;
import Interpreter.Runtime.Environment;

public class BooleanNode extends Expression {
    private final boolean value;

    public BooleanNode(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public Expression evaluate(Environment env) {
        return this;
    }

    @Override
    public String toString() {
        return value ? "true" : "false";
    }

    public Constant toNumeric() { // this is gonna move or change, i don't know why it's there to begin with
        return new IntegerConstant(value ? 1 : 0, false);
    }
}
