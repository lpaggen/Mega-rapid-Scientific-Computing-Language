package Util;

import AST.Nodes.ASTNode;
import AST.Nodes.BuiltIns.BuiltInFunctionNode;
import AST.Nodes.FunctionNode;
import AST.Nodes.Statement;
import Interpreter.Tokenizer.TokenKind;

import java.util.List;

public class FunctionSymbol extends Symbol {
    private final List<Statement> parameters;
    private final ASTNode body;
    private final BuiltInFunctionNode builtIn;

    // User-defined
    public FunctionSymbol(String name, TokenKind returnType, List<Statement> parameters, ASTNode body) {
        super(name, returnType);
        this.parameters = parameters;
        this.body = body;
        this.builtIn = null;
    }

    // Built-in
    public FunctionSymbol(BuiltInFunctionNode builtIn) {
        super(builtIn.getName(), builtIn.getReturnType());
        this.parameters = List.of(); // or builtIn.getParameters()
        this.body = null;
        this.builtIn = builtIn;
    }

    public boolean isBuiltIn() {
        return builtIn != null;
    }

    public void call(Environment env, List<Object> args) {
        if (isBuiltIn()) {
            builtIn.execute(env, args);
        } else {
            // TODO: bind args to parameters, etc.
            if (body instanceof FunctionNode functionNode) {
                for (Statement s : functionNode.getBody()) {
                    s.execute(env);
                }
            } else {
                throw new RuntimeException("Invalid function body type: " + body.getClass());
            }
        }
    }
}
