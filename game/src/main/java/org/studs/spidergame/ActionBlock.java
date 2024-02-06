package org.studs.spidergame;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;

public class ActionBlock {
    private ActionBlock parent;
    private ActionBlock child;
    private StackPane visualBlock;
    private SnapRange snapRange;
    private boolean beingDragged;
    private MoveType move;

    /**
     * Constructor for ActionBlock with parent, child, and visualBlock.
     */
    public ActionBlock(MoveType move, ActionBlock parent, ActionBlock child, StackPane visualBlock) {
        this.move = move;
        this.parent = parent;
        this.child = child;
        this.visualBlock = visualBlock;
        updateRange();
    }

    /**
     * Constructor for ActionBlock with only move and visualBlock.
     */
    public ActionBlock(MoveType move, StackPane visualBlock) {
        this(move, null, null, visualBlock);
    }

    /**
     * Constructor for ActionBlock with move, parent, and visualBlock.
     */
    public ActionBlock(MoveType move, ActionBlock parent, StackPane visualBlock) {
        this(move, parent, null, visualBlock);
    }

    /**
     * Updates the snap range of the action block based on its current position.
     */
    public void updateRange() {
        snapRange = new SnapRange(visualBlock.getLayoutX() + (DragDropUtil.BLOCK_WIDTH / 2), visualBlock.getLayoutY() + DragDropUtil.BLOCK_HEIGHT);
    }

    public ActionBlock getChild() {
        return child;
    }

    public void setChild(ActionBlock child) {
        this.child = child;
    }

    public ActionBlock getParent() {
        return parent;
    }

    public void setParent(ActionBlock parent) {
        this.parent = parent;
    }

    /**
     * Returns the tail of the action block's hierarchy.
     *
     * @return The tail action block in the hierarchy.
     */
    public ActionBlock getTail() {
        if(child == null){
            return this;
        }
        return child.getTail();
    }

    /**
     * Erases the parent of the action block if it exists.
     */
    public void eraseParent() {
        if(parent != null){
            this.parent.setChild(null);
        }
        this.parent = null;
    }

    /**
     * Returns the move type associated with the action block.
     *
     * @return The move type.
     */
    public MoveType getMoveType() {
        return move;
    }

    /**
     * Returns the visual block associated with the action block.
     *
     * @return The visual block.
     */
    public StackPane getVisualBlock() {
        return visualBlock;
    }

    /**
     * Returns the snap range associated with the action block.
     *
     * @return The snap range.
     */
    public SnapRange getSnapRange() {
        return snapRange;
    }

    /**
     * Sets the highlight status of the visual block.
     *
     * @param highlighted True if highlighted, false otherwise.
     */
    public void setHighlight(boolean highlighted) {
        for(Node node : visualBlock.getChildren()){
            if (node instanceof Rectangle block){
                block.setStroke(Color.YELLOW);
                block.setStrokeType(StrokeType.INSIDE);
                if(highlighted){
                    block.setStrokeWidth(5);
                }
                else{
                    block.setStrokeWidth(0);
                }
            }
        }
    }

    /**
     * Moves the action block (and its children recursively) by the specified offset.
     *
     * @param offsetX The X-axis offset.
     * @param offsetY The Y-axis offset.
     */
    public void move(double offsetX, double offsetY) {
        visualBlock.setLayoutX(offsetX);
        visualBlock.setLayoutY(offsetY);
        updateRange();
        if(child != null){
            child.move(offsetX, offsetY + getVisualBlock().getHeight());
        }
    }

    /**
     * Returns the text content of the visual block.
     *
     * @return The text content.
     */
    public String getText() {
        for(Node node : visualBlock.getChildren()){
            if (node instanceof Text txt){
                return txt.getText();
            }
        }
        return null;
    }

    /**
     * Checks if the action block is being dragged.
     *
     * @return True if being dragged, false otherwise.
     */
    public boolean isBeingDragged() {
        return beingDragged;
    }

    /**
     * Sets the drag status of the action block.
     *
     * @param b True if being dragged, false otherwise.
     */
    public void setBeingDragged(boolean b) {
        beingDragged = b;
    }

    /**
     * Executes the game actions associated with the action block and its children recursively.
     *
     * @param game The game instance.
     */
    public void execute(Game game) {
        // game.makeMove(move); // This will execute the actions of each block recursively
        if(child != null){
            child.execute(game);
        }
    }
}
