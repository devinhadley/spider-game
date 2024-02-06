package org.studs.spidergame;

public class SnapRange {
    private final double ogX, ogY;
    private double maxX, maxY, minX, minY;
    private static final int DEFAULT_SNAP_RANGE = 100;

    /**
     * Constructor for SnapRange with specified x, y, and range.
     *
     * @param x The original X-coordinate.
     * @param y The original Y-coordinate.
     * @param range The snap range.
     */
    public SnapRange(double x, double y, double range){
        ogX = x;
        ogY = y;
        setRange(range);
    }

    /**
     * Constructor for SnapRange with specified x, y, and default snap range.
     *
     * @param x The original X-coordinate.
     * @param y The original Y-coordinate.
     */
    public SnapRange(double x, double y){
        this(x, y, DEFAULT_SNAP_RANGE);
    }

    /**
     * Checks if the given coordinates are within the snap range.
     *
     * @param x The X-coordinate.
     * @param y The Y-coordinate.
     * @return True if within snap range, false otherwise.
     */
    public boolean inRange(double x, double y){
        return x < maxX && y < maxY && x > minX && y > minY;
    }

    /**
     * Sets the snap range based on the specified range.
     *
     * @param range The snap range.
     */
    public void setRange(double range){
        maxX = ogX + range;
        maxY = ogY + range;
        minX = ogX - range;
        minY = ogY - range;
    }
}
