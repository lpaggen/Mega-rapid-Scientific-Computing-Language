package AST.Nodes.BuiltIns;

import AST.Nodes.ASTNode;
import Interpreter.Tokenizer.TokenKind;
import Util.Environment;
import Util.FunctionSymbol;

import java.util.List;

public class BuiltInFunctionSymbol extends FunctionSymbol {
    private final String name;

    public BuiltInFunctionSymbol(String name) {
        super(name, null, null, null);
        this.name = name;
    }

    @Override
    public void call(Environment env, List<ASTNode> args) {
        // This method should be overridden in subclasses for specific built-in functions
        throw new UnsupportedOperationException("Built-in function '" + name + "' is not implemented.");
    }

    public String getName() {
        return name;
    }

    public TokenKind getReturnType() {
        return TokenKind.VOID; // Built-in functions may not have a specific return type
    }

    public void execute(Environment env, List<ASTNode> args) {
        // This method should be overridden in subclasses for specific built-in functions
        throw new UnsupportedOperationException("Built-in function '" + name + "' is not implemented.");
    }
}
