package com.naman09.SnakeExena;

public class Vector2 {
    Integer x;
    Integer y;

    Vector2(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Vector2D(" + "x=" + x + ", y=" + y + ')';
    }
}
