package org.studs.spidergame;

// Miro Haapalainen

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Level {
    private final int levelNumber;
    private final int GRID_SIZE = 5;
    private final int[] spawnLoc;
    public char[][] grid;
    public ArrayList<Pellet> pelletLocations;

    public Level(int spX, int spY, int number) {
        this.grid = new char[GRID_SIZE][GRID_SIZE];
        initGrid();
        this.pelletLocations = new ArrayList<>();
        this.spawnLoc = new int[2];
        this.spawnLoc[0] = spX;
        this.spawnLoc[1] = spY;
        this.levelNumber = number;
    }


    private void initGrid() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                grid[i][j] = ' ';
            }
        }
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public int[] getSpawnLoc() {
        return spawnLoc;
    }
}