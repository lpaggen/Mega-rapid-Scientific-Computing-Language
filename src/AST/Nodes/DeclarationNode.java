package AST.Nodes;

import Interpreter.ErrorHandler;
import Interpreter.Tokenizer.Token;
import Util.LookupTable;

public class DeclarationNode extends Statement {
    private final Token type;
    private final Token variable;
    private final Expression initializer;

    public DeclarationNode(Token type, Token variable, Expression initializer) {
        this.type = type;
        this.variable = variable;
        this.initializer = initializer;
    }

    // IMPORTANT
    // this node should be the one catching type mismatches
    // it's super easy really, we check the literal, and the type of the variable
    // if there's a mismatch, we crash the program OR we are nice and tolerate small mistakes, will see!
    @Override
    public void execute(LookupTable<String, Token> env) {
        Object value = (initializer != null) ? initializer.evaluate(env) : null;

        switch (type.getKind()) {
            case FLOAT:
                if (!(value instanceof Float)) {
                    throw new ErrorHandler("execution", variable.getLine(), "Type mismatch: expected float, got " + (value != null ? value.getClass().getSimpleName() : "null"), "Please ensure the initializer is a float.");
                    //throw new RuntimeException("Type mismatch: expected float, got " + (value != null ? value.getClass().getSimpleName() : "null") + " at line " + variable.getLine());
                }
                break;
            case INTEGER:
                if (!(value instanceof Integer)) {
                    throw new ErrorHandler("execution", variable.getLine(), "Type mismatch: expected integer, got " + (value != null ? value.getClass().getSimpleName() : "null"), "Please ensure the initializer is an integer.");
                    //throw new RuntimeException("Type mismatch: expected integer, got " + (value != null ? value.getClass().getSimpleName() : "null") + " at line " + variable.getLine());
                }
                break;
        }

        env.setValue(variable.getLexeme(), new Token(type.getKind(), variable.getLexeme(), value, variable.getLine()));
        env.showLookupTable();
    }
}
