package Types.Abstract;

import Types.Abstract.Dimension;

public final class KnownDimension implements Dimension {
    private final int value;
    public KnownDimension(int value) {
        this.value = value;
    }
}
