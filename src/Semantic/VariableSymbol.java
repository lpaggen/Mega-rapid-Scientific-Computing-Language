package Semantic;

import Types.TypeNode;

public final class VariableSymbol implements Symbol {
    private final String name;
    private final TypeNode type;

    public VariableSymbol(String name, TypeNode type) {
        this.name = name;
        this.type = type;
    }

    public TypeNode getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }
}
