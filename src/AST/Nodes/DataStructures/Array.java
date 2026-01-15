package AST.Nodes.DataStructures;

import AST.Nodes.Expressions.Expression;
import Runtime.Environment;
import Lexer.TokenKind;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;


// this one is going to change to ArrayList later on
public class Array extends Expression implements ListLike {
    private final ArrayList<Expression> elements;
    private final TokenKind type; // The type of elements in the array
    private static final Set<TokenKind> supportedTypes = Set.of(
            TokenKind.SCALAR,
            TokenKind.BOOLEAN,
            TokenKind.STRING
    );

    // static private WarningLogger warningLogger = new WarningLogger();

    public Array(ArrayList<Expression> elements, TokenKind type) {
        this.elements = elements;
        this.type = type;
    }

    private boolean isValidIndex(int index) {
        return index >= 0 && index < elements.size();
    }

    @Override
    public TokenKind getType() {
        return type;
    }

    @Override
    public Expression get(int index) {
        if (!isValidIndex(index)) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for array of size " + elements.size());
        }
        return elements.get(index);
    }

    @Override
    public void set(int index, Expression value) {
        if (!isValidIndex(index)) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for array of size " + elements.size());
        }
        elements.set(index, value);
    }

    @Override
    public void forEach(Consumer<? super Expression> action) {

    }

    @Override
    public ArrayList<Expression> getElements() {
        return elements;
    }

    @Override
    public Expression evaluate(Environment env) {
        ArrayList<Expression> evaluatedElements = new ArrayList<>();
        TokenKind expectedType = type;
        int position = 0;
        for (Expression elem : this) {
            Expression evaluated = elem.evaluate(env);
            if (expectedType == null) {
                expectedType = evaluated.getType(env);
            } else if (evaluated.getType(env) != expectedType) {
                throw new RuntimeException("Array type mismatch: expected "
                        + expectedType + ", got " + evaluated.getType(env)
                        + " in element " + evaluated + " at position " + position);
            }
            evaluatedElements.add(evaluated);
            position++;
        }
        return new Array(evaluatedElements, expectedType);
    }

    public int length() {
        return elements.size();
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    public boolean contains(Expression value) {
        for (Expression element : this) {
            if (element.equals(value)) {
                return true;
            }
        }
        return false;
    }

    public int indexOf(Expression value) {
        int counter = 0;
        for (Expression element : this) {
            if (element.equals(value)) {
                return counter;
            }
            counter++;
        }
        return -1; // Not found
    }

    @Override
    public int lastIndexOf(Expression element) {
        return 0;
    }

    public boolean allMatch(Expression value) {
        for (Expression element : this) {
            if (!element.equals(value)) {
                return false;
            }
        }
        return true;
    }

    public boolean noneMatch(Expression value) {
        for (Expression element : this) {
            if (element.equals(value)) {
                return false;
            }
        }
        return true;
    }

    public int countMatches(Expression value) {
        int count = 0;
        for (Expression element : this) {
            if (element.equals(value)) {
                count++;
            }
        }
        return count;
    }

    public Array filter(Expression value) {
        ArrayList<Expression> filteredElements = new ArrayList<>();
        for (Expression element : this) {
            if (element.equals(value)) {
                filteredElements.add(element);
            }
        }
        return new Array(filteredElements, type);
    }

    @Override
    public ListLike map(Expression element) {
        return null;
    }

    @Override
    public ListLike reduce(Expression element) {
        return null;
    }

    @Override
    public ListLike slice(int start, int end) {
        return null;
    }

    @Override
    public ListLike concat(ListLike other) {
        return null;
    }

    @Override
    public ListLike reverse() {
        return null;
    }

    @Override
    public ListLike sort() {
        return null;
    }

    @Override
    public ListLike unique() {
        return null;
    }

    @Override
    public ListLike flatten() {
        return null;
    }

    @Override
    public ListLike distinct() {
        return null;
    }

    @Override
    public ListLike copy() {
        return null;
    }

    @Override
    public ListLike extend(ListLike other) {
        return null;
    }

    @Override
    public ListLike clear() {
        return null;
    }

    @Override
    public ListLike insert(int index, Expression element) {
        return null;
    }

    @Override
    public ListLike remove(int index) {
        return null;
    }

    // a lot of this is boilerplate, will clean it up eventually in later build
    public static Array add(Expression left, Expression right) {
        ArrayList<Expression> result = new ArrayList<>();
        Scalar leftScalar = left instanceof Scalar ? (Scalar) left : null;
        Scalar rightScalar = right instanceof Scalar ? (Scalar) right : null;

        Array leftArray = left instanceof Array ? (Array) left : null;
        Array rightArray = right instanceof Array ? (Array) right : null;

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
            result.add(Scalar.add(leftScalar, rightScalar));
            TokenKind returnType = TokenKind.SCALAR;
            return new Array(result, returnType);
        }

        if (type != TokenKind.SCALAR) {
            throw new RuntimeException("Operands must be SCALAR");
        }

        for (int i = 0; i < length; i++) {
            Scalar leftElement = leftArray != null ? (Scalar) leftArray.get(i) : leftScalar;
            Scalar rightElement = rightArray != null ? (Scalar) rightArray.get(i) : rightScalar;
            result.add(Scalar.add(leftElement, rightElement));
        }

        return new Array(result, type);
    }

    public static Array mul(Expression left, Expression right) {
        ArrayList<Expression> result = new ArrayList<>();
        Scalar leftScalar = left instanceof Scalar ? (Scalar) left : null;
        Scalar rightScalar = right instanceof Scalar ? (Scalar) right : null;

        Array leftArray = left instanceof Array ? (Array) left : null;
        Array rightArray = right instanceof Array ? (Array) right : null;

        int length = 1;
        TokenKind type = null;
        // now we check the combinations
        if (leftArray != null) { // if the left array is NOT null, it means it's an array else it's a scalar
            length = leftArray.length();
            type = leftArray.getType();
        } else if (rightArray != null) {
            length = rightArray.length();
            type = rightArray.getType();
        } else {
            // both are scalars, we can just add them -- atm hardcoded to INTEGER, will figure out a fix at some point
            result.add(Scalar.multiply(leftScalar, rightScalar));
            TokenKind returnType = TokenKind.SCALAR;
            return new Array(result, returnType);
        }

        if (type != TokenKind.SCALAR) {
            throw new RuntimeException("Operands must be SCALAR");
        }

        for (int i = 0; i < length; i++) {
            Scalar leftElement = leftArray != null ? (Scalar) leftArray.get(i) : leftScalar;
            Scalar rightElement = rightArray != null ? (Scalar) rightArray.get(i) : rightScalar;
            result.add(Scalar.multiply(leftElement, rightElement)); // this could all be streamlined quite easily, maybe later
        }

        return new Array(result, type);
    }

    public static Array div(Expression left, Expression right) {
        ArrayList<Expression> result = new ArrayList<>();
        Scalar leftScalar = left instanceof Scalar ? (Scalar) left : null;
        Scalar rightScalar = right instanceof Scalar ? (Scalar) right : null;

        Array leftArray = left instanceof Array ? (Array) left : null;
        Array rightArray = right instanceof Array ? (Array) right : null;

        int length = 1;
        TokenKind type = null;
        // now we check the combinations
        if (leftArray != null) { // if the left array is NOT null, it means it's an array else it's a scalar
            length = leftArray.length();
            type = leftArray.getType();
        } else if (rightArray != null) {
            length = rightArray.length();
            type = rightArray.getType();
        } else {
            // both are scalars, we can just add them -- atm hardcoded to INTEGER, will figure out a fix at some point
            result.add(Scalar.divide(leftScalar, rightScalar));
            TokenKind returnType = TokenKind.SCALAR;
            return new Array(result, returnType);
        }

        if (type != TokenKind.SCALAR) {
            throw new RuntimeException("Operands must be SCALAR");
        }

        for (int i = 0; i < length; i++) {
            Scalar leftElement = leftArray != null ? (Scalar) leftArray.get(i) : leftScalar;
            Scalar rightElement = rightArray != null ? (Scalar) rightArray.get(i) : rightScalar;
            result.add(Scalar.divide(leftElement, rightElement)); // this could all be streamlined quite easily, maybe later
        }

        return new Array(result, type);
    }

    public static Array sub(Expression left, Expression right) {
        ArrayList<Expression> result = new ArrayList<>();
        Scalar leftScalar = left instanceof Scalar ? (Scalar) left : null;
        Scalar rightScalar = right instanceof Scalar ? (Scalar) right : null;

        Array leftArray = left instanceof Array ? (Array) left : null;
        Array rightArray = right instanceof Array ? (Array) right : null;

        int length = 1;
        TokenKind type = null;
        // now we check the combinations
        if (leftArray != null) { // if the left array is NOT null, it means it's an array else it's a scalar
            length = leftArray.length();
            type = leftArray.getType();
        } else if (rightArray != null) {
            length = rightArray.length();
            type = rightArray.getType();
        } else {
            // both are scalars, we can just add them -- atm hardcoded to INTEGER, will figure out a fix at some point
            result.add(Scalar.subtract(leftScalar, rightScalar));
            TokenKind returnType = TokenKind.SCALAR;
            return new Array(result, returnType);
        }

        if (type != TokenKind.SCALAR) {
            throw new RuntimeException("Operands must be SCALAR");
        }

        for (int i = 0; i < length; i++) {
            Scalar leftElement = leftArray != null ? (Scalar) leftArray.get(i) : leftScalar;
            Scalar rightElement = rightArray != null ? (Scalar) rightArray.get(i) : rightScalar;
            result.add(Scalar.subtract(leftElement, rightElement)); // this could all be streamlined quite easily, maybe later
        }

        return new Array(result, type);

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < elements.size(); i++) {
            sb.append(elements.get(i).toString());
            if (i < elements.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public Iterator<Expression> iterator() {
        return elements.iterator();
    }
}
