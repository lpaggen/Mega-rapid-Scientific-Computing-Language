package Semantic;

import java.util.HashMap;

public final class ConstraintStore {  // union find data structure to store symbolic dimension constraints
    private final HashMap<String, String> parent = new HashMap<>();
    private final HashMap<String, Integer> rank = new HashMap<>();
}
