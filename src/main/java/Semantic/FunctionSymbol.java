package Semantic;

import AST.FunctionTypeNode;

public record FunctionSymbol(FunctionTypeNode type) implements Symbol {}
