package Types.Abstract;

import Types.Abstract.Dimension;

public final class SymbolicDimension implements Dimension {
    private final String identifier;

    public SymbolicDimension(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return identifier;
    }
}
