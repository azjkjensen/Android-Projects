package edu.byu.cs.superasteroids.model;

import android.util.Log;

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

    public Coordinate(String coordString) {
        String[] positions = coordString.split(",");
        if(positions.length != 2) Log.i("JsonDomParserExample", "Coordinate failed to create");
        else{
            mXPos = Integer.parseInt(positions[0]);
            mYPos = Integer.parseInt(positions[1]);
        }
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

    @Override
    public String toString() {
        return Integer.toString(mXPos) + "," + Integer.toString(mYPos);
    }
}
