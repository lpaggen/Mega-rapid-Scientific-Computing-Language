package Runtime;

import AST.Nodes.Expressions.Expression;

// add methods as needed
public sealed interface MatrixLike extends RuntimeValue permits RuntimeMatrix {
    int rows();
    int cols();
    int[] shape();                     // for tensors
    RuntimeValue get(int row, int col);       // matrix element
    // Expression get(int... indices);         // tensor element
    MatrixLike getColumns(int... col);          // returns a vector
    MatrixLike getRows(int... row);             // returns a vector
    RuntimeValue[] getElements();             // could make this Iterable<Expression> too, maybe
//    VectorLike add(Expression other);
//    VectorLike sub(Expression left, Expression right);    // elementwise
//    VectorLike mul(Expression left, Expression right);    // Hadamard product
//    VectorLike div(Expression left, Expression right);      // elementwise
    MatrixLike dot(Expression left, Expression right);         // linear algebra dot product
    MatrixLike outer(Expression left, Expression right);       // linear algebra outer product
    MatrixLike pow(Expression exponent);     // elementwise
    MatrixLike transpose();                   // for matrices
}
