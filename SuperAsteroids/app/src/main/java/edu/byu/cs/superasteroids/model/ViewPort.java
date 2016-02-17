package edu.byu.cs.superasteroids.model;

/**
 * Created by Jk on 2/17/2016.
 */
public class ViewPort {
    /**The x dimension of the viewport */
    private int mXDimension;
    /**The y dimension of the viewport */
    private int mYDimension;
    /**The background image for the level */
    private BackgroundImage mBackgroundImage;

    public int getXDimension() {
        return mXDimension;
    }

    public void setXDimension(int XDimension) {
        mXDimension = XDimension;
    }

    public int getYDimension() {
        return mYDimension;
    }

    public void setYDimension(int YDimension) {
        mYDimension = YDimension;
    }

    public BackgroundImage getBackgroundImage() {
        return mBackgroundImage;
    }

    public void setBackgroundImage(BackgroundImage backgroundImage) {
        mBackgroundImage = backgroundImage;
    }

    public ViewPort() {
    }

    public ViewPort(int XDimension, int YDimension, BackgroundImage backgroundImage) {
        mXDimension = XDimension;
        mYDimension = YDimension;
        mBackgroundImage = backgroundImage;
    }
}
