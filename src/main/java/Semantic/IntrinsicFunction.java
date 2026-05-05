package Semantic;

import AST.Type;

public sealed interface IntrinsicFunction permits LinalgInverseFunction {
    Type apply(Type type, ConstraintStore constraintStore);
}
