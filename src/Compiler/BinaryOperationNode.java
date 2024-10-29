package Compiler;

// this class represents anything with + and -, * operators etc
public class BinaryOperationNode extends ASTNode {

    public ASTNode left;
    public ASTNode right;
    public Token operator;

    public BinaryOperationNode(ASTNode left, ASTNode right, Token operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    public String toString() {
        return left.toString() + " " + operator.toString() + " " + left.toString();
    }
}
