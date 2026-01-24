package Semantic;

import Types.TypeNode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

// env uses scoped hash maps
// so each "level" i call "scope" is its own environment
// the idea is that we should now check the local scope, and just go up until we find a variable to find if it is declared or not
public class Environment {
    private final Deque<Map<String, Symbol>> envStack = new ArrayDeque<>();

    public Environment() {
        pushScope();  // initialize global scope
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

    public Symbol require(String name) {
        Symbol s = lookup(name);
        if (s == null) {
            throw new RuntimeException("Undefined symbol: " + name);
        }
        return s;
    }

    // allows shadowing of variables in inner scopes, but not redeclaration in the same scope !!
    public void declare(String name, Symbol value) {
        Map<String, Symbol> currentScope = envStack.peek();
        assert currentScope != null;
        if (currentScope.containsKey(name)) {
            throw new IllegalArgumentException("Variable already declared in this scope: " + name);
        }
        currentScope.put(name, value);
    }

    public Symbol lookup(String name) {
        for (Map<String, Symbol> scope : envStack) {
            if (scope.containsKey(name)) {
                return scope.get(name);
            }
        }
        return null;  // return null, this way we don't crash the program immediately -- raise multiple errors
    }

    public TypeNode lookupType(String name) {
        Symbol sym = lookup(name);
        if (sym instanceof FunctionSymbol funcSym) {
            return funcSym.type();
        }
        if (sym instanceof VariableSymbol varSym) {
            return varSym.type();
        }
        return null;
    }

    public boolean isDeclared(String name) {
//        return envStack.peek().containsKey(name);
        for (Map<String, Symbol> scope : envStack) {
            if (scope.containsKey(name)) {
                return true;
            }
        }
        return false;
    }

    @Deprecated  // i use this only for debugging !! public because i need it in main
    public Map<String, Symbol> getAllSymbols() {
        Map<String, Symbol> allSymbols = new HashMap<>();
        for (Map<String, Symbol> scope : envStack) {
            allSymbols.putAll(scope);
        }
        return allSymbols;
    }

    public void loadModule(Map<String, Symbol> moduleSymbols) {
        Map<String, Symbol> currentScope = envStack.peek();  // lazy import is allowed, unsure why you would use it however
        for (Map.Entry<String, Symbol> entry : moduleSymbols.entrySet()) {
            String name = entry.getKey();
            Symbol symbol = entry.getValue();
            currentScope.put(name, symbol);
        }
    }

    // this isn't really what i want, but it works for now -- in the future clear() should remove everything from the env
    // it doesn't clear the global scope yet
    @Deprecated  // debug purposes
    public void clear() {
        while (envStack.size() > 1) {
            envStack.pop();
        }
    }
}
