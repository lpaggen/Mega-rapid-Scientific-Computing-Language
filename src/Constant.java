public class Constant extends Expression{
    private final double value;

    public Constant(double value) {
        this.value = value;
    }

    @Override
    public Expression diff(String variable) {
        // constant evaluates to 0 -- unless coeff, will need to address this later
        return new Constant(0);
    }

    @Override
    public double eval(double... values) {
        // return self
        return value;
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }
}
