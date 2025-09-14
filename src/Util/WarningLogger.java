package Util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WarningLogger {
    private final List<String> warnings = new ArrayList<>();
    private final HashMap<Integer, String> severityLevels = new HashMap<>();
    private static final String LOG_FILE_PATH = "warnings.log";
    public WarningLogger() {
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

        System.out.println("Generated warnings for your script...:");
        for (String warning : warnings) {
            System.out.println(warning);
        }
    }

    // this method writes to a log file
    public void logWarningsToFile() {
        clearWarningsFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH))) {
            for (String warning : warnings) {
                writer.write(warning);
                writer.newLine();
            }
        } catch (Exception e) {
            System.err.println("Error writing warnings to log file: " + e.getMessage());
        }
    }

    // the idea is to be able to use this in the non-REPL mode, so we clear the log file before writing to it
    public void clearWarningsFile() {
        try {
            File file = new File(LOG_FILE_PATH);
            if (file.exists()) {
                if (!file.delete()) {
                    System.err.println("Failed to delete the log file.");
                }
            }
        } catch (Exception e) {
            System.err.println("Error clearing the log file: " + e.getMessage());
        }
    }

    private void clearWarnings() {
        warnings.clear();
    }
}
