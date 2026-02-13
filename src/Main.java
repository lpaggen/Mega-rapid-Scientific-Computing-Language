import AST.Statement;
import Parser.Parser;
import Lexer.Tokenizer;
import Lexer.Token;
import Semantic.ConstraintStoreBuilder;
import Semantic.SymbolTable;
import Semantic.SymbolTableBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
		
        // trying to check if we can read from a .marcel file
        String code = Files.readString(Path.of("src/example.marcel"));

        // check the time to run a .marcel program
        int startTimeTokenizer = (int) System.currentTimeMillis();
        Tokenizer tokenizer = new Tokenizer(code);
        List<Token> tokens = tokenizer.tokenize();
        System.out.println("Finished tokenizing, took: " + ((int) System.currentTimeMillis() - startTimeTokenizer) + " ms");

        int startTimeParser = (int) System.currentTimeMillis();
        Parser parser = new Parser(tokens);
        List<Statement> ast = parser.parseProgram();
        System.out.println("Finished parsing, took: " + ((int) System.currentTimeMillis() - startTimeParser) + " ms");

        int startTimeSymbolTable = (int) System.currentTimeMillis();
        SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder(new ArrayList<>());
        symbolTableBuilder.build(ast);
        System.out.println("Finished building symbol table, took: " + ((int) System.currentTimeMillis() - startTimeSymbolTable) + " ms");
        symbolTableBuilder.printErrors();

        int startTimeConstaintCollector = (int) System.currentTimeMillis();
        ConstraintStoreBuilder constraintCollector = new ConstraintStoreBuilder(new ArrayList<>(), symbolTableBuilder.symbolTable);
        constraintCollector.collect(ast);
        System.out.println("Finished collecting constraints, took: " + ((int) System.currentTimeMillis() - startTimeConstaintCollector) + " ms");
        constraintCollector.printErrors();
    }
}
