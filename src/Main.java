import Interpreter.Parser.Parser;
import Interpreter.Tokenizer.Tokenizer;
import Interpreter.Tokenizer.Token;
import Util.WarningHandler;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        String input = "int x = 4;\n" +
                "int y = 2 + 2 + 8 + 9;"; // input goes here

        Tokenizer tokenizer = new Tokenizer(input);
        List<Token> tokens = tokenizer.tokenize();
        System.out.println(tokens);
        System.out.println();

        // this is where the parser would go
        Parser parser = new Parser(tokens);
        parser.interpretCode();
    }
}
