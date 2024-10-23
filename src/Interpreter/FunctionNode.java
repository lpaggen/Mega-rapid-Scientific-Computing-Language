package Interpreter;

public class FunctionNode extends ASTNode {

    public String functionName;
    public ASTNode argument; // MUST be able to take in other functions as arguments

    public FunctionNode(String functionName, ASTNode argument) {
        this.functionName = functionName;
        this.argument = argument;
    }

    public String getFunctionName() {
        return functionName;
    }

    public ASTNode getArgument() { // this doesn't have a purpose. ?
        return argument;
    }

    public String toString() {
        return functionName + "(" + argument.toString() + ")";
    }
}
