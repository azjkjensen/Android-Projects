package edu.byu.cs.superasteroids.model;

/**
 * Created by Jordan on 2/12/2016.
 * This class stores the information for displaying a given image on a level.
 */
public class LevelObject {
    /**The x/y position of the image object in the level */
    Coordinate mPosition;
    /**The image path to the image file for this object */
    String mImagePath;
    /**A scalar multiple to scale the image by */
    float mScale;

    public LevelObject() {
    }

    public LevelObject(Coordinate position, String imagePath, float scale) {
        mPosition = position;
        mImagePath = imagePath;
        mScale = scale;
    }

    public Coordinate getPosition() {
        return mPosition;
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
