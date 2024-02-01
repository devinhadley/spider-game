package org.studs.spidergame;

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
    private AnchorPane myRoot;

    private int xpos = 100;



    @FXML private void createAndAddRectangles() {
        /**myRoot is already instantiated. you can simply add nodes to it at runtime
         by using onAction="createAndAddRectangles" tag on a button in your .fxml file.**/
    }

    private void makeRectangle(String type, Color color){
        Text text = new Text(type);
        Rectangle rec = new Rectangle(100, 100);
        rec.setFill(color);
        StackPane stack = new StackPane();
        stack.getChildren().addAll(rec, text);
        // Not sure where to position it yet
        stack.setLayoutX(xpos);
        stack.setLayoutY(100);
        xpos += 100;
        myRoot.getChildren().add(stack);
    }

    @FXML protected void onStep(){
    }
    @FXML protected void onTurn(){
    }
    @FXML protected void onPaint(){
        makeRectangle("Paint", Color.RED);
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }


}