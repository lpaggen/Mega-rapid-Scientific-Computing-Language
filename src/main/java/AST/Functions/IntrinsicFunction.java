package AST.Functions;

import AST.Type;
import Semantic.ConstraintStore;

public sealed interface IntrinsicFunction permits LinalgInverseFunction, LinalgTransposeFunction {
    Type apply(Type type, ConstraintStore constraintStore);
}
