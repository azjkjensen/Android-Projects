package edu.byu.cs.superasteroids.model;

import java.util.List;

import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by Jk on 2/17/2016.
 */
public class ViewPort {
    AsteroidsGameModel mGame;
    /**The x dimension of the viewport */
    private int mXDimension;
    /**The y dimension of the viewport */
    private int mYDimension;

    private float mXPosition;
    private float mYPosition;
    /**The background image for the level */
    private List<BackgroundImage> mBackgroundImages;

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

    public List<BackgroundImage> getBackgroundImages() {
        return mBackgroundImages;
    }

    public void setBackgroundImage(List<BackgroundImage> backgroundImages) {
        mBackgroundImages = backgroundImages;
    }

    public ViewPort() {
    }

    public ViewPort(int XDimension, int YDimension, List<BackgroundImage> backgroundImages,
                    AsteroidsGameModel game) {
        mXDimension = XDimension;
        mYDimension = YDimension;
        mBackgroundImages = backgroundImages;
        mGame = game;
    }

    public Coordinate toViewCoordinates(Coordinate worldCoord){
        float xVPPos = worldCoord.getXPos() - mXPosition;
        float yVPPos = worldCoord.getYPos() - mYPosition;
        return new Coordinate(Math.round(xVPPos), Math.round(yVPPos));
    }

    public void update(){
        float proposedX = mGame.getSpaceShip().getXPosition() - (mXDimension / 2);
        float proposedY = mGame.getSpaceShip().getYPosition() - (mYDimension / 2);
        if(proposedX > 0 && proposedX + mXDimension < mGame.getCurrentLevel().getWidth()){
            mXPosition = proposedX;
        }
        if(proposedY + mYDimension > 0 && proposedY < mGame.getCurrentLevel().getHeight()) {
            mYPosition = proposedY;
        }
    }

    public void draw(){

    }
}
