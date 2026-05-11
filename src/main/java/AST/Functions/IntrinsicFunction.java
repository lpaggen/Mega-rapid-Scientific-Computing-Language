package AST.Functions;

import AST.Type;
import AST.TypedExpression;
import Semantic.ConstraintStore;

public sealed interface IntrinsicFunction permits LinalgInverseFunction, LinalgTransposeFunction {
    TypedExpression apply(TypedExpression type, ConstraintStore constraintStore);
}
