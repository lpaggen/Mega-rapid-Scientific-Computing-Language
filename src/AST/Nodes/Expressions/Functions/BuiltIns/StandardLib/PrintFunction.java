package AST.Nodes.Expressions.Functions.BuiltIns.StandardLib;

import AST.Nodes.Expressions.Functions.BuiltIns.BuiltInFunctionSymbol;
import Interpreter.Tokenizer.TokenKind;
import Interpreter.Runtime.Environment;

import java.util.List;

public class PrintFunction extends BuiltInFunctionSymbol {
    public PrintFunction() {
        super("print", TokenKind.VOID);
    }

    public Object call(Environment env, List<Object> args) {
        if (!args.isEmpty() && args.getFirst() != null) {
            System.out.println(args.getFirst().toString());
        } else {
            System.out.println();
        }
        return null;
    }
}
