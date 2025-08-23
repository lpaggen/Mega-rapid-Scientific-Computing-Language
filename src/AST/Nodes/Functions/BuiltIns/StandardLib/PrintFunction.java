package AST.Nodes.Functions.BuiltIns.StandardLib;

import AST.Nodes.Functions.BuiltIns.BuiltInFunctionSymbol;
import Interpreter.Tokenizer.TokenKind;
import Interpreter.Runtime.Environment;

import java.util.List;

public class PrintFunction extends BuiltInFunctionSymbol {
    public PrintFunction() {
        super("print", TokenKind.VOID);
    }

    public Object call(Environment env, List<Object> args) {
        if (!args.isEmpty()) {
            System.out.println(args.getFirst().toString());
        } else {
            System.out.println();
        }
        return null;
    }
}
