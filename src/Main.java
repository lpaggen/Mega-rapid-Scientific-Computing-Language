import Expressions.*;

public class Main {
    public static void main(String[] args) {
        Expression x = new Variable("x");
        Expression expr = new Product(x, new Power(x, new Constant(3)));

        System.out.println("expression: " + expr);

        // the partial and WRT is not supported yet actually...
        System.out.println("Derivative wrt x: " + expr.diff("x"));
    }
}
