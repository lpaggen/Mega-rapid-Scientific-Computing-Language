package AST.Nodes.Statements;

import AST.Nodes.ASTNode;
import Interpreter.Runtime.Environment;

public abstract class Statement extends ASTNode {
    public abstract void execute(Environment env);
}
