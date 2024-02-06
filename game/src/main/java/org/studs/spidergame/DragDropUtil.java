package org.studs.spidergame;

import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;

import java.util.*;

public class DragDropUtil {
    private final AnchorPane blockPanel;
    private final AnchorPane dragPanel;
    private ActionBlock selected;
    private List<ActionBlock> tails; //list of blocks at the end of each chain
    private int blockCounter;
    private Rectangle projection;
    private double startX, startY;
    public static final double BLOCK_WIDTH = 100;
    public static final double BLOCK_HEIGHT = 100;
    public static final Map<Color, MoveType> colorMap = new HashMap<>();

    static {
        colorMap.put(Color.BLACK, MoveType.CLRBLACK);
        colorMap.put(Color.RED, MoveType.CLRRED);
        colorMap.put(Color.BLUE, MoveType.CLRBLUE);
        colorMap.put(Color.GREEN, MoveType.CLRGREEN);
    }

    /**
     * Converts coordinates from one Node to another.
     *
     * @param source The source Node.
     * @param target The target Node.
     * @param x      The X-coordinate.
     * @param y      The Y-coordinate.
     * @return The converted Point2D.
     */
    private static Point2D convertCoords(Node source, Node target, double x, double y) {
        Point2D sceneCoords = source.localToScene(x, y);
        return target.sceneToLocal(sceneCoords);
    }

    /**
     * Constructor for DragDropUtil.
     *
     * @param blockPanel The block panel.
     * @param dragPanel  The drag panel.
     */
    public DragDropUtil(AnchorPane blockPanel, AnchorPane dragPanel){
        this.blockPanel = blockPanel;
        this.dragPanel = dragPanel;

        selected = null;
        tails = new ArrayList<>();
        blockCounter = 0;
        projection = null;
        initDragPanel();
    }

    /**
     * Initializes the drag panel by clearing its children and populating it with draggable blocks
     * for each MoveType enumeration value.
     */
    private void initDragPanel(){
        dragPanel.getChildren().clear();
        double xOffset = 20;
        double yPos = 0;

        for(MoveType move : MoveType.values()){
            StackPane dragBlock = genPane(move);
            makeSideBlockDraggable(dragBlock, move);
            dragBlock.setLayoutX(xOffset);
            dragBlock.setLayoutY(yPos);
            dragPanel.getChildren().add(dragBlock);
            yPos += 105;
        }
    }

    /**
     * Adds a block to the block panel with the specified move type.
     *
     * @param pane The StackPane representing the visual block.
     * @param move The move type associated with the block.
     */
    public void addBlock(StackPane pane, MoveType move) {
        addBlock(new ActionBlock(move, pane), null, 20, 20);
    }

    /**
     * Adds a block to the block panel with the specified move type and attaches it to another block.
     *
     * @param block     The ActionBlock to add.
     * @param attachTo  The ActionBlock to attach to.
     */
    public void addBlock(ActionBlock block, ActionBlock attachTo) {
        addBlock(block, attachTo, 20, 20);
    }

    /**
     * Adds a block to the block panel with the specified move type.
     *
     * @param move The move type associated with the block.
     */
    public void addBlock(MoveType move) {
        addBlock(new ActionBlock(move, genPane(move)), null, 20, 20);
    }

    /**
     * Adds a block to the block panel with the specified move type and attaches it to another block.
     *
     * @param toAdd     The ActionBlock to add.
     * @param attachTo  The ActionBlock to attach to.
     * @param x         The X-coordinate of the block's position.
     * @param y         The Y-coordinate of the block's position.
     */
    public void addBlock(ActionBlock toAdd, ActionBlock attachTo, double x, double y) {
        ActionBlock finalAttach = selected;
        if(attachTo != null && attachTo != toAdd){
            finalAttach = attachTo;
        }

        if(finalAttach != null){ //adding block as child of selected
            ActionBlock tail = finalAttach.getTail();
            toAdd.setParent(tail);
            tail.setChild(toAdd);
            tail.move(tail.getVisualBlock().getLayoutX(), tail.getVisualBlock().getLayoutY());
            tails.remove(tail);
        }
        else{//creates new head block
            toAdd.getVisualBlock().setLayoutX(x);
            toAdd.getVisualBlock().setLayoutY(y);
            toAdd.updateRange();
        }

        if(!blockPanel.getChildren().contains(toAdd.getVisualBlock())){
            makeDraggable(toAdd);
            blockPanel.getChildren().add(toAdd.getVisualBlock());
        }

        tails.add(toAdd.getTail());
        selectNew(toAdd);

        blockCounter++;
    }

    /**
     * Makes a side block draggable within the drag panel.
     *
     * @param button The StackPane representing the draggable block.
     * @param move   The move type associated with the block.
     */
    private void makeSideBlockDraggable(StackPane button, MoveType move) {
        button.setOnMousePressed(e -> {
            button.setCursor(Cursor.CLOSED_HAND);
            startX = e.getSceneX() - button.getLayoutX();
            startY = e.getSceneY() - button.getLayoutY();
        });

        button.setOnMouseDragged(e -> {
            button.setLayoutX(e.getSceneX() - startX);
            button.setLayoutY(e.getSceneY() - startY);
            Point2D converted = DragDropUtil.convertCoords(dragPanel, blockPanel, button.getLayoutX(), button.getLayoutY());

            ActionBlock inRange = checkSnapRanges(converted.getX()+ (button.getWidth() / 2), converted.getY());
            clearProjection();
            if(inRange != null){
                projectBlockBelow(inRange);
            }
        });

        button.setOnMouseReleased(e -> {
            clearProjection();
            button.setCursor(Cursor.OPEN_HAND);

            if(button.getLayoutX() > -50){
                System.out.println("Out of range");
                initDragPanel();
                return;
            }

            Point2D converted = DragDropUtil.convertCoords(dragPanel, blockPanel, button.getLayoutX(), button.getLayoutY());

            ActionBlock inRange = checkSnapRanges(converted.getX() + (button.getWidth() / 2), converted.getY());
            deselect();
            addBlock(new ActionBlock(move, button), inRange, converted.getX(), converted.getY());

            dragPanel.getChildren().remove(button);
            initDragPanel();
        });
    }

    /**
     * Makes an action block draggable within the block panel.
     *
     * @param block The ActionBlock to make draggable.
     */
    private void makeDraggable(ActionBlock block) {
        StackPane node = block.getVisualBlock();

        node.setOnMousePressed(e -> {
            node.setCursor(Cursor.CLOSED_HAND);
            startX = e.getSceneX() - node.getLayoutX();
            startY = e.getSceneY() - node.getLayoutY();
            selectNew(block);
        });

        node.setOnMouseDragged(e -> {
            block.setBeingDragged(true);
            tails.remove(block.getTail());

            if(block.getParent() != null){
                tails.add(block.getParent());
                block.eraseParent();
            }

            block.move(e.getSceneX() - startX, e.getSceneY() - startY);

            ActionBlock inRange = checkSnapRanges(node.getLayoutX() + (node.getWidth() / 2), node.getLayoutY());
            clearProjection();
            if(inRange != null && block != inRange){
                projectBlockBelow(inRange);
            }
        });

        node.setOnMouseReleased(e -> {
            clearProjection();
            node.setCursor(Cursor.OPEN_HAND);

            if(!block.isBeingDragged()){
                return;
            }

            ActionBlock inRange = checkSnapRanges(node);
            deselect();
            addBlock(block, inRange, block.getVisualBlock().getLayoutX(), block.getVisualBlock().getLayoutY());

            block.updateRange();
            block.setBeingDragged(false);
        });
    }

    /**
     * Checks if a given block is within the snap ranges of other blocks.
     *
     * @param checkAgainst The StackPane to check against.
     * @return The ActionBlock within snap range, or null if none.
     */
    private ActionBlock checkSnapRanges(StackPane checkAgainst) {
        return checkSnapRanges(checkAgainst.getLayoutX() + (checkAgainst.getWidth() / 2), checkAgainst.getLayoutY());
    }

    /**
     * Checks if a given point is within the snap ranges of other blocks.
     *
     * @param x The X-coordinate of the point.
     * @param y The Y-coordinate of the point.
     * @return The ActionBlock within snap range, or null if none.
     */
    private ActionBlock checkSnapRanges(double x, double y) {
        for(ActionBlock b : tails){
            if(b.getSnapRange().inRange(x, y)){
                return b;
            }
        }
        return null;
    }

    /**
     * Generates a StackPane representing a draggable block based on the specified move type.
     *
     * @param move The move type associated with the block.
     * @return The generated StackPane.
     */
    private StackPane genPane(MoveType move) {
        String txt = "";
        Color clr = Color.GRAY;
        switch (move){
            case STEP -> txt = "Step";
            case TURN -> txt = "Turn";
            case CLRBLACK -> {txt = "Color"; clr = Color.BLACK;}
            case CLRRED -> {txt = "Color"; clr = Color.RED;}
            case CLRBLUE-> {txt = "Color"; clr = Color.BLUE;}
            case CLRGREEN -> {txt = "Color"; clr = Color.GREEN;}
        }

        Text text = new Text(txt + blockCounter);
        text.setFill(Color.WHITE);
        Rectangle rec = new Rectangle(100, 100);
        rec.setFill(clr);
        StackPane stack = new StackPane();
        stack.getChildren().addAll(rec, text);
        stack.setCursor(Cursor.OPEN_HAND);
        return stack;
    }

    /**
     * Projects a transparent block below the specified ActionBlock.
     *
     * @param actionBlock The ActionBlock above which to project the block.
     */
    private void projectBlockBelow(ActionBlock actionBlock) {
        Rectangle rec = new Rectangle(100, 100);
        rec.setFill(Color.TRANSPARENT);
        rec.setStroke(Color.BLACK);
        rec.setStrokeType(StrokeType.INSIDE);
        rec.setStrokeWidth(3);

        blockPanel.getChildren().add(rec);
        rec.setLayoutX(actionBlock.getVisualBlock().getLayoutX());
        rec.setLayoutY(actionBlock.getVisualBlock().getLayoutY() + actionBlock.getVisualBlock().getHeight());
        projection = rec;
    }

    /**
     * Clears the projection of the transparent block.
     */
    private void clearProjection() {
        if(projection != null){
            projection.setVisible(false);
            blockPanel.getChildren().remove(projection);
        }
        projection = null;
    }

    /**
     * Selects a new ActionBlock for interaction.
     *
     * @param newSelection The ActionBlock to be selected.
     */
    private void selectNew(ActionBlock newSelection) {
        deselect();
        selected = newSelection;
        newSelection.setHighlight(true);
    }

    /**
     * Deselects the currently selected ActionBlock.
     */
    private void deselect() {
        if(selected != null){
            selected.setHighlight(false);
        }
        selected = null;
    }
}
