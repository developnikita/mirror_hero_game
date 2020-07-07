package com.neolab.heroesGame.arena;

import java.util.Objects;

public class SquareCoordinate implements Comparable<SquareCoordinate>{
    private final int X;
    private final int Y;

    public SquareCoordinate(int x, int y) {
        X = x;
        Y = y;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    /**
     * Compare a given SquareCoordinate with current(this) object.
     * X compare name and than Y
     */
    @Override
    public int compareTo(SquareCoordinate coordinate) {
        int diff = X - coordinate.getX();
        return (diff == 0) ? Y - coordinate.getY() : diff;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SquareCoordinate that = (SquareCoordinate) o;
        return X == that.X &&
                Y == that.Y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(X, Y);
    }
}
