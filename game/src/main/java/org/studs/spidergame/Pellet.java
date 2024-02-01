/**
 * Represents a pellet within the game, characterized by its location on the grid and its color.
 */
package org.studs.spidergame;

import javafx.scene.paint.Color;

public class Pellet {
    /**
     * The x-coordinate of the pellet on the game grid.
     */
    public int x;

    /**
     * The y-coordinate of the pellet on the game grid.
     */
    public int y;

    /**
     * The color code of the pellet, represented as a character ('r' for red, 'g' for green, 'b' for blue).
     */
    public char color;

    /**
     * Constructs a new Pellet with specified coordinates and color.
     *
     * @param x the x-coordinate of the pellet on the grid.
     * @param y the y-coordinate of the pellet on the grid.
     * @param c the character representing the color of the pellet.
     */
    public Pellet(int x, int y, char c) {
        this.x = x;
        this.y = y;
        this.color = c;
    }

    /**
     * Returns the {@link Color} of the pellet.
     *
     * @return the {@link Color} object corresponding to the character code of the pellet's color.
     */
    public Color getColor() {
        return switch (this.color) {
            case 'r' -> Color.RED;
            case 'g' -> Color.GREEN;
            case 'b' -> Color.BLUE;
            default -> null;
        };
    }

    /**
     * Returns a string representation of the pellet, including its color and location.
     *
     * @return a string describing the pellet.
     */
    @Override
    public String toString() {
        return "Pellet of color '" + color + "' at x = " + x + ", y = " + y;
    }
}