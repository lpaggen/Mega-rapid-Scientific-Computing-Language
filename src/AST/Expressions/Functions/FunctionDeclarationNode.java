package AST.Expressions.Functions;

import AST.Statements.Statement;
import Lexer.TokenKind;
import Semantic.VariableSymbol;

import java.util.List;

// so, the FunctionDeclarationNode is used to declare a function in the AST.
// It will be used to register the function in the environment, so it can be called later.
// This is different from FunctionCallNode, which is used to call a function.
// here, we want to parse PARAMETERS, arguments are during the call !!!! don't get it mixed...
public class FunctionDeclarationNode extends Statement {
    private final String name;
    private final TokenKind returnType;
    private final List<VariableSymbol> parameters; // technically i should allow for all Symbols, since you can pass Functions ideally
    private final List<Statement> body; // Placeholder for function body, if needed

    public FunctionDeclarationNode(String name, TokenKind returnType, List<VariableSymbol> parameters, List<Statement> body) {
        this.name = name;
        this.returnType = returnType;
        this.parameters = parameters;
        this.body = body;
    }

//    @Override
//    public void execute(ScopeStack env) {
//        // Register the function in the environment
//        env.declareSymbol(name, new FunctionSymbol(name, returnType, parameters, body != null ? body : List.of()));
//
//        // Optionally, you can print or log the function declaration
//        System.out.println("Function declared: " + name + " with return type: " + returnType);
//    }
}
