package com.neolab.heroesGame.arena;

import java.util.Objects;

public class SquareCoordinate {
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
