package com.example.spiderworld2;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class DummyWindow extends Application {
    LevelDriver ld = new LevelDriver("LevelData.txt");
    private Label messageLabel = new Label();
    @Override
    public void start(Stage primaryStage) {
        // Create buttons
        Button button1 = new Button("1");
        Button button2 = new Button("2");
        Button button3 = new Button("3");
        Button button4 = new Button("4");
        Button button5 = new Button("5");
        Button button6 = new Button("6");

        // Create an HBox to hold the buttons
        HBox buttonBox = new HBox(10); // 10 pixels spacing between buttons
        buttonBox.getChildren().addAll(button1, button2, button3, button4, button5, button6, messageLabel);

        // Set event handlers for each button
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                displayLevelData(1);
            }
        });

        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                displayLevelData(2);
            }
        });

        button3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                displayLevelData(3);
            }
        });

        button4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                displayLevelData(4);
            }
        });

        button5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                displayLevelData(5);
            }
        });

        button6.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                displayLevelData(6);
            }
        });

        // Create the main layout (VBox)
        javafx.scene.layout.VBox root = new javafx.scene.layout.VBox();
        root.getChildren().addAll(buttonBox); // Add the button box to the main layout

        // Create the scene
        Scene scene = new Scene(root, 1000, 1000);

        // Set the stage properties
        primaryStage.setTitle("Dummy Window - Miro");
        primaryStage.setScene(scene);

        // Show the stage
        primaryStage.show();
    }

    public void displayLevelData(int levelNumber) {
        int[] spawnLoc = ld.levels[levelNumber-1].getSpawnLoc();
        ArrayList<PelletData> pd = ld.levels[levelNumber-1].pelletLocations;
        String msg = "Level " + levelNumber + "\nSpawn location: " + spawnLoc[0] + " " + spawnLoc[1] +
                "\n";
        for (PelletData pelletData : pd) {
            msg += ("\n" + pelletData);
        }
        messageLabel.setText(msg);
    }

    public static void main(String[] args) {
        launch();
    }
}