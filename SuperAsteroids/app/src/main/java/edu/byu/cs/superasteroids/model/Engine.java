package edu.byu.cs.superasteroids.model;

/**
 * Created by Jordan on 2/12/2016.
 * This class stores all of the information for the engine part of the ship.
 */
public class Engine {
    /**The maximum velocity of the ship (pixels per second) */
    int mBaseSpeed;
    /**The turn rate of the ship (degrees per second) */
    int mBaseTurnRate;
    /**Coordinate of where to attach the engine to the ship */
    Coordinate mAttachPoint;
    /**Image information for drawing the engine */
    ViewableObject mViewableInfo;

    public Engine() {
    }

    public Engine(int baseSpeed, int baseTurnRate, Coordinate attachPoint, ViewableObject viewableInfo) {
        mBaseSpeed = baseSpeed;
        mBaseTurnRate = baseTurnRate;
        mAttachPoint = attachPoint;
        mViewableInfo = viewableInfo;
    }

    public int getBaseSpeed() {
        return mBaseSpeed;
    }

    public void setBaseSpeed(int baseSpeed) {
        mBaseSpeed = baseSpeed;
    }

    public int getBaseTurnRate() {
        return mBaseTurnRate;
    }

    public void setBaseTurnRate(int baseTurnRate) {
        mBaseTurnRate = baseTurnRate;
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

