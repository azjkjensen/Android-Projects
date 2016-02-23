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
    /** The direction the Asteroid is moving */
    int mDirection;
    /**The speed of the Asteroid, in pixels/second */
    int mSpeed;
    /**The number of hitpoints remaining on the Asteroid */
    int mHitPoints;
    /**The id associated with this item in the database */
    int mID;

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
    public AsteroidType(String name, String type, ViewableObject viewableInfo, int id) {
        mName = name;
        mType = type;
        mViewableInfo = viewableInfo;
        mID = id;
    }

    public int getID() {
        return mID;
    }

    public void setID(int ID) {
        mID = ID;
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

    public int getDirection() {
        return mDirection;
    }

    public void setDirection(int direction) {
        mDirection = direction;
    }

    public int getSpeed() {
        return mSpeed;
    }

    public void setSpeed(int speed) {
        mSpeed = speed;
    }

    public int getHitPoints() {
        return mHitPoints;
    }

    public void setHitPoints(int hitPoints) {
        mHitPoints = hitPoints;
    }

    /**
     * Accounts for when the asteroid takes a hit.
     */
    public void takeHit(){
        mHitPoints--;
    }

    /**
     * @return Whether the asteroid is ready to split or not.
     */
    public boolean shouldSplit(){
        return false;
    }

    /**
     *
     * @return whether the asteroid has been shot enough to explode.
     */
    public boolean shouldExplode(){
        if(mHitPoints == 0) {
            return true;
        }
        else return false;
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
