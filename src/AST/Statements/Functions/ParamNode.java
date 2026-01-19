package AST.Statements.Functions;

import Types.TypeNode;

public final class ParamNode {
    private final String name;
    private final TypeNode type;
    public ParamNode(String name, TypeNode type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public TypeNode getType() {
        return type;
    }

    @Override
    public String toString() {
        return "ParamNode{" +
                "name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
