package AST.Nodes;

import Interpreter.Runtime.Environment;

public abstract class Statement extends ASTNode {
    public abstract void execute(Environment env);
}
