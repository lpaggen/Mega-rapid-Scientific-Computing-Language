package AST.Expressions;

import AST.Nodes.ASTNode;

public class ConstantNode extends ASTNode {
    private final Number value; // hold int or float
    private final ConstantType type;

    public enum ConstantType {
        INTEGER,
        FLOAT
    }

    public ConstantNode(Integer intArgument) { // one constructor supports int, other float
        this.value = intArgument;
        this.type = ConstantType.INTEGER;
    }

    public ConstantNode(Float floatArgument) {
        this.value = floatArgument;
        this.type = ConstantType.FLOAT;
    }

    public Number getValue() {
        return value;
    }

    public ConstantType getType() {
        return type;
    }

    @Override
    public String toString() {
        return value.toString(); // could return value too
    }
}