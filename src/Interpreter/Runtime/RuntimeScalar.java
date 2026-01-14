package Interpreter.Runtime;

public sealed interface RuntimeScalar extends RuntimeValue permits RuntimeInteger, RuntimeDouble {
    RuntimeScalar add(RuntimeScalar other);
    RuntimeScalar subtract(RuntimeScalar other);
    RuntimeScalar multiply(RuntimeScalar other);
    RuntimeScalar divide(RuntimeScalar other);
    double getValue();
    @Override
    String toString();
}
