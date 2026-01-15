import Parser.Parser;
import Lexer.Tokenizer;
import Lexer.Token;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        // obviously we should get the input from a file, but for now we will just use a string
        // String input = "include stdlib;\nint x = 0;";  // input goes here
		
        // trying to check if we can read from a .marcel file
        String code = Files.readString(Path.of("src/example.marcel"));

        // check the time to run a .marcel program
        int startTime = (int) System.currentTimeMillis();

        Tokenizer tokenizer = new Tokenizer(code);
        List<Token> tokens = tokenizer.tokenize();
        System.out.println(tokens);
        System.out.println();

        // this is where the parser would go
        Parser parser = new Parser(tokens);
        parser.parseProgram();

        int endTime = (int) System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime) + " ms");
    }
}
