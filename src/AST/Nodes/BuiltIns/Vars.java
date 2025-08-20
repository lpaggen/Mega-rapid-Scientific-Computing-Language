package AST.Nodes.BuiltIns;

import Interpreter.Tokenizer.TokenKind;
import Util.Environment;

import java.util.List;

public class Vars extends BuiltInFunctionSymbol {
    Environment env;

    public Vars() {
        super("vars");
    }

    @Override
    public Object call(Environment env, List<Object> args) {
        // This function should return a list of all variables in the environment
        // that's what it does, not sure how it will look right now however
        return env.getAllSymbols();
    }
}
