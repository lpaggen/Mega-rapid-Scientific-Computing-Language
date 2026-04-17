package Semantic;

// the Symbol interface can represent both variables and functions in the environment
public sealed interface Symbol permits VariableSymbol, FunctionSymbol, TypeSymbol {}
