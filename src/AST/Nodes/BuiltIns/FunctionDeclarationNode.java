package AST.Nodes.BuiltIns;

import AST.Nodes.Statement;
import Interpreter.Tokenizer.TokenKind;
import Util.Environment;
import Util.FunctionSymbol;
import Util.VariableSymbol;

import java.util.List;

public class FunctionDeclarationNode extends Statement {
    private final String name;
    private final TokenKind returnType;
    private final List<VariableSymbol> arguments;
    private final List<Statement> body; // Placeholder for function body, if needed

    public FunctionDeclarationNode(String name, TokenKind returnType, List<VariableSymbol> arguments, List<Statement> body) {
        this.name = name;
        this.returnType = returnType;
        this.arguments = arguments;
        this.body = body;
    }

    @Override
    public void execute(Environment env) {
        // Register the function in the environment
        env.declareSymbol(name, new FunctionSymbol(name, returnType, arguments, body != null ? body : List.of()));

        // Optionally, you can print or log the function declaration
        System.out.println("Function declared: " + name + " with return type: " + returnType);
    }
}
