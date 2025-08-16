package AST.Nodes;

import Util.Environment;

public abstract class Expression extends ASTNode {
    // this is getting the variable from the lookup table (which we call env)
    public abstract double evaluate(Environment env);
    public abstract String toString();
}
