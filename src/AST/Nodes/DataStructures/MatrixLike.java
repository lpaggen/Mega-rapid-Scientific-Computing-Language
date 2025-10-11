package AST.Nodes.DataStructures;

import AST.Nodes.Expressions.Expression;

// can call this iterable, collection, doesn't matter too much for now
// will add more methods as needed, this is just a start for the demo project
public interface MatrixLike extends SequenceLike {
    int rows();
    int cols();
    int[] shape();                     // for tensors
    Expression get(int row, int col);       // matrix element
    // Expression get(int... indices);         // tensor element
    MatrixLike getColumns(int... col);          // returns a vector
    MatrixLike getRows(int... row);             // returns a vector
    Expression[] getElements();             // could make this Iterable<Expression> too, maybe
//    VectorLike add(Expression other);
//    VectorLike sub(Expression left, Expression right);    // elementwise
//    VectorLike mul(Expression left, Expression right);    // Hadamard product
//    VectorLike div(Expression left, Expression right);      // elementwise
    MatrixLike dot(Expression left, Expression right);         // linear algebra dot product
    MatrixLike outer(Expression left, Expression right);       // linear algebra outer product
    MatrixLike pow(Expression exponent);     // elementwise
    MatrixLike transpose();                   // for matrices
}
