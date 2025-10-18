package AST.Nodes.Expressions.Functions.BuiltIns;

import AST.Nodes.Expressions.StringNode;
import Interpreter.Tokenizer.TokenKind;
import Interpreter.Runtime.Environment;
import Interpreter.Parser.FunctionSymbol;

import java.util.List;

public abstract class BuiltInFunctionSymbol extends FunctionSymbol {
    private final String name;
    private final TokenKind returnType;

    public BuiltInFunctionSymbol(String name, TokenKind returnType) {
        super(name, returnType, null, null);
        this.name = name;
        this.returnType = returnType;
    }

    @Override
    public Object call(Environment env, List<Object> args) {
        // This method should be overridden in subclasses for specific built-in functions
        throw new UnsupportedOperationException("Built-in function '" + name + "' is not implemented.");
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name;
    }

    public StringNode getNameNode() {
        throw new UnsupportedOperationException("getNameNode() not implemented for built-in function: " + name);
    }

    // inherited classes can (must) override this to provide specific help
    public String help() {
        return "No help available for built-in function: " + name;
    }

    public TokenKind getReturnType() {
        return TokenKind.VOID; // some built in functions can return stuff, TODO: add support for this feature
    }

    public void execute(Environment env, List<Object> args) {
        // This method should be overridden in subclasses for specific built-in functions
        throw new UnsupportedOperationException("Built-in function '" + name + "' is not implemented.");
    }
}
