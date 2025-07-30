package AST.Nodes;

import Util.Environment;

public abstract class Statement extends ASTNode {
    public abstract void execute(Environment env);
}
