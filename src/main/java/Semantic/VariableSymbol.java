package Semantic;

import AST.TypeInterface;

public record VariableSymbol(TypeInterface typeInterface, boolean mutable) implements Symbol, Mutable {}
