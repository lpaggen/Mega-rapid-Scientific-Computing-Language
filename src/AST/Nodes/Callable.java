package AST.Nodes;

public interface Callable { // eventually i'll document everything
    /**
     * Calls the function with the provided arguments.
     *
     * @param args The arguments to pass to the function.
     * @return The result of the function call.
     */
    Object call(Object... args);

    /**
     * Returns the name of the callable.
     *
     * @return The name of the callable.
     */
    String getName();
}
