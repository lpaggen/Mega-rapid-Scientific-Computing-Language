package DataTypes;

import AST.Nodes.Exp;

import java.util.ArrayList;
import java.util.List;

public class Expression {
    private List<Term> terms;

    public Expression() {
        this.terms = new ArrayList<>();
    }

    public void addTerm(Number coefficient, Symbol symbol) {
        if (coefficient instanceof Integer) {
            this.terms.add(new Term(symbol, (Integer) coefficient));
        } else if (coefficient instanceof Float) {
            this.terms.add(new Term(symbol, (Float) coefficient));
        } else {
            throw new IllegalArgumentException("Expected either Float or Int as coefficient of a symbol");
        }
    }

    public List<Term> getTerms() {
        return terms;
    }

    // this has to somehow be parsed too, we could definitely run a parse and tokenizer here, i don't think we have a choice
    // because we need a way to match the symbols together in operations, which is crazy difficult
    public Expression add(Expression this, Expression other) { // this must handle the additions, it's quite complex
        // will write this out on paper first, it's very complex
        return null;
    }

    @Override
    public String toString() {
        return terms.toString(); // not sure if this can work
    }
}
