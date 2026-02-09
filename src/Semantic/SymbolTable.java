package Semantic;

import AST.MathTypeNode;
import AST.Type;
import java.util.*;

public class SymbolTable {
    private final Deque<Map<String, TypeInfo>> scopes = new ArrayDeque<>();

    public SymbolTable() {
        pushScope();
    }

    public void pushScope() {
        scopes.push(new HashMap<>());
    }

    public void popScope() {
        if (scopes.size() == 1) {
            throw new IllegalStateException("Cannot pop global scope");
        }
        scopes.pop();
    }

    public void declare(String name, Type type, boolean isMutable) {
        Map<String, TypeInfo> currentScope = scopes.peek();
        if (currentScope.containsKey(name)) {
            throw new RuntimeException("Symbol already declared: " + name);
        }
        currentScope.put(name, new TypeInfo(name, type, isMutable));
    }

    public boolean isMutable(String name) {
        TypeInfo info = lookup(name);
        return info != null && info.isMutable();
    }

    public TypeInfo lookup(String name) {
        for (Map<String, TypeInfo> scope : scopes) {
            if (scope.containsKey(name)) {
                return scope.get(name);
            }
        }
        return null;
    }

    public boolean isSymbolicDimension(String name) {
        TypeInfo info = lookup(name);
        return info != null && info.type() instanceof MathTypeNode;
    }

    public boolean isDeclared(String name) {
        return lookup(name) != null;
    }
}

// we need a TypeInfo class to store the type and mutability of a symbol
record TypeInfo(
        String name,
        Type type,
        boolean isMutable
) {}
