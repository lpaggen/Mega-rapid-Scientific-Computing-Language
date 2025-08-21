package AST.Nodes.BuiltIns;

import Interpreter.Tokenizer.TokenKind;
import Util.Environment;

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
                case "h" -> now.getHour();
                case "m" -> now.getMinute();
                case "s" -> now.getSecond();
                case "ms" -> now.toString(); // milliseconds since epoch
                default -> throw new IllegalArgumentException("Invalid arguments for time function. Expected one of: " + String.join(", ", validArguments));
            };
        } else if (args.size() > 1) {
            throw new IllegalArgumentException("time function accepts at most one argument.");
        }
        throw new IllegalArgumentException("Invalid arguments for time function. Expected one of: " + String.join(", ", validArguments));
    }
}
