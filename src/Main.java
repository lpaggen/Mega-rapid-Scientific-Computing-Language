import Interpreter.Parser.Parser;
import Interpreter.Tokenizer.Tokenizer;
import Interpreter.Tokenizer.Token;
import Util.WarningHandler;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        // obviously we should get the input from a file, but for now we will just use a string
        String input = "fn hi() -> void {return null};"; // input goes here

        Tokenizer tokenizer = new Tokenizer(input);
        List<Token> tokens = tokenizer.tokenize();
        System.out.println(tokens);
        System.out.println();

        // this is where the parser would go
        Parser parser = new Parser(tokens);
        parser.interpretCode();
    }
}
