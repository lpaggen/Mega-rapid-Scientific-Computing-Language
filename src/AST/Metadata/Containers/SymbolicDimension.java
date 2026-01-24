package AST.Metadata.Containers;

import AST.Metadata.Containers.Dimension;

public final class SymbolicDimension implements Dimension {
    private final String identifier;

    public SymbolicDimension(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return identifier;
    }

    @Override
    public boolean isKnown() {
        return false;
    }
}
