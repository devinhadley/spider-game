package com.example.spiderworld2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// Miro Haapalainen

public class LevelDriver {
    public Level[] levels = new Level[15];

    public LevelDriver(String levelDataFilename) {
        parseDataFile(levelDataFilename);
    }

    public void parseDataFile(String levelDataFilename) {
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
                Level l1 = new Level(Character.getNumericValue(split[0].charAt(0)), Character.getNumericValue(split[0].charAt(1)), levelIndex+1);
                for (int i = 1; i < split.length; i++) {
                    int pX = Character.getNumericValue(split[i].charAt(0));
                    int pY = Character.getNumericValue(split[i].charAt(1));
                    char color = split[i].charAt(2);
                    l1.grid[pX][pY] = color;
                    l1.pelletLocations.add(new PelletData(pX, pY, color));
                }
                levels[levelIndex++] = l1;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Unable to find file " + levelDataFilename);
        }
    }
}