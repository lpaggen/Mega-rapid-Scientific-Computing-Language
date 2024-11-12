package AST.Expressions;

import AST.Nodes.ASTNode;

public class FunctionNode extends ASTNode {

    public String functionName;
    public ASTNode argument; // MUST be able to take in other functions as arguments

    public FunctionNode(String functionName, ASTNode argument) {
        this.functionName = functionName;
        this.argument = argument; // however, arguments can be multiple at once... will need to fix this at some stage
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
