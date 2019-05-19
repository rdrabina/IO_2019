package agar_io;

import constant.Constants;

public class Position {
    final int x;
    final int y;
    private final int ordinal;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
        ordinal = x + Constants.WINDOW_WIDTH * y;
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
