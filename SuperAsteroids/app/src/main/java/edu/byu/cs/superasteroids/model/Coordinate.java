package edu.byu.cs.superasteroids.model;

/**
 * Created by Jordan on 2/12/2016.
 * This class stores information regarding a coordinate of a given image.
 */
public class Coordinate {
    /**The x position in pixels */
    int mXPos;
    /**The y position in pixels */
    int mYPos;

    public Coordinate() {
    }

    public Coordinate(int XPos, int YPos) {
        mXPos = XPos;
        mYPos = YPos;
    }

    public int getXPos() {
        return mXPos;
    }

    public void setXPos(int XPos) {
        mXPos = XPos;
    }

    public int getYPos() {
        return mYPos;
    }

    public void setYPos(int YPos) {
        mYPos = YPos;
    }
}
