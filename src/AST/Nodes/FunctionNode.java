package AST.Nodes;

import Interpreter.Tokenizer.Token;
import Util.Environment;

import java.util.List;

public class FunctionNode implements Callable {
    private final String name;
    private final String returnType; // this really should not be String, but until I implement a type system, this will do
    private List<String> parameters;
    private final List<Statement> body;
    private final Environment<String, Token> environment;

    public FunctionNode(String name, String returnType, List<Statement> body, Environment<String, Token> environment) {
        this.name = name;
        this.returnType = returnType;
        this.body = body;
        this.environment = environment;
    }

    @Override
    public Object call(Object... args) {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
}
