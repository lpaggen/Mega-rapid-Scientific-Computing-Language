package Semantic;

import AST.Expressions.Mutable;

// the Symbol interface can represent both variables and functions in the environment
public sealed interface Symbol permits VariableSymbol, FunctionSymbol, TypeSymbol {
    String getName();
}
