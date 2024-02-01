package com.example.spiderworld2;

// Miro Haapalainen

import java.util.ArrayList;

public class Level {
    private int levelNumber;
    private final int GRID_SIZE = 5;
    public char[][] grid;
    private int[] spawnLoc;
    public ArrayList<PelletData> pelletLocations;

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