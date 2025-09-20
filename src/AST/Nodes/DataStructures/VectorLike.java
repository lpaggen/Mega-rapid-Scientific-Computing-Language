package AST.Nodes.DataStructures;

import AST.Nodes.DataTypes.IntegerConstant;
import AST.Nodes.Expression;

import java.util.AbstractMap;

// can call this iterable, collection, doesn't matter too much for now
// will add more methods as needed, this is just a start for the demo project
public interface VectorLike extends SequenceLike {
    int rows();
    int cols();
    int[][] shape();                     // for tensors
    Expression get(int row, int col);       // matrix element
    // Expression get(int... indices);         // tensor element
    VectorLike getColumns(int... col);          // returns a vector
    VectorLike getRows(int... row);             // returns a vector
    Expression[] getElements();             // could make this Iterable<Expression> too, maybe
    VectorLike add(VectorLike other);         // elementwise
    VectorLike sub(VectorLike other);    // elementwise
    VectorLike mul(VectorLike other);    // Hadamard product
    VectorLike div(VectorLike other);      // elementwise
    VectorLike dot(VectorLike other);         // linear algebra dot product
    VectorLike outer(VectorLike other);       // linear algebra outer product
    VectorLike pow(Expression exponent);     // elementwise
    VectorLike transpose();                   // for matrices
}