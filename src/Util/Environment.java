package Util;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

// scoped hash maps! super easy concept for the time being, use a stack to push and pop scope
// so each "level" i call "scope" is its own environment
// the idea is that we should now check the local scope, and just go up until we find a variable to find if it is declared or not
public class Environment {
    private final Deque<Map<String, Symbol>> envStack = new ArrayDeque<>();

    public Environment() {
        // initialize with a global environment -- remember we can just change this anytime!
        pushScope();
    }

    public void pushScope() {
        envStack.push(new HashMap<>());
    }

    // self-explanatory, this just goes up in scope
    // obviously we can delete scopes when we go up, since we don't need them anymore
    public void popScope() {
        if (envStack.size() == 1) {
            throw new IllegalStateException("Cannot pop global scope");
        }
        envStack.pop();
    }

    public void declareSymbol(String name, Symbol value) {
        Map<String, Symbol> currentScope = envStack.peek();
        if (currentScope.containsKey(name)) {
            throw new IllegalArgumentException("Symbol '" + name + "' already declared in current scope");
        }
        currentScope.put(name, value);
    }

    public Symbol lookup(String name) {
        for (Map<String, Symbol> scope : envStack) {
            if (scope.containsKey(name)) {
                return scope.get(name);
            }
        }
        throw new IllegalArgumentException("Variable '" + name + "' not found in any scope");
    }

    public boolean isDeclared(String name) {
        return envStack.peek().containsKey(name);
    }

    // here we just set a new value for the variable
    public void updateVariable(String name, Symbol value) {
        for (Map<String, Symbol> scope : envStack) {
            if (scope.containsKey(name)) {
                scope.put(name, value);
                return;
            }
        }
        throw new IllegalArgumentException("Variable '" + name + "' not found in any scope");
    }

    public void loadModule(Map<String, Symbol> moduleSymbols) {
        // load a module into the current scope (this is going to be global always)
        Map<String, Symbol> currentScope = envStack.peek();
        for (Map.Entry<String, Symbol> entry : moduleSymbols.entrySet()) {
            String name = entry.getKey();
            Symbol symbol = entry.getValue();
            currentScope.put(name, symbol);
        }
    }
}
