package AST.Metadata.Functions;

import AST.TypeInterface;

public record ParamNode(String name, TypeInterface typeInterface) {

    @Override
    public String toString() {
        return "ParamNode{" +
                "name='" + name + '\'' +
                ", typeInterface=" + typeInterface +
                '}';
    }
}
