package com.neolab.heroesGame.arena;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class SquareCoordinate implements Comparable<SquareCoordinate> {
    private final int x;
    private final int y;

    @JsonCreator
    public SquareCoordinate(@JsonProperty("x") final int x, @JsonProperty("y") final int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Compare a given SquareCoordinate with current(this) object.
     * X compare name and than Y
     */
    @Override
    public int compareTo(SquareCoordinate coordinate) {
        int diff = x - coordinate.getX();
        return (diff == 0) ? y - coordinate.getY() : diff;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SquareCoordinate that = (SquareCoordinate) o;
        return x == that.x &&
                y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
