package AST.Nodes.BuiltIns;

import AST.Nodes.Statement;
import Interpreter.Tokenizer.Token;
import Util.Environment;

import java.util.List;

public abstract class BuiltInFunction extends Statement {
    public Object call(List<Object> args) {
        // This method should be overridden by subclasses
        throw new UnsupportedOperationException("Built-in function must implement call method.");
    }

    @Override
    public void execute(Environment<String, Token> env) {
        throw new UnsupportedOperationException("Built-in functions cannot be executed directly.");
    }
}
