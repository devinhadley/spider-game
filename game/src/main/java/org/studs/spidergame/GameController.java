package org.studs.spidergame;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameController {
    @FXML
    private Label welcomeText;

    @FXML
    private AnchorPane drawBox;

    @FXML
    AnchorPane gridAnchor;

    @FXML
    private Button redButton;

    @FXML
    private Button blueButton;

    @FXML
    private Button greenButton;

    @FXML
    private Button blackButton;

    @FXML
    private Rectangle diamond;

    @FXML
    private Circle spider;

    private Level[] levels = new Level[15];

    private Level currentLevel;

    private int yPos = 0;

    private Rectangle[][] gridBoxes = new Rectangle[5][5];

    private ArrayList<Rectangle> pellets = new ArrayList<>();

    private final double GRID_BOX_RADIUS = 31.5;


    @FXML
    private void initialize() {
        final String path = "/home/devinhadley/dev/308/spider-game/game/LevelData.txt";
        Level.populateLevelsFromFile(path, levels);
        currentLevel = levels[0];
        renderGrid();
    }


    private void clearPellets() {
        ObservableList<Node> nodes = gridAnchor.getChildren();
        for (Rectangle pellet : pellets){
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
            Rectangle diamond = new Rectangle(15, 15); // Diamond size

            diamond.setRotate(45);
            diamond.setFill(pellet.getColor());

            Rectangle gridRect = gridBoxes[pellet.x][pellet.y];

            double[] center = getRectCenter(gridRect);

            // Adjust diamond's position to account for its size, ensuring it's centered
            diamond.setLayoutX(center[0] - diamond.getWidth() / 2);
            diamond.setLayoutY(center[1] - diamond.getHeight() / 2);

            pellets.add(diamond);
            gridAnchor.getChildren().add(diamond);


        }
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
        drawBox.getChildren().add(stack);
    }

    @FXML
    protected void onStep() {
        makeRectangle("Step", Color.GRAY);
        yPos += 100;
    }

    @FXML
    protected void onTurn() {
        makeRectangle("Turn", Color.GRAY);
        yPos += 100;
    }

    @FXML
    protected void onPaint(ActionEvent event) {
        Object source = event.getSource();

        if (source == blackButton) {
            makeRectangle("Paint", Color.BLACK);
        } else if (source == redButton) {
            makeRectangle("Paint", Color.RED);
        } else if (source == blueButton) {
            makeRectangle("Paint", Color.BLUE);
        } else if (source == greenButton) {
            makeRectangle("Paint", Color.GREEN);
        } else {
            throw new RuntimeException("onPaint called from non color button!");
        }

        yPos += 100;

    }

    @FXML
    protected void onChangeLevel(ActionEvent event) {
        Pattern pattern = Pattern.compile("'(.*?)'");
        Matcher matcher = pattern.matcher(event.getSource().toString());

        while (matcher.find()) {
            String level = matcher.group(1);
            int levelNumber = Integer.parseInt(level);
            currentLevel = levels[levelNumber - 1];
            renderGrid();
        }

    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}