package edu.byu.cs.superasteroids.model;

/**
 * Created by Jordan on 2/12/2016.
 * This class stores the information for displaying a given image on a level.
 */
public class BackgroundImage {
    /**The x/y position of the image object in the level */
    private Coordinate mPosition;
    /**The image path to the image file for this object */
    private String mImagePath;
    /**A scalar multiple to scale the image by */
    private float mScale;
    /** */
    private int mObjectID;

    public BackgroundImage() {
    }

    public BackgroundImage(Coordinate position, String imagePath, float scale, int objectID) {
        mPosition = position;
        mImagePath = imagePath;
        mScale = scale;
        mObjectID = objectID;
    }

    public Coordinate getPosition() {
        return mPosition;
    }

    public String getPositionString(){
        return mPosition.toString();
    }

    public void setPosition(Coordinate position) {
        mPosition = position;
    }

    public String getImagePath() {
        return mImagePath;
    }

    public void setImagePath(String imagePath) {
        mImagePath = imagePath;
    }

    public float getScale() {
        return mScale;
    }

    public void setScale(float scale) {
        mScale = scale;
    }

    public int getObjectID() {
        return mObjectID;
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
