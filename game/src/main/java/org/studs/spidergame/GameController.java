package org.studs.spidergame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameController {

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
    private Circle spider;

    private Game game;

    @FXML
    private void initialize() {
        this.game = new Game(spider, gridAnchor, drawBox);
    }

    @FXML
    protected void onStep() {
        game.onStep();
    }

    @FXML
    protected void onTurn() {
        game.onTurn();
    }

    @FXML
    protected void onPaint(ActionEvent event) {
        Object source = event.getSource();

        if (source == blackButton) {
            game.onPaint(Color.BLACK);
        } else if (source == redButton) {
            game.onPaint(Color.RED);
        } else if (source == blueButton) {
            game.onPaint(Color.BLUE);
        } else if (source == greenButton) {
            game.onPaint(Color.GREEN);
        } else {
            throw new RuntimeException("onPaint called from non color button!");
        }
    }

    protected int extractLevel(String eventString){
        Pattern pattern = Pattern.compile("'(.*?)'");
        Matcher matcher = pattern.matcher(eventString);
        if(matcher.find()){
            String level = matcher.group(1);
            return Integer.parseInt(level);
        };
        throw new RuntimeException("Couldn't extract level from button!");
    }

    @FXML
    protected void onChangeLevel(ActionEvent event) {
        game.changeLevel(extractLevel(event.toString()));
    }
}