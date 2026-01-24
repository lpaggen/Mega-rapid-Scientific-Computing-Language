package Semantic;

import Types.TypeNode;

public record VariableSymbol(TypeNode type, boolean mutable) implements Symbol, Mutable {}
