package AST.Nodes.BuiltIns;

import AST.Nodes.ASTNode;
import Interpreter.Tokenizer.TokenKind;
import Util.Environment;
import Util.FunctionSymbol;

import java.util.List;

public class PrintFunction extends BuiltInFunctionSymbol {
    public PrintFunction() {
        super("print");
    }

    public void call(Environment env, List<ASTNode> args) {
        if (!args.isEmpty()) {
            System.out.println(args.getFirst());
        } else {
            System.out.println();
        }
    }
}
