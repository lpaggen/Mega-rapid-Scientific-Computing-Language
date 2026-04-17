package Semantic;

import AST.Type;

public record VariableSymbol(Type type, boolean mutable) implements Symbol, Mutable {}
