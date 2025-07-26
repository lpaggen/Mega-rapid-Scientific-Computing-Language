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

    // so until (maybe) future revisions, this should stay the base ASTNode
    // in the future we might specify a more specific type for the body,
    // but really we might not need to, think about it:
    // the body is just a list of statements, and we can execute them in the environment
    public ASTNode getBody() {
        return body;
    }
}