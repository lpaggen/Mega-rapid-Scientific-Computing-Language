package Interpreter;

// this class is just meant to handle errors
// it should include LINE + description of the error, not much else
// it needs to be basic enough, but return some degree of help
public class ErrorHandler extends Error {
    private final int line;
    private final String description;

    public ErrorHandler(int line, String description) {
        super("Error at line " + line + ": " + description); // super constructor to set the error message, no method call needed
        this.line = line;
        this.description = description;
    }

    public int getLine() {
        return line;
    }

    public String getDescription() {
        return description;
    }
}
