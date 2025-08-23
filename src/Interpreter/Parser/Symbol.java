package Interpreter.Parser;

import AST.Nodes.Expression;
import Interpreter.Tokenizer.TokenKind;

// the Symbol interface can represent both variables and functions in the environment
public abstract class Symbol {
    protected final String name;
    protected final TokenKind type;

    protected Symbol(String name, TokenKind type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public TokenKind getType() {
        return type;
    }
}
