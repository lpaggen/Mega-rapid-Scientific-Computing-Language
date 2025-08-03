package AST.Nodes;

import Interpreter.ErrorHandler;
import Interpreter.Tokenizer.Token;
import Util.Environment;
import Util.VariableSymbol;
import Util.WarningHandler;

public class VariableDeclarationNode extends Statement {
    private final Token type;
    private final Token variable;
    private final Expression initializer;
    private final WarningHandler warningHandler = new WarningHandler();

    public VariableDeclarationNode(Token type, Token variable, Expression initializer) {
        this.type = type;
        this.variable = variable;
        this.initializer = initializer;
    }

    // IMPORTANT
    // this node should be the one catching type mismatches
    // it's super easy really, we check the literal, and the type of the variable
    // if there's a mismatch, we crash the program OR we are nice and tolerate small mistakes, will see!
    @Override
    public void execute(Environment env) {
        Object value = (initializer != null) ? initializer.evaluate(env) : null;

        switch (type.getKind()) {
            case FLOAT:
                if (value instanceof Integer) {
                    // convert integer to float if needed -- but throw warning somehow...
                    value = ((Integer) value).floatValue();
                    warningHandler.addWarning(1, "Implicit conversion from integer to float at line " + variable.getLine(), variable.getLine());
                } else if (!(value instanceof Float) && !(value instanceof Double)) {
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
            case STRING:
                if (!(value instanceof String)) {
                    throw new ErrorHandler("execution", variable.getLine(), "Type mismatch: expected string, got " + (value != null ? value.getClass().getSimpleName() : "null"), "Please ensure the initializer is a string.");
                    //throw new RuntimeException("Type mismatch: expected string, got " + (value != null ? value.getClass().getSimpleName() : "null") + " at line " + variable.getLine());
                }
                break;
        }

        env.declareSymbol(variable.getLexeme(), new VariableSymbol(variable.getLexeme(), type.getKind(), value));
    }

    // will work on this in a later build, it's not necessary yet
    public void printWarnings() {
        warningHandler.printWarnings();
    }
}
