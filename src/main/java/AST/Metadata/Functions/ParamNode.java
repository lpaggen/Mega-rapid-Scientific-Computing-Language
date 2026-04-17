package AST.Metadata.Functions;

import AST.Type;

public record ParamNode(String name, Type type) {

    @Override
    public String toString() {
        return "ParamNode{" +
                "name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
