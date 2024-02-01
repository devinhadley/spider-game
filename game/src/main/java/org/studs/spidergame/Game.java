package org.studs.spidergame;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    final String path = "/home/devinhadley/dev/308/spider-game/game/LevelData.txt";
    private final Level[] levels = new Level[15];
    private Level currentLevel;
    private final Rectangle[][] gridBoxes = new Rectangle[5][5];
    private final ArrayList<Rectangle> pellets = new ArrayList<>();

    @FXML
    private final Circle spider;

    private final double GRID_BOX_RADIUS = 31.5;

    private int yPos = 0;


    private AnchorPane gridAnchor;
    private AnchorPane drawPanel;



    public Game(Circle spider, AnchorPane gridAnchor, AnchorPane drawPanel) {
        populateLevelsFromFile();
        currentLevel = levels[0];
        this.gridAnchor = gridAnchor;
        this.spider = spider;
        this.drawPanel = drawPanel;
        renderGrid();
    }

    private void clearPellets() {
        ObservableList<Node> nodes = gridAnchor.getChildren();
        for (Rectangle pellet : pellets) {
            nodes.remove(pellet);
        }
    }

    private void renderGrid() {
        clearPellets();
        populateGridBoxes();
        moveSpiderToStart();
        placePellets();
    }


    private void placePellets() {
        for (Pellet pellet : currentLevel.pelletLocations) {
            Rectangle diamond = new Rectangle(15, 15);

            diamond.setRotate(45);
            diamond.setFill(pellet.getColor());

            Rectangle gridRect = gridBoxes[pellet.x][pellet.y];

            double[] center = getRectCenter(gridRect);

            diamond.setLayoutX(center[0] - diamond.getWidth() / 2);
            diamond.setLayoutY(center[1] - diamond.getHeight() / 2);

            pellets.add(diamond);
            gridAnchor.getChildren().add(diamond);


        }
    }

    private void populateGridBoxes() {
        int row = 0, col = 0;

        for (Node node : gridAnchor.getChildren()) {
            if (node instanceof Rectangle) {
                gridBoxes[row][col] = (Rectangle) node;
                col++;
                if (col == 5) {
                    col = 0;
                    row++;
                }
            }
            if (row == 5) break;
        }
    }

    private void makeRectangle(String type, Color color) {
        Text text = new Text(type);
        Rectangle rec = new Rectangle(100, 100);
        rec.setFill(color);
        StackPane stack = new StackPane();
        stack.getChildren().addAll(rec, text);
        stack.setLayoutY(yPos);
        drawPanel.getChildren().add(stack);
    }

    private double[] getRectCenter(Rectangle rect) {
        double[] list = new double[2];
        list[0] = rect.getLayoutX() + rect.getWidth() / 2;
        list[1] = rect.getLayoutY() + rect.getHeight() / 2;
        return list;
    }

    private void moveSpiderToStart() {
        int startX = currentLevel.getSpawnLoc()[0];
        int startY = currentLevel.getSpawnLoc()[1];

        Rectangle gridRect = gridBoxes[startX][startY];

        double[] center = getRectCenter(gridRect);

        spider.setLayoutX(center[0]);
        spider.setLayoutY(center[1]);
    }


    private void populateLevelsFromFile() {
        try {

            // This parser assumes that each line in the input file, corresponding to a single level, is formatted as such:
            // "22 43r 12b 45g" Corresponds to a level with a spawn location at coordinate 2, 2,
            // a red pellet at coordinate 4, 3, a blue pellet at 1, 2,
            // and a green pellet at 4, 5. The line number is implicitly passed as the level number.

            File f = new File(path);
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
            System.out.println("Unable to find file. You may need to update the path in DummyWindow.java!\n" + path);
        }
    }

    protected void onStep() {
        makeRectangle("Step", Color.GRAY);
        yPos += 100;
    }

    protected void onTurn() {
        makeRectangle("Turn", Color.GRAY);
        yPos += 100;
    }

    protected void onPaint(Color color) {
        makeRectangle("Paint", color);
        yPos += 100;
    }

    protected void changeLevel(int levelNumber) {
        currentLevel = levels[levelNumber - 1];
        renderGrid();
    }


}
