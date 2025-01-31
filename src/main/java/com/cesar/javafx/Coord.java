package com.cesar.javafx;

import java.util.Objects;

public class Coord {
    private int Y;
    private int X;

    public Coord() {
    }

    public Coord(int y, int x) {
        Y = y;
        X = x;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public void setX(int x) {
        X = x;
    }

    public void setY(int y) {
        Y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Coord coord = (Coord) o;
        return X == coord.X && Y == coord.Y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(X, Y);
    }

    @Override
    public String toString() {
        return "Coord{" +
                "Y=" + Y +
                ", X=" + X +
                '}';
    }
}
