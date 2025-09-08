package AST.Nodes.DataStructures;

import AST.Nodes.Constant;
import AST.Nodes.Expression;
import Interpreter.Runtime.Environment;
import Interpreter.Tokenizer.TokenKind;

import java.util.ArrayList;
import java.util.List;


public class ArrayNode extends Expression {
    private final Expression[] elements;
    private TokenKind type; // The type of elements in the array

    public ArrayNode(Expression[] elements, TokenKind type) {
        this.elements = elements;
        this.type = type;
    }

    private boolean isValidIndex(int index) {
        return index >= 0 && index < elements.length;
    }

    public TokenKind getType() {
        return type;
    }
    public Expression getElement(int index) {
        if (!isValidIndex(index)) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for array of size " + elements.length);
        }
        return elements[index];
    }

    public void setElement(int index, Expression value) {
        if (!isValidIndex(index)) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for array of size " + elements.length);
        }
        elements[index] = value;
    }

    public Expression[] getElements() {
        return elements;
    }

    @Override
    public Expression evaluate(Environment env) {
        List<Expression> evaluatedElements = new ArrayList<>();
        TokenKind expectedType = type;
        int position = 0;
        for (Expression elem : elements) {
            Expression evaluated = elem.evaluate(env);
            if (expectedType == null) {
                expectedType = evaluated.getType(env);
            } else if (evaluated.getType(env) != expectedType) {
                throw new RuntimeException("Array type mismatch: expected " + expectedType + ", got " + evaluated.getType(env) + " in element " + evaluated + " at position " + position);
            }
            evaluatedElements.add(evaluated);
            position++;
        }
        Expression[] innerContent = evaluatedElements.toArray(new Expression[0]);
        return new ArrayNode(innerContent, expectedType);
    }

    public int length() {
        return elements.length;
    }

    public boolean isEmpty() {
        return elements.length == 0;
    }

    public boolean contains(Expression value) {
        for (Expression element : elements) {
            if (element.equals(value)) {
                return true;
            }
        }
        return false;
    }

    public int indexOf(Expression value) {
        for (int i = 0; i < elements.length; i++) {
            if (elements[i].equals(value)) {
                return i;
            }
        }
        return -1; // Not found
    }

    public boolean allMatch(Expression value) {
        for (Expression element : elements) {
            if (!element.equals(value)) {
                return false;
            }
        }
        return true;
    }

    public boolean anyMatch(Expression value) {
        for (Expression element : elements) {
            if (element.equals(value)) {
                return true;
            }
        }
        return false;
    }

    public boolean noneMatch(Expression value) {
        for (Expression element : elements) {
            if (element.equals(value)) {
                return false;
            }
        }
        return true;
    }

    public int countMatches(Expression value) {
        int count = 0;
        for (Expression element : elements) {
            if (element.equals(value)) {
                count++;
            }
        }
        return count;
    }

    public ArrayNode filter(Expression value) {
        List<Expression> filteredElements = new ArrayList<>();
        for (Expression element : elements) {
            if (element.equals(value)) {
                filteredElements.add(element);
            }
        }
        return new ArrayNode(filteredElements.toArray(new Expression[0]), type);
    }

    // this could get more complex, for now just a demo to see if it does work or not
    // now to remember where this crap is called from
    public static ArrayNode add(ArrayNode left, ArrayNode right) {
        if (left.length() != right.length()) {
            throw new RuntimeException("Array size mismatch: left array size " + left.length() + ", right array size " + right.length());
        }
        if (left.getType() != right.getType()) {
            throw new RuntimeException("Array type mismatch: left array type " + left.getType() + ", right array type " + right.getType());
        }
        if (left.getType() != TokenKind.INTEGER && left.getType() != TokenKind.FLOAT) {
            throw new RuntimeException("Array type must be INTEGER or FLOAT for addition, got " + left.getType());
        }
        Expression[] result = new Expression[left.length()];
        for (int i = 0; i < left.length(); i++) {
            Constant leftElement = (Constant) left.getElement(i);
            Constant rightElement = (Constant) right.getElement(i);
            result[i] = Constant.add(leftElement, rightElement);
        }
        return new ArrayNode(result, left.getType());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < elements.length; i++) {
            sb.append(elements[i].toString());
            if (i < elements.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
