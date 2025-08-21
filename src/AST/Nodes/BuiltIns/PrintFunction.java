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

    public Object call(Environment env, List<Object> args) {
        if (!args.isEmpty()) {
            System.out.println(args.getFirst().toString());
        } else {
            System.out.println();
        }
        return null;
    }
}
