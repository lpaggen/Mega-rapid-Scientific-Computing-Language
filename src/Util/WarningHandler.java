package Util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WarningHandler extends Throwable{
    private final List<String> warnings = new ArrayList<>();
    private final HashMap<Integer, String> severityLevels = new HashMap<>();
    public WarningHandler() {
        // Initialize severity levels
        severityLevels.put(1, "LOW");
        severityLevels.put(2, "MEDIUM");
        severityLevels.put(3, "HIGH");
    }

    public void addWarning(Integer severity, String warning, Integer line) {
        if (severity < 1 || severity > 3) {
            throw new IllegalArgumentException("Severity must be between 1 and 3.");
        }
        warnings.add(severityLevels.get(severity) + "Warning at line " + line + ": " + warning);
    }

    public void printWarnings() {
        if (warnings.isEmpty()) {
            System.out.println("No warnings detected.");
            return;
        }

        System.out.println("Warnings:");
        for (String warning : warnings) {
            System.out.println(warning);
        }
    }

    private void clearWarnings() {
        warnings.clear();
    }
}
