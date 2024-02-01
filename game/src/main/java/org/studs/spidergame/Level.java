/**
 * Represents a level in the spider game, encapsulating details such as the level's number,
 * the spawn location for the spider, and the positions of pellets within the level.
 */
package org.studs.spidergame;

import java.util.ArrayList;

public class Level {
    /**
     * The index identifying the level.
     */
    private final int levelNumber;

    /**
     * The grid size for the level, defining the dimensions of the level's playing field.
     */
    private final int GRID_SIZE = 5;

    /**
     * The spawn location of the spider on the grid, represented as an array of two integers
     * where index 0 holds the x-coordinate and index 1 holds the y-coordinate.
     */
    private final int[] spawnLoc;

    /**
     * A two-dimensional array representing the grid of the level. Each cell of the grid can hold
     * a character indicating the presence or absence of a pellet and its color.
     */
    public char[][] grid;

    /**
     * A list of {@link Pellet} objects representing the locations and colors of pellets within the level.
     */
    public ArrayList<Pellet> pelletLocations;

    /**
     * Constructs a new {@code Level} object with specified spawn coordinates and a level number.
     *
     * @param spX    the x-coordinate of the spider's spawn location.
     * @param spY    the y-coordinate of the spider's spawn location.
     * @param number the unique index identifying the level.
     */
    public Level(int spX, int spY, int number) {
        this.grid = new char[GRID_SIZE][GRID_SIZE];
        initGrid();
        this.pelletLocations = new ArrayList<>();
        this.spawnLoc = new int[2];
        this.spawnLoc[0] = spX;
        this.spawnLoc[1] = spY;
        this.levelNumber = number;
    }

    /**
     * Initializes the grid for the level, filling each cell with a space character to indicate
     * that it is empty.
     */
    private void initGrid() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                grid[i][j] = ' ';
            }
        }
    }

    /**
     * Returns the level number of this level.
     *
     * @return the level number.
     */
    public int getLevelNumber() {
        return levelNumber;
    }

    /**
     * Returns the spawn location of the spider for this level.
     *
     * @return an array of two integers where index 0 is the x-coordinate and index 1 is the y-coordinate.
     */
    public int[] getSpawnLoc() {
        return spawnLoc;
    }
}