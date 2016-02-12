package edu.byu.cs.superasteroids.model;

/**
 * Created by Jordan on 2/12/2016.
 * This class stores all information for the main body part of the ship.
 */
public class MainBody {
    /**The attach point for the cannon */
    Coordinate mCannonAttach;
    /**The attach point for the engine */
    Coordinate mEngineAttach;
    /**The attach point for the extra part */
    Coordinate mExtraAttach;
    /**The image information for the main body */
    ViewableObject mViewableInfo;

    public MainBody() {
    }

    public MainBody(Coordinate cannonAttach, Coordinate engineAttach, Coordinate extraAttach, ViewableObject viewableInfo) {
        mCannonAttach = cannonAttach;
        mEngineAttach = engineAttach;
        mExtraAttach = extraAttach;
        mViewableInfo = viewableInfo;
    }

    public Coordinate getCannonAttach() {
        return mCannonAttach;
    }

    public void setCannonAttach(Coordinate cannonAttach) {
        mCannonAttach = cannonAttach;
    }

    public Coordinate getEngineAttach() {
        return mEngineAttach;
    }

    public void setEngineAttach(Coordinate engineAttach) {
        mEngineAttach = engineAttach;
    }

    public Coordinate getExtraAttach() {
        return mExtraAttach;
    }

    public void setExtraAttach(Coordinate extraAttach) {
        mExtraAttach = extraAttach;
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
