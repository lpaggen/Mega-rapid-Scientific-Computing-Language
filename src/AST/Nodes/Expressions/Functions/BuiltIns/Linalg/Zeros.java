package AST.Nodes.Expressions.Functions.BuiltIns.Linalg;

import AST.Nodes.Expressions.Functions.BuiltIns.BuiltInFunctionSymbol;
import Runtime.Environment;
import Lexer.TokenKind;

import java.util.List;

public class Zeros extends BuiltInFunctionSymbol {
    public Zeros() {
        super("zeros", TokenKind.MATRIX);
    }

    @Override
    public Object call (Environment env, List<Object> arguments) {
        if (arguments.size() != 2) {
            throw new RuntimeException("zeros function expects 2 arguments: rows and columns.");
        }
        Scalar rows = (Scalar) arguments.get(0);
        Scalar cols = (Scalar) arguments.get(1);
        return Matrix.zeros(rows.getValue().intValue(), cols.getValue().intValue());
    }

    @Override
    public String toString() {
        return "zeros";
    }

    @Override
    public String help() {
        return "Creates a matrix filled with zeros. Usage: zeros(rows, cols). Parameters: rows (int) - number of rows, cols (int) - number of columns.";
    }
}
