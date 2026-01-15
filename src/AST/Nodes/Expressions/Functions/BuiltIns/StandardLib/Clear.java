package AST.Nodes.Expressions.Functions.BuiltIns.StandardLib;

import AST.Nodes.Expressions.Functions.BuiltIns.BuiltInFunctionSymbol;
import Lexer.TokenKind;
import Runtime.Environment;

import java.util.List;

public class Clear extends BuiltInFunctionSymbol {
    public Clear() {
        super("clear", TokenKind.VOID);
    }

    @Override
    public void execute(Environment env, List<Object> args) {
        // Clear the environment by removing all symbols
        if (args != null && !args.isEmpty()) {
            throw new IllegalArgumentException("Clear function does not take any arguments.");
        }
        System.out.println("Clearing environment...");
        env.clear(); // Assuming Environment has a clear method to remove all symbols
    }
}
