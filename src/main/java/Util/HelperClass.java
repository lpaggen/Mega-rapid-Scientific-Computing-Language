package Util;

public class HelperClass {

    public static void help(String arg) {
        switch (arg) {
            case "help":
                System.out.println("Available commands:");
                System.out.println("  help - Show this help message");
                System.out.println("  exit - Exit the program");
                System.out.println("  run <file> - Run the specified file");
                System.out.println("  compile <file> - Compile the specified file");
                break;
            case "exit":
                System.out.println("Exiting the program...");
                break;
            case "run":
                System.out.println("Running the specified file...");
                break;
            case "compile":
                System.out.println("Compiling the specified file...");
                break;
            default:
                System.out.println("Unknown command: " + arg);
        }
    }
}
