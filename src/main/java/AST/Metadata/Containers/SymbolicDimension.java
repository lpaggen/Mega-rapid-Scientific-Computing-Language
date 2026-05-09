package AST.Metadata.Containers;

public record SymbolicDimension(String name) implements Dimension {
    @Override
    public boolean isKnown() {
        return false;
    }

    @Override
    public String getName() {
        return name;
    }
}
