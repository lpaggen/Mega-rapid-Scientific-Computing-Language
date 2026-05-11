package AST;



public final class FloatTypeInterface implements TypeInterface {
    @Override
    public String toString() {
        return "float";
    }

//    @Override
//    public <R> R accept(TypeVisitor<R> visitor) {
//        return visitor.visitFloatType(this);
//    }
}
