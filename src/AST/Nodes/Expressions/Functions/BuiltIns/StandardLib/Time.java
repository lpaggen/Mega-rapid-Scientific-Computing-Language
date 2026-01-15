package AST.Nodes.Expressions.Functions.BuiltIns.StandardLib;

import AST.Nodes.DataTypes.Scalar;
import AST.Nodes.Expressions.Functions.BuiltIns.BuiltInFunctionSymbol;
import Lexer.TokenKind;
import Runtime.Environment;

import java.time.LocalTime;
import java.util.List;

public class Time extends BuiltInFunctionSymbol {
    public Time() {
        super("time", TokenKind.STRING);
    }

    private static final String[] validArguments = {"h", "m", "s", "ms"};

    @Override
    public Object call(Environment env, List<Object> args) {
        System.out.println("Calling built-in function 'time' with arguments: " + args);
        // depending on the argument:
        // default should return human-readable time from Java's LocalTime
        // is user specifies one of h, m, s, ms -> return accordingly
        if (args.isEmpty()) {
            return java.time.LocalTime.now(); // default to human-readable time
        } else if (args.size() == 1) {
            String unit = args.getFirst().toString().toLowerCase();
            LocalTime now = java.time.LocalTime.now();
            return switch (unit) {
                case "h" -> new Scalar(now.getHour()); // we have to use our wrappers for everything now, good fun!
                case "m" -> new Scalar(now.getMinute());
                case "s" -> new Scalar(now.getSecond());
                case "ms" -> new Scalar((int) (System.nanoTime() / 1000000)); // convert to ms
                default -> throw new IllegalArgumentException("Invalid arguments for time function. Expected one of: " + String.join(", ", validArguments));
            };
        }
        throw new IllegalArgumentException("time function accepts at most one argument.");
    }
}
