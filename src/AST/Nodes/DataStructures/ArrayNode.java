package AST.Nodes.DataStructures;

import AST.Nodes.Constant;
import AST.Nodes.Expression;
import Interpreter.Runtime.Environment;
import Interpreter.Tokenizer.Token;
import Interpreter.Tokenizer.TokenKind;
import Util.WarningLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


// this one is going to change to ArrayList later on
public class ArrayNode extends Expression {
    private final Expression[] elements;
    private TokenKind type; // The type of elements in the array
    private static final Set<TokenKind> supportedTypes = Set.of(
            TokenKind.INTEGER,
            TokenKind.FLOAT,
            TokenKind.BOOLEAN,
            TokenKind.STRING
    );

    static private WarningLogger warningLogger = new WarningLogger();

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
                throw new RuntimeException("Array type mismatch: expected "
                        + expectedType + ", got " + evaluated.getType(env)
                        + " in element " + evaluated + " at position " + position);
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

    // a lot of this is boilerplate, will clean it up eventually in later build
    public static ArrayNode add(Expression left, Expression right) {
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
            return new ArrayNode(new Expression[]{Constant.add(leftScalar, rightScalar)}, TokenKind.INTEGER);
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

        return new ArrayNode(result, type);
    }

    public static ArrayNode mul(Expression left, Expression right) {
        Constant leftScalar = left instanceof Constant ? (Constant) left : null;
        Constant rightScalar = right instanceof Constant ? (Constant) right : null;

        ArrayNode leftArray = left instanceof ArrayNode ? (ArrayNode) left : null;
        ArrayNode rightArray = right instanceof ArrayNode ? (ArrayNode) right : null;

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
            return new ArrayNode(new Expression[]{Constant.multiply(leftScalar, rightScalar)}, TokenKind.INTEGER);
        }

        if (type != TokenKind.INTEGER && type != TokenKind.FLOAT) {
            throw new RuntimeException("Operands must be INTEGER or FLOAT");
        }

        Expression[] result = new Expression[length];
        for (int i = 0; i < length; i++) {
            Constant leftElement = leftArray != null ? (Constant) leftArray.getElement(i) : leftScalar;
            Constant rightElement = rightArray != null ? (Constant) rightArray.getElement(i) : rightScalar;
            result[i] = Constant.multiply(leftElement, rightElement); // this could all be streamlined quite easily, maybe later
        }

        return new ArrayNode(result, type);
    }

    public static ArrayNode div(Expression left, Expression right) {
        Constant leftScalar = left instanceof Constant ? (Constant) left : null;
        Constant rightScalar = right instanceof Constant ? (Constant) right : null;

        ArrayNode leftArray = left instanceof ArrayNode ? (ArrayNode) left : null;
        ArrayNode rightArray = right instanceof ArrayNode ? (ArrayNode) right : null;

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
            return new ArrayNode(new Expression[]{Constant.divide(leftScalar, rightScalar)}, TokenKind.INTEGER);
        }

        if (type != TokenKind.INTEGER && type != TokenKind.FLOAT) {
            throw new RuntimeException("Operands must be INTEGER or FLOAT");
        }

        Expression[] result = new Expression[length];
        for (int i = 0; i < length; i++) {
            Constant leftElement = leftArray != null ? (Constant) leftArray.getElement(i) : leftScalar;
            Constant rightElement = rightArray != null ? (Constant) rightArray.getElement(i) : rightScalar;
            result[i] = Constant.divide(leftElement, rightElement);
        }

        return new ArrayNode(result, type);
    }

    public static ArrayNode sub(Expression left, Expression right) {
        Constant leftScalar = left instanceof Constant ? (Constant) left : null;
        Constant rightScalar = right instanceof Constant ? (Constant) right : null;

        ArrayNode leftArray = left instanceof ArrayNode ? (ArrayNode) left : null;
        ArrayNode rightArray = right instanceof ArrayNode ? (ArrayNode) right : null;

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
            return new ArrayNode(new Expression[]{Constant.subtract(leftScalar, rightScalar)}, TokenKind.INTEGER);
        }

        if (type != TokenKind.INTEGER && type != TokenKind.FLOAT) {
            throw new RuntimeException("Operands must be INTEGER or FLOAT");
        }

        Expression[] result = new Expression[length];
        for (int i = 0; i < length; i++) {
            Constant leftElement = leftArray != null ? (Constant) leftArray.getElement(i) : leftScalar;
            Constant rightElement = rightArray != null ? (Constant) rightArray.getElement(i) : rightScalar;
            result[i] = Constant.subtract(leftElement, rightElement);
        }

        return new ArrayNode(result, type);
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
