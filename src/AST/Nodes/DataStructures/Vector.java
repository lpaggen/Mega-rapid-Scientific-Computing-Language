package AST.Nodes.DataStructures;

import AST.Nodes.DataTypes.Constant;
import AST.Nodes.Expression;
import Interpreter.Runtime.Environment;
import Interpreter.Tokenizer.TokenKind;

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map;

public class Vector extends Expression implements VectorLike {
    private final Expression[] elements;
    int numRows;
    int numCols;
    public Vector(Expression[] elements, TokenKind type) {
        this.elements = elements;
    }

    public static TokenKind[] allowedTypes = {TokenKind.INTEGER, TokenKind.FLOAT};  // extend this with the maths stuff, later

    @Override
    public Expression evaluate(Environment env) {
        return null;
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
        return elements.length;
    }

    @Override
    public int cols() {
        return elements.length;
    }

    @Override
    public Expression get(int row, int col) {
        return null;
    }

    @Override
    public VectorLike getColumns(int... col) {
        return null;
    }

    @Override
    public VectorLike getRows(int... row) {
        return null;
    }

//    @Override
//    public Expression getColumns(Constant... col) {
//        return null;
//    }
//
//    @Override
//    public Expression getRows(Constant... row) {
//        return null;
//    }

//    @Override
//    public Expression get(Constant index) {
//        return null;
//    }

    @Override
    public Expression[] getElements() {
        return new Expression[0];
    }

    @Override
    public VectorLike add(VectorLike other) {
        return null;
    }

    @Override
    public VectorLike sub(VectorLike other) {
        return null;
    }

    @Override
    public VectorLike mul(VectorLike other) {
        return null;
    }

    @Override
    public VectorLike div(VectorLike other) {
        return null;
    }

    @Override
    public VectorLike dot(VectorLike other) {
        return null;
    }

    @Override
    public VectorLike outer(VectorLike other) {
        return null;
    }

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
        return null;
    }

    @Override
    public int[][] shape() {
        return new int[numRows][numCols];
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
        return null;
    }

    @Override
    public boolean contains(Expression element) {
        return false;
    }

    @Override
    public TokenKind getType() {
        return null;
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
            Constant leftElement = leftArray != null ? (Constant) leftArray.get(i) : leftScalar;
            Constant rightElement = rightArray != null ? (Constant) rightArray.get(i) : rightScalar;
            result[i] = Constant.add(leftElement, rightElement);
        }

        return new Vector(result, type);
    }

    @Override
    public Iterator<Expression> iterator() {
        return new Iterator<Expression>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < elements.length;
            }

            @Override
            public Expression next() {
                return elements[currentIndex++];
            }
        };
    }
}
