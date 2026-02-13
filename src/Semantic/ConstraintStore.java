package Semantic;

import AST.Metadata.Containers.Dimension;
import AST.Metadata.Containers.KnownDimension;
import AST.Metadata.Containers.SymbolicDimension;

import java.util.HashMap;

public final class ConstraintStore {  // union find data structure to store symbolic dimension constraints
    private final HashMap<String, String> parent = new HashMap<>();
    private final HashMap<String, Integer> rank = new HashMap<>();
    private final HashMap<String, Integer> symbolValues = new HashMap<>();  // for symbolic dimensions that are bound to known values
    public ConstraintStore() {}

    private String find(String x) {
        if (!parent.containsKey(x)) {
            parent.put(x, x);
            rank.put(x, 0);
            return x;
        }
        if (!parent.get(x).equals(x)) {  // while not root, keep going up the tree and do path compression
            parent.put(x, find(parent.get(x)));
        }

        return parent.get(x);
    }

    private void union(String x, String y) {
        String rootX = find(x);
        String rootY = find(y);

        if (rootX.equals(rootY)) {
            return;  // belong to same set
        }

        int rankX = rank.get(rootX);
        int rankY = rank.get(rootY);  // smaller tree goes under bigger tree to keep the tree flat

        if (rankX < rankY) {
            parent.put(rootX, rootY);
        } else if (rankX > rankY) {
            parent.put(rootY, rootX);
        } else {
            parent.put(rootY, rootX);
            rank.put(rootX, rankX + 1);
        }
    }

    private boolean areEqual(String x, String y) {
        return find(x).equals(find(y));
    }

    public void addEqualityConstraint(Dimension x, Dimension y) {
        if (x instanceof SymbolicDimension s1 && y instanceof SymbolicDimension s2) {
            union(s1.name(), s2.name());
        }
        if (x instanceof KnownDimension k1 && y instanceof KnownDimension k2) {
            if (k1.value() != k2.value()) {
                throw new RuntimeException("Constraint violation: " + k1.value() + " != " + k2.value());
            }
        }
        if (x instanceof KnownDimension k && y instanceof SymbolicDimension s) {
            bindSymbolToValue(s.name(), k.value());
        }
        if (x instanceof SymbolicDimension s && y instanceof KnownDimension k) {
            bindSymbolToValue(s.name(), k.value());
        }

        // TODO complex cases, eg :    claim a + 1 = b + 1;
    }

    private void bindSymbolToValue(String symbol, int value) {
        String root = find(symbol);
        if (symbolValues.containsKey(root)) {
            int existingValue = symbolValues.get(root);
            if (existingValue != value) {
                throw new RuntimeException(
                        "Inconsistent constraint: " + symbol +
                                " cannot be both " + existingValue + " and " + value
                );
            }
        }
        symbolValues.put(root, value);
    }

    private Integer getSymbolValue(String symbol) {
        String root = find(symbol);
        return symbolValues.get(root);
    }

    public boolean canProve(Dimension x, Dimension y) {
        if (x == null || y == null) {
            return false;
        }
        if (x == null && y == null) {
            return true;
        }
        if (x instanceof SymbolicDimension s1 && y instanceof SymbolicDimension s2) {
            return areEqual(s1.name(), s2.name());
        }
        if (x instanceof KnownDimension k1 && y instanceof KnownDimension k2) {
            return k1.value() == k2.value();
        }
        if (x instanceof KnownDimension k && y instanceof SymbolicDimension s) {
            Integer value = getSymbolValue(s.name());
            return value != null && value == k.value();
        }
        if (x instanceof SymbolicDimension s && y instanceof KnownDimension k) {
            Integer value = getSymbolValue(s.name());
            return value != null && value == k.value();
        }
        // TODO complex cases, eg :    claim a + 1 = b + 1;
        return false;
    }
}
