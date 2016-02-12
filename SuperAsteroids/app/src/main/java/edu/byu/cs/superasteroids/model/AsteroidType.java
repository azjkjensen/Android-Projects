package edu.byu.cs.superasteroids.model;

/**
 * Created by Jk on 2/12/2016.
 * This class contains each asteroid type available to the game to use.
 */
public class AsteroidType {
    /**The name of the asteroid. */
    String mName;
    /**The type of asteroid */
    String mType;
    /**The image information for the asteroid image. */
    ViewableObject mViewableInfo;

    /**
    * Default Constructor
     */
    public AsteroidType(){
    }

    /**
     * Field filling constructor
     * @param name
     * @param type
     * @param viewableInfo
     */
    public AsteroidType(String name, String type, ViewableObject viewableInfo) {
        mName = name;
        mType = type;
        mViewableInfo = viewableInfo;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
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
