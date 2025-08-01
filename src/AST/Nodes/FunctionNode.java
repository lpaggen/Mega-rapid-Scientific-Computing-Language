package AST.Nodes;

import AST.Nodes.BuiltIns.BuiltIns;
import Interpreter.Tokenizer.TokenKind;
import Util.Environment;

import javax.swing.*;
import java.util.List;

public class FunctionNode extends Statement {
    private final String name;
    private final TokenKind returnType; // this really should not be String, but until I implement a type system, this will do
    private List<Statement> arguments;
    private final List<Statement> body;
    private final Environment environment;

    // this is the other constructor for user-defined stuff
    public FunctionNode(String name, TokenKind returnType, List<Statement> arguments, List<Statement> body, Environment environment) {
        this.name = name;
        this.returnType = returnType;
        this.arguments = arguments;
        this.body = body;
        this.environment = environment;
    }

    // for built-ins: no body needed
    public FunctionNode(String name, TokenKind returnType, List<Statement> arguments, Environment environment) {
        this(name, returnType, arguments, List.of(), environment);
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
            for (Statement statement : body) {
                statement.execute(env); // assuming each statement can be executed in the environment
            }
        } else {
            System.out.println("No body to execute for function: " + name);
        }
    }

    // so we might want to implement some form of "execute" method
}
