package AST.Nodes;

import Util.LookupTable;

import java.sql.SQLDataException;

public class symbolicNode extends Expression {
    private final String name;

    public symbolicNode(String name) {
        this.name = name;
    }

    @Override
    public Expression derive(String variable) {
        return null;
    }

    @Override
    public Object evaluate(LookupTable<String, Expression, Object> lookupTable) { // not sure if i want this to be Number yet, could also be the computable compliant one
        if (lookupTable.isDeclared(name)) {
            return lookupTable.getValue(name);
        }
        throw new RuntimeException("no no no"); // maybe we should throw an error
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public Expression simplify() {
        return this;
    }

    // this isn't correct -- what i need to do is handle variable substitution in the case of symbols...
    @Override
    public Expression substitute(LookupTable<String, Expression, Object> lookupTable, String... s) {
        if (lookupTable.isDeclared(name)) {
            return lookupTable.getValue(name);
        }
    }
}
