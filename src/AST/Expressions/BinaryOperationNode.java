package AST.Expressions;

import AST.Nodes.ASTNode;
import Compiler.Tokenizer.Token;

// this class represents anything with + and -, * operators etc.
// to be reviewed at later stage
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
        return "(" + left.toString() + " " + operator + " " + right.toString() + ")";
    }

    public Token getOperator() {
        return operator;
    }

    public ASTNode getLeft() {
        return left;
    }

    public ASTNode getRight() {
        return right;
    }
}
