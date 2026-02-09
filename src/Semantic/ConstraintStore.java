package Semantic;

import java.util.HashMap;

public final class ConstraintStore {  // union find data structure to store symbolic dimension constraints
    private final HashMap<String, String> parent = new HashMap<>();
    private final HashMap<String, Integer> rank = new HashMap<>();
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
}
