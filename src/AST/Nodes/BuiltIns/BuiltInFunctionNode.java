package AST.Nodes.BuiltIns;

import AST.Nodes.FunctionNode;
import AST.Nodes.Statement;
import Interpreter.Tokenizer.TokenKind;
import Util.Environment;

import java.util.List;

// this is the class for the built-ins, i need it because it gets too messy without it, and it really doesn't add much complexity
// it has this executeWithArgs method, so it can accept arguments, like print needs an object to print and what not
public abstract class BuiltInFunctionNode extends FunctionNode {
    public BuiltInFunctionNode(String name, TokenKind returnType, List<Statement> arguments) {
        super(name, returnType, arguments, List.of());
    }

    public abstract void execute(Environment env, List<Object> args);

    public String getName() {
        return super.getName();
    }
}
