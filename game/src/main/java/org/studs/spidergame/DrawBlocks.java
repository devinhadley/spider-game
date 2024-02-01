import java.fx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.event.EventHandler;

public class Main extends Application implements EventHandler<ActionEvent>{

    Button stepButton;
    Button paintButton;
    Button turnButton;
    Group layout;

    public static void main(String[] args,){}

    public void start(Stage primary Stage) {
        // Buttons to create Blocks
        stepButton = new Button("Step");
        paintButton = new Button("Paint");
        turnButton = new Button("Turn");

        // Placement of Buttons
        paintButton.setLayoutX(100);
        paintButton.setLayoutY(0);
        stepButton.setLayoutX(250);
        stepButton.setLayoutY(0);
        turnButton.setLayoutX(400);
        turnButton.setLayoutY(0);

        stepButton.setOnAction(this);
        paintButton.setOnAction(this);
        turnButton.setOnAction(this);
        layout = new Group();
        layout.getChildren().addAll(paintButton, stepButton, turnButton);

        Scene scene = new Scene(layout, 500, 500, Color.BLUE);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void handle(ActionEvent event){
        // When each button pressed, make a block for the button
        if (event.getSource() == stepButton) {
            makeRectangle("Step", Color.LIGHTGRAY);
        } else if (event.getSource() == paintButton) {
            makeRectangle("Paint", Color.RED);
        } else if (event.getSource() == turnButton) {
            makeRectangle("Turn", Color.LIGHTGRAY);
        }
    }

    public void makeRectangle(String type, Color color){
        Text text = new Text(type);
        Rectangle rec = new Rectangle(100, 100);
        rec.setFill(color);
        StackPane stack = new StackPane();
        stack.getChildren().addAll(rec, text);
        // Not sure where to position it yet
        stack.setLayoutX(100);
        stack.setLayoutY(100);
        layout.getChildren().add(stack);

    }



}
