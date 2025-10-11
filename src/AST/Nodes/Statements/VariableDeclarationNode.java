package AST.Nodes.Statements;

import AST.Nodes.Conditional.BooleanNode;
import AST.Nodes.DataStructures.Matrix;
import AST.Nodes.DataTypes.Constant;
import AST.Nodes.DataTypes.FloatConstant;
import AST.Nodes.Expressions.Expression;
import AST.Nodes.Expressions.StringNode;
import AST.Nodes.Expressions.VariableNode;
import Util.ErrorHandler;
import Interpreter.Tokenizer.Token;
import Interpreter.Runtime.Environment;
import Interpreter.Parser.VariableSymbol;
import Util.WarningLogger;

public class VariableDeclarationNode extends Statement {
    private final Token type;
    private final Token variable;
    private final Expression initializer;
    private final WarningLogger warningLogger = new WarningLogger();

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
        // we might want to check if we have a function call
        Expression value = (initializer != null) ? initializer.evaluate(env) : null;

        switch (type.getKind()) {
            case MATH:
                if (value instanceof StringNode) {
                    throw new ErrorHandler("execution", variable.getLine(), "Type mismatch: expected math, got string", "Please ensure the initializer is a math expression.");
                    //throw new RuntimeException("Type mismatch: expected math, got string at line " + variable.getLine());
                }
                value = new VariableNode(variable.getLexeme());
                break;
            case FLOAT:
                if (value instanceof Constant v && v.getValue() instanceof Integer) {
                    // convert integer to float if needed -- but throw warning somehow...
                    value = new FloatConstant(v.getDoubleValue());
                    warningLogger.addWarning(1, "Implicit conversion from integer to float at line " + variable.getLine(), variable.getLine());
                } else if (!(value instanceof Constant v && v.getValue() instanceof Float)) {
                    throw new ErrorHandler("execution", variable.getLine(), "Type mismatch: expected float, got " + (value != null ? value.getClass().getSimpleName() : "null"), "Please ensure the initializer is a float.");
                    //throw new RuntimeException("Type mismatch: expected float, got " + (value != null ? value.getClass().getSimpleName() : "null") + " at line " + variable.getLine());
                }
                break;
            case INTEGER:
                if (!(value instanceof Constant v && v.getValue() instanceof Integer)) {
                    throw new ErrorHandler("execution", variable.getLine(), "Type mismatch: expected integer, got " + (value != null ? value.getClass().getSimpleName() : "null"), "Please ensure the initializer is an integer.");
                    //throw new RuntimeException("Type mismatch: expected integer, got " + (value != null ? value.getClass().getSimpleName() : "null") + " at line " + variable.getLine());
                }
                break;
            case STRING:
                if (!(value instanceof StringNode)) {
                    throw new ErrorHandler("execution", variable.getLine(), "Type mismatch: expected string, got " + (value != null ? value.getClass().getSimpleName() : "null"), " Please ensure the initializer is a string.");
                    //throw new RuntimeException("Type mismatch: expected string, got " + (value != null ? value.getClass().getSimpleName() : "null") + " at line " + variable.getLine());
                }
                break;
            case BOOLEAN:
                if (!(value instanceof BooleanNode)) {
                    throw new ErrorHandler("execution", variable.getLine(), "Type mismatch: expected boolean, got " + (value != null ? value.getClass().getSimpleName() : "null"), "Please ensure the initializer is a boolean.");
                    //throw new RuntimeException("Type mismatch: expected boolean, got " + (value != null ? value.getClass().getSimpleName() : "null") + " at line " + variable.getLine());
                }
                break;
            case MATRIX:
                Matrix v = (Matrix) value;
                if (v.isEmpty()) {
                    warningLogger.addWarning(2, "Initialized matrix is empty at line " + variable.getLine(), variable.getLine());
                }
                if (v == null) {
                    throw new ErrorHandler("execution", variable.getLine(), "Type mismatch: expected matrix, got " + (value != null ? value.getClass().getSimpleName() : "null"), "Please ensure the initializer is a matrix.");
                    //throw new RuntimeException("Type mismatch: expected vector, got " + (value != null ? value.getClass().getSimpleName() : "null") + " at line " + variable.getLine());
                }
                break;
        }
        env.declareSymbol(variable.getLexeme(), new VariableSymbol(variable.getLexeme(), type.getKind(), value));
    }

    // will work on this in a later build, it's not necessary yet
    // we just need to log them all in a separate file if the debugger is active
    public void printWarnings() {
        warningLogger.printWarnings();
    }
}
