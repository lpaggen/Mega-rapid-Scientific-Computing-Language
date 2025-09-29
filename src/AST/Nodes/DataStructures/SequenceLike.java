package AST.Nodes.DataStructures;

import AST.Nodes.Expression;
import Interpreter.Tokenizer.TokenKind;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

// this just needs to have the basic methods that both Array and Vector need, nothing more
// this class is the superclass for Array and Vector, it defines the common methods
public interface SequenceLike extends Iterable<Expression> {
    int length();
    Expression get(int index);
    void set(int index, Expression element);
    boolean isEmpty();
    String toString();
    boolean equals(Object o);
    int hashCode();
    TokenKind getType();
}
