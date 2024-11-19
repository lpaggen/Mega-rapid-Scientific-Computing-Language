package Compiler.Parser;

import AST.Nodes.ASTNode;
import Compiler.Tokenizer.TokenKind;
import DataTypes.Symbol;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    private String name;
    private Integer intValue;
    private Float floatValue;
    private Symbol symbolValue;
    private TokenKind type;
}