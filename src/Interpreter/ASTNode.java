package Interpreter;

public abstract class ASTNode {

    public ASTNode left;
    public ASTNode right;
    public ASTNode operator;

    public ASTNode(ASTNode left, ASTNode right, ASTNode operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    public ASTNode(ASTNode left, ASTNode right) {
        this.left = left;
        this.right = right;
    }

    public ASTNode(ASTNode left) {
        this.left = left;
    }

    public ASTNode() {}

    public abstract String toString();
}
