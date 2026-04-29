package Semantic;

import AST.MathTypeNodeInterface;
import AST.TypeAttributes;
import AST.TypeInterface;
import AST.Type;

import java.util.*;

public class SymbolTable {
    private final Deque<Map<String, Type>> scopes = new ArrayDeque<>();

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

    public void declare(String name, TypeInterface typeInterface, boolean isMutable) {
        Map<String, Type> currentScope = scopes.peek();
        if (currentScope.containsKey(name)) {
            throw new RuntimeException("Symbol already declared: " + name);
        }
        currentScope.put(name, new Type(typeInterface, new TypeAttributes(isMutable, false)));
    }

    public boolean isMutable(String name) {
        Type type = lookup(name);
        return type != null && type.attributes().mutable();
    }

    public Type lookup(String name) {
        for (Map<String, Type> scope : scopes) {
            if (scope.containsKey(name)) {
                return scope.get(name);
            }
        }
        return null;
    }

    public boolean isSymbolicDimension(String name) {
        Type info = lookup(name);
        return info != null && info.typeInterface() instanceof MathTypeNodeInterface;
    }

    public boolean isDeclared(String name) {
        return lookup(name) != null;
    }
}

