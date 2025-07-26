package Util;

import AST.Nodes.ASTNode;
import Interpreter.Tokenizer.TokenKind;

import java.util.List;

public class FunctionSymbol extends Symbol {
    private final List<String> parameters;
    private final ASTNode body;  // Or whatever type your function body has

    public FunctionSymbol(String name, TokenKind returnType, List<String> parameters, ASTNode body) {
        super(name, returnType);
        this.parameters = parameters;
        this.body = body;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public ASTNode getBody() {
        return body;
    }
}