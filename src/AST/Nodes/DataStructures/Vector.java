package AST.Nodes.DataStructures;

import AST.Nodes.DataTypes.Constant;
import AST.Nodes.Expression;
import Interpreter.Runtime.Environment;
import Interpreter.Tokenizer.Token;
import Interpreter.Tokenizer.TokenKind;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class Vector extends Expression implements VectorLike {
    private final Expression[] elements;
    int numCols;
    int numRows;
    int length;
    private final TokenKind type;
    public Vector(Expression[] elements, TokenKind type) {
        this.elements = elements;
        this.type = type;
    }

    public static TokenKind[] allowedTypes = {TokenKind.INTEGER, TokenKind.FLOAT};  // extend this with the maths stuff, later

    @Override
    public Expression evaluate(Environment env) {
        Expression[] evaluatedElements = new Expression[elements.length];
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
            evaluatedElements[position] = evaluated;
            position++;
        }
        return new Vector(evaluatedElements, expectedType);
    }

    public static boolean checkType(TokenKind type) {
        for (TokenKind allowedType : allowedTypes) {
            if (allowedType == type) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int rows() {
        return 1;  // this always has 1 row, else matrix
    }

    @Override
    public int cols() {
        return elements.length;
    }

    @Override
    public VectorLike getColumns(int... col) {
        return null;
    }

    @Override
    public VectorLike getRows(int... row) {
        return null;
    }

    @Override
    public Expression[] getElements() {
        return new Expression[0];
    }

//    @Override
//    public VectorLike dot(VectorLike other) {
//        return null;
//    }
//
//    @Override
//    public VectorLike outer(Expression left, Expression right) {
//        return null;
//    }

//    @Override
//    public Vector add(Vector other) {
//        return null;
//    }
//
//    @Override
//    public Vector subtract(Vector other) {
//        return null;
//    }
//
//    @Override
//    public Vector multiply(Vector other) {
//        return null;
//    }
//
//    @Override
//    public Vector divide(Vector other) {
//        return null;
//    }
//
//    @Override
//    public Vector dot(Vector other) {
//        return null;
//    }
//
//    @Override
//    public Vector outer(Vector other) {
//        return null;
//    }

//    @Override
//    public Vector mod(Expression element) {
//        return null;
//    }

    @Override
    public Vector pow(Expression element) {
        return null;
    }

    @Override
    public VectorLike transpose() {
        return null;
    }

    @Override
    public int length() {
        return 0;
    }

    @Override
    public Expression get(int index) {
        return elements[index];
    }

    @Override
    public int[] shape() {
        return new int[length];
    }

    @Override
    public Expression get(int row, int col) {
        return null;
    }

    @Override
    public void set(int index, Expression element) {

    }

    @Override
    public boolean isEmpty() {
        return elements.length == 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < cols(); i++) {
            sb.append(elements[i].toString());
            if (i < cols() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }


    @Override
    public boolean contains(Expression element) {
        return false;
    }

    @Override
    public TokenKind getType() {
        return type;
    }

    public static Vector add(Expression left, Expression right) {
        Constant leftScalar = left instanceof Constant ? (Constant) left : null;
        Constant rightScalar = right instanceof Constant ? (Constant) right : null;

        Vector leftArray = left instanceof Vector ? (Vector) left : null;
        Vector rightArray = right instanceof Vector ? (Vector) right : null;

        int length;
        TokenKind type;
        // now we check the combinations
        if (leftArray != null) { // if the left array is NOT null, it means it's an array else it's a scalar
            length = leftArray.cols();
            type = leftArray.getType();
        } else if (rightArray != null) {
            length = rightArray.cols();
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
            Constant leftElement = leftArray != null ? (Constant) leftArray.get(i) : leftScalar;
            Constant rightElement = rightArray != null ? (Constant) rightArray.get(i) : rightScalar;
            result[i] = Constant.add(leftElement, rightElement);
        }

        return new Vector(result, type);
    }

    public static Vector sub(Expression left, Expression right) {
        Constant leftScalar = left instanceof Constant ? (Constant) left : null;
        Constant rightScalar = right instanceof Constant ? (Constant) right : null;

        Vector leftArray = left instanceof Vector ? (Vector) left : null;
        Vector rightArray = right instanceof Vector ? (Vector) right : null;

        int length;
        TokenKind type;
        // now we check the combinations
        if (leftArray != null) { // if the left array is NOT null, it means it's an array else it's a scalar
            length = leftArray.cols();
            type = leftArray.getType();
        } else if (rightArray != null) {
            length = rightArray.cols();
            type = rightArray.getType();
        } else {
            // both are scalars, we can just add them -- atm hardcoded to INTEGER, will figure out a fix at some point
            return new Vector(new Expression[]{Constant.subtract(leftScalar, rightScalar)}, TokenKind.INTEGER);
        }

        if (type != TokenKind.INTEGER && type != TokenKind.FLOAT) {
            throw new RuntimeException("Operands must be INTEGER or FLOAT");
        }

        Expression[] result = new Expression[length];
        for (int i = 0; i < length; i++) {
            Constant leftElement = leftArray != null ? (Constant) leftArray.get(i) : leftScalar;
            Constant rightElement = rightArray != null ? (Constant) rightArray.get(i) : rightScalar;
            result[i] = Constant.subtract(leftElement, rightElement);
        }

        return new Vector(result, type);
    }

    public static Vector div(Expression left, Expression right) {
        Constant leftScalar = left instanceof Constant ? (Constant) left : null;
        Constant rightScalar = right instanceof Constant ? (Constant) right : null;

        Vector leftArray = left instanceof Vector ? (Vector) left : null;
        Vector rightArray = right instanceof Vector ? (Vector) right : null;

        int length;
        TokenKind type;
        // now we check the combinations
        if (leftArray != null) { // if the left array is NOT null, it means it's an array else it's a scalar
            length = leftArray.cols();
            type = leftArray.getType();
        } else if (rightArray != null) {
            length = rightArray.cols();
            type = rightArray.getType();
        } else {
            // both are scalars, we can just add them -- atm hardcoded to INTEGER, will figure out a fix at some point
            return new Vector(new Expression[]{Constant.divide(leftScalar, rightScalar)}, TokenKind.INTEGER);
        }

        if (type != TokenKind.INTEGER && type != TokenKind.FLOAT) {
            throw new RuntimeException("Operands must be INTEGER or FLOAT");
        }

        Expression[] result = new Expression[length];
        for (int i = 0; i < length; i++) {
            Constant leftElement = leftArray != null ? (Constant) leftArray.get(i) : leftScalar;
            Constant rightElement = rightArray != null ? (Constant) rightArray.get(i) : rightScalar;
            result[i] = Constant.divide(leftElement, rightElement);
        }

        return new Vector(result, type);
    }

    @Override
    public VectorLike dot(Expression left, Expression right) {
        return null;
    }

    @Override
    public VectorLike outer(Expression left, Expression right) {
        return null;
    }

    public static Vector mul(Expression left, Expression right) {
        Constant leftScalar = left instanceof Constant ? (Constant) left : null;
        Constant rightScalar = right instanceof Constant ? (Constant) right : null;

        Vector leftArray = left instanceof Vector ? (Vector) left : null;
        Vector rightArray = right instanceof Vector ? (Vector) right : null;

        int length;
        TokenKind type;
        // now we check the combinations
        if (leftArray != null) { // if the left array is NOT null, it means it's an array else it's a scalar
            length = leftArray.cols();
            type = leftArray.getType();
        } else if (rightArray != null) {
            length = rightArray.cols();
            type = rightArray.getType();
        } else {
            // both are scalars, we can just add them -- atm hardcoded to INTEGER, will figure out a fix at some point
            return new Vector(new Expression[]{Constant.multiply(leftScalar, rightScalar)}, TokenKind.INTEGER);
        }

        if (type != TokenKind.INTEGER && type != TokenKind.FLOAT) {
            throw new RuntimeException("Operands must be INTEGER or FLOAT");
        }

        Expression[] result = new Expression[length];
        for (int i = 0; i < length; i++) {
            Constant leftElement = leftArray != null ? (Constant) leftArray.get(i) : leftScalar;
            Constant rightElement = rightArray != null ? (Constant) rightArray.get(i) : rightScalar;
            result[i] = Constant.multiply(leftElement, rightElement);
        }

        return new Vector(result, type);
    }

    @Override
    public Iterator<Expression> iterator() {
        return new Iterator<>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < cols();
            }

            @Override
            public Expression next() {
                return elements[currentIndex++];
            }
        };
    }
}
