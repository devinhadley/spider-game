package org.studs.spidergame;

import javafx.scene.paint.Color;

public class Pellet {
    public int x;
    public int y;
    public char color;

    public Pellet(int x, int y, char c) {
        this.x = x;
        this.y = y;
        this.color = c;
    }

    public Color getColor() {
        return switch (this.color) {
            case 'r' -> Color.RED;
            case 'g' -> Color.GREEN;
            case 'b' -> Color.BLUE;
            default -> null;
        };
    }

    @Override
    public String toString() {
        return "Pellet of color '" + color + "' at x = " + x + ", y = " + y;
    }
}
