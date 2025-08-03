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

    // this is the other constructor for user-defined stuff
    public FunctionNode(String name, TokenKind returnType, List<Statement> arguments, List<Statement> body) {
        this.name = name;
        this.returnType = returnType;
        this.arguments = arguments;
        this.body = body;
    }

    // for built-ins: no body needed
    public FunctionNode(String name, TokenKind returnType, List<Statement> arguments) {
        this(name, returnType, arguments, List.of());
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
        if (!body.isEmpty()) {
            for (Statement statement : body) {
                statement.execute(env);
            }
        } else {
            System.out.println("No body to execute for function: " + name);
        }

    public String getName() {
        return name;
    }

    public Statement[] getBody() {
        if (body.isEmpty()) {
            // if the body is empty, we return an empty array
            return new Statement[0];
        } else {
            // otherwise, we return the body as an array
            return body.toArray(new Statement[0]);
        }
    }
}
