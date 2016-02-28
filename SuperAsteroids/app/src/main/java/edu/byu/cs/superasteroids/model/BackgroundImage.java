package edu.byu.cs.superasteroids.model;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;

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
    /**The id associated with this item in the database  */
    private int mObjectID;
    /** The id associated with this item in the contentManager */
    private int mImageID;

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

    public void setObjectID(int objectID) {
        mObjectID = objectID;
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
        if(mImagePath != null){
            int imageID = ContentManager.getInstance().getImageId(mImagePath);
            Coordinate positionInView = AsteroidsGameModel.getInstance()
                    .getViewPort().toViewCoordinates(mPosition);
            DrawingHelper.drawImage(imageID, positionInView.getXPos(), positionInView.getYPos(),
                    0, mScale, mScale, 255);
        }
    }
}
