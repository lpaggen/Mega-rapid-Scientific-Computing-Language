package AST.Nodes.DataStructures;

import AST.Nodes.Conditional.BooleanNode;
import AST.Nodes.Constant;
import AST.Nodes.Expression;

// moved everything to my AST wrapper types, will see how this goes
// except toString, but i can just add some kind of toStringNode or something
public interface ListLike extends Iterable<Expression> {
    Constant size();
    Expression get(int index);
    Expression[] getElements();
    ListLike add(Expression element);
    ListLike subtract(Expression element);
    ListLike multiply(Expression element);
    ListLike divide(Expression element);
    BooleanNode isEmpty();
    String toString();
    BooleanNode contains(Expression element);
}
