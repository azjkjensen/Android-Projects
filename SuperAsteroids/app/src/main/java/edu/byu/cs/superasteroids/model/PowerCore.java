package edu.byu.cs.superasteroids.model;

/**
 * Created by Jordan on 2/12/2016.
 * This class stores all information for the power core part of the ship.
 */
public class PowerCore {
    /**The boost off the cannon */
    int mCannonBoost;
    /**The boost of the engine */
    int mEngineBoost;
    /**The filepath for the image */
    String mImage;
    /**The id associated with this item in the content manager */
    int mImageID;

    public PowerCore() {
    }

    /**
     *
     * @param image
     * @param engineBoost
     * @param cannonBoost
     */
    public PowerCore(String image, int engineBoost, int cannonBoost) {
        mImage = image;
        mEngineBoost = engineBoost;
        mCannonBoost = cannonBoost;
    }

    public int getCannonBoost() {
        return mCannonBoost;
    }

    public void setCannonBoost(int cannonBoost) {
        mCannonBoost = cannonBoost;
    }

    public int getEngineBoost() {
        return mEngineBoost;
    }

    public void setEngineBoost(int engineBoost) {
        mEngineBoost = engineBoost;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public int getImageID() {
        return mImageID;
    }

    public void setImageID(int imageID) {
        mImageID = imageID;
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
