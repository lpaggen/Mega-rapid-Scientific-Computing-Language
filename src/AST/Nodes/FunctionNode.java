package AST.Nodes;

import Interpreter.Tokenizer.TokenKind;
import Util.Environment;

import java.util.List;

public class FunctionNode extends Statement {
    private final String name;
    private final TokenKind returnType; // this really should not be String, but until I implement a type system, this will do
    private List<Statement> parameters;
    private final Expression body;
    private final Environment environment;

    public FunctionNode(String name, TokenKind returnType, List<Statement> parameters, Environment environment) {
        this.name = name;
        this.returnType = returnType;
        this.parameters = parameters;
        this.body = null; // body is not needed for built-in functions
        this.environment = environment;
    }

    // this is the other constructor for built-ins, doesn't need a body
    public FunctionNode(String name, TokenKind returnType, List<Statement> parameters, Expression body, Environment environment) {
        this.name = name;
        this.returnType = returnType;
        this.parameters = parameters;
        this.body = body;
        this.environment = environment;
    }

    public TokenKind getReturnType() {
        return returnType;
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public void execute(Environment env) {
        // this is where we would execute the function, but for now we will just print the name
        System.out.println("Executing function: " + name);
        // we should also check if the function has a body, and if it does, execute it
        if (body != null) {
            body.evaluate(env); // assuming body is an Expression that can be evaluated
        } else {
            System.out.println("No body to execute for function: " + name);
        }
    }

    // so we might want to implement some form of "execute" method
}
