package Util;

// this class is just meant to handle errors
// it should include LINE + description of the error, not much else
// it needs to be basic enough, but return some degree of help
// some more work is needed to refine the whole thing, but overall it should be alright
public class ErrorHandler extends Error {
    private final int line;
    private final String description;
    private final String phase;

    public ErrorHandler(String phase, int line, String description, String suggestion) {
        super("Detected error during " + phase + " phase." + "\nError at line " + line + ": " + description + " " + suggestion); // super constructor to set the error message, no method call needed
        this.line = line;
        this.description = description;
        this.phase = phase;
    }

    public int getLine() {
        return line;
    }

    public String getDescription() {
        return description;
    }
}
