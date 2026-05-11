package Semantic;

import AST.Functions.IntrinsicFunction;
import AST.Functions.LinalgInverseFunction;
import AST.Functions.LinalgTransposeFunction;

import java.util.HashMap;
import java.util.Map;

public final class Registry {  // for functions
    private final Map<String, IntrinsicFunction> functions = new HashMap<>();

    public Registry() {
        register("linalg", "inverse", new LinalgInverseFunction());
        register("linalg", "transpose", new LinalgTransposeFunction());
    }

    public void register(String namespace, String method, IntrinsicFunction fn) {
        functions.put(key(namespace, method), fn);
    }

    public IntrinsicFunction lookupNamespaceMethod(String namespace, String method) {
        return functions.get(key(namespace, method));
    }

    public static String key(String ns, String method) {
        return ns + "::" + method;
    }
}
