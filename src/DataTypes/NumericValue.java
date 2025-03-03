//package DataTypes;
//
//// NumericValue is a Computable, so it can be used in operations
//// IntegerValue and FloatValue both implement NumericValue, so they can be used in operations together
//public class NumericValue implements Computable {
//    private final Number value;
//
//    public NumericValue(Number value) {
//        this.value = value;
//    }
//
//    @Override
//    public Computable add(Computable other) {
//        if (other instanceof NumericValue num) {
//            if (this.isInteger() && num.isInteger()) {
//                return new NumericValue(this.value.intValue() + num.value.intValue()); // here we stick to Integer
//            }
//            return new NumericValue(this.value.doubleValue() + num.value.doubleValue()); // here we go for doubles instead
//        }
//        throw new UnsupportedOperationException("Cannot add a Numeric to a non-Numeric");
//    }
//
//    @Override
//    public Computable subtract(Computable other) {
//        return null;
//    }
//
//    @Override
//    public Computable multiply(Computable other) {
//        if (other instanceof NumericValue num) {
//            if (this.isInteger() && num.isInteger()) {
//                return new NumericValue(this.value.intValue() * num.value.intValue());
//            }
//            return new NumericValue(this.value.doubleValue() * num.value.doubleValue());
//        }
//        throw new UnsupportedOperationException("Cannot multiply a Numeric with a non-Numeric");
//    }
//
//
//    @Override
//    public Computable divide(Computable other) {
//        return null;
//    }
//
//    @Override
//    public String toString() {
//        return value.toString();
//    }
//
//    private boolean isInteger() { // we check both Floats and Integers here
//        return this.value instanceof Integer || this.value.doubleValue() % 1 == 0;
//    }
//}
