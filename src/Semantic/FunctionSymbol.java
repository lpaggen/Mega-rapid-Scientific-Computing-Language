package Semantic;

import Types.Functions.FunctionTypeNode;

public final class FunctionSymbol implements Symbol {
    private final String name;
    private final FunctionTypeNode type;

    public FunctionSymbol(String name, FunctionTypeNode type) {
        this.name = name;
        this.type = type;
    }

    public FunctionTypeNode getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }
}
