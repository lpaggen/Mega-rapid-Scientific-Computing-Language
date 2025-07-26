package Interpreter.Parser;

import Interpreter.Tokenizer.TokenKind;
import AST.Nodes.MathSymbol;

public class SymbolTable {
    private String name;
    private Integer intValue;
    private Float floatValue;
    private MathSymbol symbolValue;
    private TokenKind type;
}