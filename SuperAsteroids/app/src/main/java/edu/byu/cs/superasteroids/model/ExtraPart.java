package edu.byu.cs.superasteroids.model;

/**
 * This class stores information for the extra part of the ship.
 * Created by Jordan on 2/12/2016.
 */
public class ExtraPart {
    /**The coordinate of where to attach the extra part */
    Coordinate mAttachPoint;
    /**Image information for drawing the extra part */
    ViewableObject mViewableInfo;

    public ExtraPart() {
    }

    public ExtraPart(Coordinate attachPoint, ViewableObject viewableInfo) {
        mAttachPoint = attachPoint;
        mViewableInfo = viewableInfo;
    }

    public Coordinate getAttachPoint() {
        return mAttachPoint;
    }

    public void setAttachPoint(Coordinate attachPoint) {
        mAttachPoint = attachPoint;
    }

    public ViewableObject getViewableInfo() {
        return mViewableInfo;
    }

    public void setViewableInfo(ViewableObject viewableInfo) {
        mViewableInfo = viewableInfo;
    }

    /**
     * Draws the image associated with this object
     */
    public void draw(){

    }

    /**
     * Updates the information associated with this object
     */
    public void update(){

    }
}
