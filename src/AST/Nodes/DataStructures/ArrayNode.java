package AST.Nodes.DataStructures;

import AST.Nodes.Constant;
import AST.Nodes.Expression;
import Interpreter.Runtime.Environment;

// this is the classic static Array
// it's just beautiful because it's static and doesn't change size
public class ArrayNode extends Expression {
    private final Expression[] elements;

    public ArrayNode(Expression[] elements, Constant size) {
        if ((int) size.getValue() != elements.length) {
            throw new IllegalArgumentException("Array size does not match the number of elements. Expected size: " + size.getValue() + ", but got: " + elements.length);
        }
        this.elements = elements;
    }

    private boolean isValidIndex(int index) {
        return index >= 0 && index < elements.length;
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
        return this; // Arrays evaluate to themselves
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
