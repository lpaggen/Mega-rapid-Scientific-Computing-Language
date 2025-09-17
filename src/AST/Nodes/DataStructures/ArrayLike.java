package AST.Nodes.DataStructures;

import AST.Nodes.Conditional.BooleanNode;
import AST.Nodes.Constant;
import AST.Nodes.Expression;

import java.util.ArrayList;

// can call this iterable, collection, doesn't matter too much for now
// will add more methods as needed, this is just a start for the demo project
public interface ArrayLike extends Iterable<Expression> {
    Constant size();  // returns Tuple-Like (rows, cols) for matrix, length for vector
    Constant rows();
    Constant cols();
    Expression get(Constant index);              // vector element
    Expression get(Constant row, Constant col);       // matrix element
    // Expression get(int... indices);         // tensor element
    Expression getColumns(Constant... col);          // returns a vector
    Expression getRows(Constant... row);             // returns a vector
    Expression[] getElements();             // could make this Iterable<Expression> too, maybe
    ArrayLike add(ArrayLike other);         // elementwise
    ArrayLike subtract(ArrayLike other);    // elementwise
    ArrayLike multiply(ArrayLike other);    // Hadamard product
    ArrayLike divide(ArrayLike other);      // elementwise
    ArrayLike dot(ArrayLike other);         // linear algebra dot product
    ArrayLike outer(ArrayLike other);       // linear algebra outer product
    ArrayLike pow(Expression exponent);     // elementwise
    BooleanNode isEmpty();
    String toString();
}
