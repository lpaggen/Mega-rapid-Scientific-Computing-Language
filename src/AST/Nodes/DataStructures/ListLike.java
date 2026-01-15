package AST.Nodes.DataStructures;

import AST.Nodes.Expressions.Expression;
import Lexer.TokenKind;

import java.util.ArrayList;


public interface ListLike extends SequenceLike {
    ArrayList<Expression> getElements();
    ListLike filter(Expression element);
    ListLike map(Expression element);                     // takes function, applies to each element
    ListLike reduce(Expression element);                  // takes function, returns single element based on user defined function
    ListLike slice(int start, int end);                   // like python, end exclusive
    ListLike concat(ListLike other);                      // like python +
    ListLike reverse();
    ListLike sort();                                      // most likely just implement Java's default sort
    ListLike unique();
    ListLike flatten();                                    // for nested lists, flattens to single list
    ListLike distinct();                                  // removes duplicates, keeps order
    ListLike copy();                                      // deep copy
    ListLike extend(ListLike other);                      // like python extend
    ListLike clear();
    TokenKind getType();
    ListLike insert(int index, Expression element);
    ListLike remove(int index);
    int indexOf(Expression element);  // idk, maybe this would be Constant, will see how it goes
    int lastIndexOf(Expression element);
    boolean equals(Object o);
}
