package AST.Metadata.Containers;

public sealed interface Dimension permits KnownDimension, SymbolicDimension, BinaryDimension {
    boolean isKnown();
}
