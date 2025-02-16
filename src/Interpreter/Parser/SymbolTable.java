package Interpreter.Parser;

import Interpreter.Tokenizer.TokenKind;
import DataTypes.Symbol;

public class SymbolTable {
    private String name;
    private Integer intValue;
    private Float floatValue;
    private Symbol symbolValue;
    private TokenKind type;
}