public class Main {
    public static void main(String[] args) {
        Expression x = new Variable("x");
        Expression expr = new Product(new Constant(5), new Cosine(new Product(x, new Constant(4))));

        System.out.println("expression: " + expr);

        // the partial and WRT is not supported yet actually...
        System.out.println("Derivative wrt x: " + expr.diff("x"));
    }
}
