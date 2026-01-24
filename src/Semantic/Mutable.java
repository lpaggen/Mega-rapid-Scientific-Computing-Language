package Semantic;

import Semantic.Symbol;
import Semantic.VariableSymbol;

public sealed interface Mutable permits VariableSymbol {  // make sealed
    boolean mutable();
}
