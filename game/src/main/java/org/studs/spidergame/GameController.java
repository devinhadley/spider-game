package org.studs.spidergame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class GameController {
    @FXML
    private Label welcomeText;

    @FXML
    private AnchorPane drawBox;

    @FXML
    private Button redButton;

    @FXML
    private Button blueButton;

    @FXML
    private Button greenButton;

    @FXML
    private Button blackButton;

    private int yPos = 0;


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
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }


}