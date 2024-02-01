package com.example.spiderworld2;

public class PelletData {
    public int x;
    public int y;
    public char color;

    public PelletData(int x, int y, char c) {
        this.x = x;
        this.y = y;
        this.color = c;
    }

    @Override
    public String toString() {
        return "Pellet of color '" + color + "' at x = " + x + ", y = " + y;
    }
}
