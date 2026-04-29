package Semantic;

import AST.FunctionTypeNodeInterface;

public record FunctionSymbol(FunctionTypeNodeInterface type) implements Symbol {}
