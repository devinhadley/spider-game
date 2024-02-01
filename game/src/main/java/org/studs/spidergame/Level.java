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
        this.spawnLoc[0] = spX - 1;
        this.spawnLoc[1] = spY - 1;
        this.levelNumber = number;
    }

    public static void populateLevelsFromFile(String levelDataFilename, Level[] levels) {
        try {

            // This parser assumes that each line in the input file, corresponding to a single level, is formatted as such:
            // "22 43r 12b 45g" Corresponds to a level with a spawn location at coordinate 2, 2,
            // a red pellet at coordinate 4, 3, a blue pellet at 1, 2,
            // and a green pellet at 4, 5. The line number is implicitly passed as the level number.

            File f = new File(levelDataFilename);
            Scanner in = new Scanner(f);
            int levelIndex = 0;
            while (in.hasNextLine()) {
                String data = in.nextLine();
                String[] split = data.split("\\s");
                Level l1 = new Level(Character.getNumericValue(split[0].charAt(0)), Character.getNumericValue(split[0].charAt(1)), levelIndex + 1);
                for (int i = 1; i < split.length; i++) {
                    int pX = Character.getNumericValue(split[i].charAt(0));
                    int pY = Character.getNumericValue(split[i].charAt(1));
                    char color = split[i].charAt(2);
                    l1.grid[pX][pY] = color;
                    l1.pelletLocations.add(new Pellet(pX, pY, color));
                }
                levels[levelIndex++] = l1;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Unable to find file. You may need to update the path in DummyWindow.java!" + levelDataFilename);
        }
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