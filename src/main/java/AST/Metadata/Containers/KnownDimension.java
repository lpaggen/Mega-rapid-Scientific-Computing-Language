package AST.Metadata.Containers;

public record KnownDimension(int value) implements Dimension {
    @Override
    public boolean isKnown() {
        return true;
    }
    @Override
    public String getName() {
        return Integer.toString(value);
    }
}
