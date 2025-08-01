package AST.Nodes.BuiltIns;

import AST.Nodes.FunctionNode;
import Interpreter.Tokenizer.TokenKind;
import Util.Environment;

import java.util.List;

public class PrintFunction extends BuiltInFunctionNode {
    public PrintFunction(Environment env) {
        super("print", TokenKind.VOID, List.of(), env);
    }

    @Override
    public void executeWithArgs(Environment env, List<Object> args) {
        if (!args.isEmpty()) {
            System.out.println(args.getFirst());
        } else {
            System.out.println();
        }
    }
}
