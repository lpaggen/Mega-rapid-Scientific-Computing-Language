package AST.Nodes.DataStructures;

import AST.Nodes.Constant;
import AST.Nodes.Expression;
import Interpreter.Runtime.Environment;
import Interpreter.Tokenizer.TokenKind;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Vector extends Expression implements ArrayLike {
    private final Expression[] elements;

    public Vector(Expression[] elements, TokenKind type) {
        this.elements = elements;
    }

    @Override
    public Expression evaluate(Environment env) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Expression get(int index) {
        return null;
    }

    @Override
    public Expression[] getElements() {
        return new Expression[0];
    }

    @Override
    public ArrayList<Expression> toList() {
        return null;
    }

    @Override
    public ArrayLike add(Expression element) {
        return null;
    }

    @Override
    public ArrayLike subtract(Expression element) {
        return null;
    }

    @Override
    public ArrayLike multiply(Expression element) {
        return null;
    }

    @Override
    public ArrayLike divide(Expression element) {
        return null;
    }

    @Override
    public ArrayLike mod(Expression element) {
        return null;
    }

    @Override
    public ArrayLike pow(Expression element) {
        return null;
    }

    @Override
    public ArrayLike concat(ArrayLike other) {
        return null;
    }

    @Override
    public boolean contains(Expression element) {
        return false;
    }

    @Override
    public int indexOf(Expression element) {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public String toString() {
        return null;
    }

    public static Vector add(Expression left, Expression right) {
        Constant leftScalar = left instanceof Constant ? (Constant) left : null;
        Constant rightScalar = right instanceof Constant ? (Constant) right : null;

        ArrayNode leftArray = left instanceof ArrayNode ? (ArrayNode) left : null;
        ArrayNode rightArray = right instanceof ArrayNode ? (ArrayNode) right : null;

        int length;
        TokenKind type;
        // now we check the combinations
        if (leftArray != null) { // if the left array is NOT null, it means it's an array else it's a scalar
            length = leftArray.length();
            type = leftArray.getType();
        } else if (rightArray != null) {
            length = rightArray.length();
            type = rightArray.getType();
        } else {
            // both are scalars, we can just add them -- atm hardcoded to INTEGER, will figure out a fix at some point
            return new Vector(new Expression[]{Constant.add(leftScalar, rightScalar)}, TokenKind.INTEGER);
        }

        if (type != TokenKind.INTEGER && type != TokenKind.FLOAT) {
            throw new RuntimeException("Operands must be INTEGER or FLOAT");
        }

        Expression[] result = new Expression[length];
        for (int i = 0; i < length; i++) {
            Constant leftElement = leftArray != null ? (Constant) leftArray.getElement(i) : leftScalar;
            Constant rightElement = rightArray != null ? (Constant) rightArray.getElement(i) : rightScalar;
            result[i] = Constant.add(leftElement, rightElement);
        }

        return new Vector(result, type);
    }

    @Override
    public Iterator<Expression> iterator() {
        return null;
    }

    @Override
    public void forEach(Consumer<? super Expression> action) {
        ArrayLike.super.forEach(action);
    }

    @Override
    public Spliterator<Expression> spliterator() {
        return ArrayLike.super.spliterator();
    }
}
