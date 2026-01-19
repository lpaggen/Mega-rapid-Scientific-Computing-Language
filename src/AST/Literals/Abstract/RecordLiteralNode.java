package AST.Literals.Abstract;

import AST.Expressions.Expression;

import java.util.Map;

public final class RecordLiteralNode extends Expression {  // this is our hashmap, key-value pairs
    private final Map<String, Expression> fields;

    public RecordLiteralNode(Map<String, Expression> fields) {
        this.fields = fields;
    }

    public Expression getField(String name) {
        return fields.get(name);
    }

    @Override
    public String toString() {
        return "RecordLiteralNode{" +
                "fields=" + fields +
                '}';
    }
}
