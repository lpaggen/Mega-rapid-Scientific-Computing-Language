package Parser;

import AST.Expressions.Functions.BuiltIns.BuiltInFunctionSymbol;
import AST.Statements.Statement;
import Lexer.TokenKind;
import Runtime.Environment;

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

    public boolean isBuiltIn() {
        return builtIn != null;
    }

    public Object call(Environment env, List<Object> args) {
        if (isBuiltIn()) {
            builtIn.execute(env, args);
        }
        // TODO: add support to bind arguments to the function, so users can define their own functions. atm testing only builtins
        throw new UnsupportedOperationException("Function '" + getName() + "' is not implemented or is not a built-in function.");
    }
}
