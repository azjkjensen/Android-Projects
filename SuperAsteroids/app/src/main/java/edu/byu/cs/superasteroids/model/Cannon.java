package edu.byu.cs.superasteroids.model;

/**
 * Created by Jordan on 2/12/2016.
 * This class stores all information for a cannon to attach to the ship.
 */
public class Cannon {
    /**The coordinate where the cannon attaches to the ship */
    Coordinate mAttachPoint;
    /**The coordinate where the laser emits from. */
    Coordinate mEmitPoint;
    /**The image information for the cannon */
    ViewableObject mMainViewableInfo;

    public Cannon() {
    }

    public Cannon(Coordinate attachPoint, Coordinate emitPoint,
                  ViewableObject mainViewableInfo, ViewableObject attackViewableInfo,
                  String attackSound, int damage) {
        mAttachPoint = attachPoint;
        mEmitPoint = emitPoint;
        mMainViewableInfo = mainViewableInfo;
    }

    public Coordinate getAttachPoint() {
        return mAttachPoint;
    }

    public void setAttachPoint(Coordinate attachPoint) {
        mAttachPoint = attachPoint;
    }

    public Coordinate getEmitPoint() {
        return mEmitPoint;
    }

    public void setEmitPoint(Coordinate emitPoint) {
        mEmitPoint = emitPoint;
    }

    public ViewableObject getMainViewableInfo() {
        return mMainViewableInfo;
    }

    public void setMainViewableInfo(ViewableObject mainViewableInfo) {
        mMainViewableInfo = mainViewableInfo;
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
