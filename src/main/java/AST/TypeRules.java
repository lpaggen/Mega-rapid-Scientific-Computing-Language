package AST;

import Semantic.ConstraintStore;


/**
    * This class contains the type inference rules for the language. It is used by the TypeChecker to infer the types of expressions and statements.
 */
public final class TypeRules {
    private final ConstraintStore constraints;
    public TypeRules(ConstraintStore constraints) {
        this.constraints = constraints;
    }
    public Type inferMultiplication(TypedExpression left, TypedExpression right) {
        if (left.type().typeInterface() instanceof MatrixType A &&
                right.type().typeInterface() instanceof MatrixType B) {
            constraints.addEqualityConstraint(
                    A.cols(),
                    B.rows()
            );
            return new Type(
                    new MatrixType(
                            A.entryDataType(),
                            A.rows(),
                            B.cols()
                    ),
                    new TypeAttributes(false, false)
            );
        }
        throw new RuntimeException("Invalid types for multiplication: " + left.type() + " and " + right.type());
    }

    public Type inferDivision(TypedExpression leftType, TypedExpression rightType) {
        if (leftType.type().typeInterface() instanceof MatrixType A &&
                rightType.type().typeInterface() instanceof MatrixType B) {
            constraints.addEqualityConstraint(
                    A.cols(),
                    B.rows()
            );
            return new Type(
                    new MatrixType(
                            A.entryDataType(),
                            A.rows(),
                            B.cols()
                    ),
                    new TypeAttributes(false, false)
            );
        }
        throw new RuntimeException("Invalid types for division: " + leftType.type() + " and " + rightType.type());
    }

    public Type inferAddition(TypedExpression leftType, TypedExpression rightType) {
        if (leftType.type().typeInterface() instanceof MatrixType A &&
                rightType.type().typeInterface() instanceof MatrixType B) {
            constraints.addEqualityConstraint(
                    A.rows(),
                    B.rows()
            );
            constraints.addEqualityConstraint(
                    A.cols(),
                    B.cols()
            );
            return new Type(
                    new MatrixType(
                            A.entryDataType(),
                            A.rows(),
                            A.cols()
                    ),
                    new TypeAttributes(false, false)
            );
        }
        throw new RuntimeException("Invalid types for addition: " + leftType.type() + " and " + rightType.type());
    }

    public Type inferSubtraction(TypedExpression leftType, TypedExpression rightType) {
        if (leftType.type().typeInterface() instanceof MatrixType A &&
                rightType.type().typeInterface() instanceof MatrixType B) {
            constraints.addEqualityConstraint(
                    A.rows(),
                    B.rows()
            );
            constraints.addEqualityConstraint(
                    A.cols(),
                    B.cols()
            );
            return new Type(
                    new MatrixType(
                            A.entryDataType(),
                            A.rows(),
                            A.cols()
                    ),
                    new TypeAttributes(false, false)
            );
        }
        throw new RuntimeException("Invalid types for subtraction: " + leftType.type() + " and " + rightType.type());
    }
}
