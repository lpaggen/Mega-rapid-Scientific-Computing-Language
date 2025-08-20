package AST.Nodes.BuiltIns;

import Util.Environment;

import java.time.LocalTime;
import java.util.List;

public class Time extends BuiltInFunctionSymbol {
    public Time() {
        super("time");
    }

    @Override
    public Object call(Environment env, List<Object> args) {
        // depending on the argument:
        // default should return human-readable time from Java's LocalTime
        // is user specifies one of h, m, s, ms -> return accordingly
        if (args.isEmpty()) {
            return java.time.LocalTime.now().toString(); // default to human-readable time
        } else if (args.size() == 1) {
            String unit = args.getFirst().toString().toLowerCase();
            LocalTime now = java.time.LocalTime.now();
            return switch (unit) {
                case "h" -> now.getHour();
                case "m" -> now.getMinute();
                case "s" -> now.getSecond();
                case "ms" -> now; // milliseconds since epoch
                default -> throw new IllegalArgumentException("Invalid time unit: " + unit);
            };
        } else {
            throw new IllegalArgumentException("time function accepts at most one argument.");
        }
    }
}
