package helpers;

import constant.Constants;

public class Position {
    public final double x;
    public final double y;
    private final int ordinal;

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
        ordinal = (int) x + Constants.WINDOW_WIDTH * (int) y;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Position))
            return false;
        Position other = (Position) o;
        return this.x == other.x && this.y == other.y;
    }

    @Override
    public int hashCode() {
        return ordinal;
    }
}
