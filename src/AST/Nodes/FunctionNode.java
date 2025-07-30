package AST.Nodes;

import Interpreter.Tokenizer.TokenKind;
import Util.Environment;

import java.util.List;

public class FunctionNode extends Expression {
    private final String name;
    private final TokenKind returnType; // this really should not be String, but until I implement a type system, this will do
    private List<Expression> parameters;
    private final List<Statement> body;
    private final Environment environment;

    public FunctionNode(String name, TokenKind returnType, List<Expression> parameters, Environment environment) {
        this.name = name;
        this.returnType = returnType;
        this.parameters = parameters;
        this.body = null; // body is not needed for built-in functions
        this.environment = environment;
    }

    // this is the other constructor for built-ins, doesn't need a body
    public FunctionNode(String name, TokenKind returnType, List<Expression> parameters, List<Statement> body, Environment environment) {
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
    public Object evaluate(Environment env) {
        return null;
    }

    @Override
    public String toString() {
        return null;
    }

    // so we might want to implement some form of "execute" method
}
