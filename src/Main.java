import Compiler.ASTNode;
import Compiler.Parser;
import Compiler.Tokenizer;
import Compiler.Token;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Define the input string you want to tokenize
        String input = "SYMBOL x = 2"; // Example input

        // Instantiate the Tokenizer with the input expression
        Tokenizer tokenizer = new Tokenizer(input);

        // Call the tokenize() method to get a list of tokens
        List<Token> tokens = tokenizer.tokenize();

        // Loop through the tokens and print them
        System.out.println(tokens);

        for (Token token : tokens) {
            System.out.println(token.getKind());
        }

        Parser parser = new Parser(tokens);

        ASTNode out = parser.interpretCode();

        // System.out.println(result);
    }
}
