package AST.Metadata.Containers;

public final class KnownDimension implements Dimension {
    private final int value;
    public KnownDimension(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
