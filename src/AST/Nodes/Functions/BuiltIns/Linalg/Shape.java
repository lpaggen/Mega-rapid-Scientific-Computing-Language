package AST.Nodes.Functions.BuiltIns.Linalg;

import AST.Nodes.DataStructures.Array;
import AST.Nodes.DataStructures.Matrix;
import AST.Nodes.DataTypes.IntegerConstant;
import AST.Nodes.Expression;
import AST.Nodes.Functions.BuiltIns.BuiltInFunctionSymbol;
import Interpreter.Runtime.Environment;
import Interpreter.Tokenizer.TokenKind;

import java.util.ArrayList;
import java.util.List;

public class Shape extends BuiltInFunctionSymbol {
    public Shape() {
        super("shape", TokenKind.ARRAY); // Return type can be defined as needed
    }

    @Override
    public Object call(Environment env, List<Object> args) {
        if (args.size() != 1) {
            throw new IllegalArgumentException("Shape function requires exactly one argument.");
        }
        Object arg = args.getFirst();
        if (arg instanceof Matrix mat) {
            ArrayList<Expression> shape = new ArrayList<>();
            shape.add(new IntegerConstant(mat.rows()));
            shape.add(new IntegerConstant(mat.cols()));
            return new Array(shape, TokenKind.INTEGER);
        } else {
            throw new IllegalArgumentException("Shape function requires a Matrix as an argument.");
        }
    }
}
