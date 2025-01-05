import AST.Nodes.ASTNode;
import Compiler.Parser.Parser;
import Compiler.Tokenizer.TokenKind;
import Compiler.Tokenizer.Tokenizer;
import Compiler.Tokenizer.Token;
import DataTypes.Symbol;
import DataTypes.Term;
import Util.HelperClass;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Define the input string you want to tokenize
        String input = "INTEGER x;"; // Example input

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

        System.out.println();
        System.out.println("Output of the parser:");
        System.out.println(out);

        System.out.println();
        System.out.println("testing new structures");
        Symbol x = new Symbol("x", 5, TokenKind.INTEGER);
        Term xTerm = new Term(x, 5);

        // HelperClass.help("symbol");

        System.out.println(x);
        System.out.println();
        System.out.println(xTerm);
    }
}
