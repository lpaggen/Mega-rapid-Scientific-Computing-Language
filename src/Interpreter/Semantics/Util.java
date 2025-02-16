package Interpreter.Semantics;

import AST.Nodes.ASTNode;
import Interpreter.Tokenizer.Token;

// util class (rename later) handles all the transformation from generic nodes to their proper AST nodes
// so a binary node can become a sum or negation etc
// we should work on this at some stage.
public class Util { // have to see how to format all this
    private ASTNode left;
    private ASTNode right;
    private Token operator;
}
