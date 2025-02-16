import Interpreter.Tokenizer.Tokenizer;
import Interpreter.Tokenizer.Token;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        String input = "MATRIX x = [[3 5][6 8][7 0]];"; // input goes here

        Tokenizer tokenizer = new Tokenizer(input);
        List<Token> tokens = tokenizer.tokenize();
        System.out.println(tokens);

        for (Token token : tokens) {
            System.out.println(token);
        }
    }
}
