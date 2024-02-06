/**
 * The {@code Game} class represents the core functionality of the spider game, handling game initialization,
 * level management, grid rendering, and game actions. It interacts with JavaFX components to display the game state
 * in a graphical user interface.
 */
package org.studs.spidergame;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    /**
     * Path to the level data file.
     */
    final String path = "C:/Users/ethan/Box Sync/Cal Poly/2023-2024/Winter Quarter/CSC 308/Assignment 1/CodeCity/FileParser/CodeCityJParser/spider-game/game/LevelData.txt";

    /**
     * Array holding all levels available in the game.
     */
    private final Level[] levels = new Level[15];

    /**
     * The current level the player is on.
     */
    private Level currentLevel;

    /**
     * Two-dimensional array representing the grid boxes on the game board.
     */
    private final Rectangle[][] gridBoxes = new Rectangle[5][5];

    /**
     * List of rectangles representing pellets on the game board.
     */
    private final ArrayList<Rectangle> pellets = new ArrayList<>();

    /**
     * The spider character, represented as a circle.
     */
    @FXML
    private final Circle spider;

    /**
     * Radius of each grid box.
     */
    private final double GRID_BOX_RADIUS = 31.5;

    /**
     * AnchorPane representing the grid on which the game is played.
     */
    private AnchorPane gridAnchor;

    /**
     * AnchorPane used for drawing additional elements like steps, turns, etc.
     */
    //private AnchorPane drawPanel;
    private DragDropUtil dragDropUtil;

    /**
     * Constructs a new Game instance, initializing the game with a spider, grid, and drawing panel.
     *
     * @param spider     the spider character
     * @param gridAnchor the grid AnchorPane
     * @param drawPanel  the drawing panel AnchorPane
     */
    public Game(Circle spider, AnchorPane gridAnchor, AnchorPane drawPanel, AnchorPane dragPanel) {
        populateLevelsFromFile();
        currentLevel = levels[0];
        this.gridAnchor = gridAnchor;
        this.spider = spider;
        this.dragDropUtil = new DragDropUtil(drawPanel, dragPanel);
        renderGrid();
    }

    /**
     * Clears pellets from the game board.
     */
    private void clearPellets() {
        ObservableList<Node> nodes = gridAnchor.getChildren();
        for (Rectangle pellet : pellets) {
            nodes.remove(pellet);
        }
    }

    /**
     * Renders the grid for the current level, placing the spider and pellets.
     */
    private void renderGrid() {
        clearPellets();
        populateGridBoxes();
        moveSpiderToStart();
        placePellets();
    }

    /**
     * Places pellets on the grid based on the current level's pellet locations.
     */
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

    /**
     * Populates the gridBoxes array with Rectangle nodes from the gridAnchor's children.
     */
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

    /**
     * Calculates the center coordinates of a given Rectangle.
     *
     * @param rect the Rectangle to calculate the center for
     * @return an array of two doubles representing the center's x and y coordinates
     */
    private double[] getRectCenter(Rectangle rect) {
        double[] list = new double[2];
        list[0] = rect.getLayoutX() + rect.getWidth() / 2;
        list[1] = rect.getLayoutY() + rect.getHeight() / 2;
        return list;
    }

    /**
     * Moves the spider to the starting location of the current level.
     */
    private void moveSpiderToStart() {
        int startX = currentLevel.getSpawnLoc()[0];
        int startY = currentLevel.getSpawnLoc()[1];

        Rectangle gridRect = gridBoxes[startX][startY];

        double[] center = getRectCenter(gridRect);

        spider.setLayoutX(center[0]);
        spider.setLayoutY(center[1]);
    }

    /**
     * Populates the levels array by reading level data from a file.
     */
    private void populateLevelsFromFile() {
        try {
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

    /**
     * Handles the action to be taken on stepping.
     */
    protected void onStep() {
        dragDropUtil.addBlock(MoveType.STEP);
        //TO-DO: execute game actions
    }

    /**
     * Handles the action to be taken on turning.
     */
    protected void onTurn() {
        dragDropUtil.addBlock(MoveType.TURN);
        //TO-DO: execute game actions
    }

    /**
     * Handles the action to be taken on painting, creating a rectangle of the specified color.
     *
     * @param color the color to paint
     */
    protected void onPaint(Color color) {
        dragDropUtil.addBlock(DragDropUtil.colorMap.get(color));
        //TO-DO: execute game actions
    }

    /**
     * Changes the current level to the specified level number, re-rendering the grid accordingly.
     *
     * @param levelNumber the level number to switch to
     */
    protected void changeLevel(int levelNumber) {
        currentLevel = levels[levelNumber - 1];
        renderGrid();
    }
}
