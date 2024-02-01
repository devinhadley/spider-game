package org.studs.spidergame;

public class Pellet {
    public int x;
    public int y;
    public char color;

    public Pellet(int x, int y, char c) {
        this.x = x;
        this.y = y;
        this.color = c;
    }

    @Override
    public String toString() {
        return "Pellet of color '" + color + "' at x = " + x + ", y = " + y;
    }
}
