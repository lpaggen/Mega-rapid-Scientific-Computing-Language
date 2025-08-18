package Util;

import AST.Nodes.BuiltIns.BuiltInFunctionSymbol;
import AST.Nodes.Expression;
import AST.Nodes.Statement;
import Interpreter.Tokenizer.TokenKind;

import java.util.List;

public class FunctionSymbol extends Symbol {
    private final List<VariableSymbol> parameters; // User-defined functions have parameters
    private final List<Statement> body;
    private final BuiltInFunctionSymbol builtIn; // null if not a built-in function

    // User-defined
    public FunctionSymbol(String name, TokenKind returnType, List<VariableSymbol> parameters, List<Statement> body) {
        super(name, returnType);
        this.parameters = parameters;
        this.body = body;
        this.builtIn = null;
    }

    // built ins
    public FunctionSymbol(BuiltInFunctionSymbol builtIn) {
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
        }
        // TODO: add support to bind arguments to the function, so users can define their own functions. atm testing only builtins
    }
}
