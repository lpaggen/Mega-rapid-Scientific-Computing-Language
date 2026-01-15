package AST.Nodes.Expressions.Functions.BuiltIns.StandardLib;

import AST.Nodes.Expressions.Functions.BuiltIns.BuiltInFunctionSymbol;
import Lexer.TokenKind;
import Runtime.Environment;

import java.util.List;

public class Type extends BuiltInFunctionSymbol {
    public Type() {
        super("type", TokenKind.VOID); // this returns something else than void, of course, idk how to implement it yet
    }

    // this works, but at the moment we still have some Java types
    // so while my language wants something like "int", "string", "bool", etc. it currently returns Java types
    @Override
    public Object call(Environment env, List<Object> args) {
        if (args.isEmpty()) {
            throw new IllegalArgumentException("Type function requires at least one argument.");
        }

        Object arg = args.get(0);
        String typeName = arg.getClass().getSimpleName();

        // Return the type name as a string
        return typeName;
    }
}
