package AST.Metadata.Functions;

import Types.TypeNode;

public record ParamNode(String name, TypeNode type) {

    @Override
    public String toString() {
        return "ParamNode{" +
                "name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
