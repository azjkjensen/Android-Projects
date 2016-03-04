package edu.byu.cs.superasteroids.model;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.List;

import edu.byu.cs.superasteroids.content.ContentManager;
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

    private float mXPosition = 0;
    private float mYPosition = 0;
    /**The background image for the level */
    private List<BackgroundImage> mBackgroundImages;

    private MiniMap mMiniMap;

    //For testing
    int count = 0;

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

    public MiniMap getMiniMap() {
        return mMiniMap;
    }

    public ViewPort() {
        mXDimension = DrawingHelper.getGameViewWidth();
        mYDimension = DrawingHelper.getGameViewHeight();
        mMiniMap = new MiniMap();
        //Loads base image for every level.
        ContentManager.getInstance().loadImage("images/space.bmp");
    }

    public ViewPort(int XDimension, int YDimension, List<BackgroundImage> backgroundImages,
                    AsteroidsGameModel game) {
        mXDimension = XDimension;
        mYDimension = YDimension;
        mBackgroundImages = backgroundImages;
        mGame = game;
        mMiniMap = new MiniMap();
        //Loads base image for every level.
        ContentManager.getInstance().loadImage("images/space.bmp");
    }

    public Coordinate toViewCoordinates(Coordinate worldCoord){
        float xVPPos = worldCoord.getXPos() - mXPosition;
        float yVPPos = worldCoord.getYPos() - mYPosition;
        return new Coordinate(Math.round(xVPPos), Math.round(yVPPos));
    }

    public Coordinate toWorldCoordinates(Coordinate viewCoord){
        float xWPos = viewCoord.getXPos() + mXPosition;
        float yWPos = viewCoord.getYPos() + mYPosition;
        return new Coordinate(Math.round(xWPos), Math.round(yWPos));
    }

    public void update(){
        float proposedX = mGame.getSpaceShip().getXPosition() - (mXDimension / 2) + count;
        float proposedY = mGame.getSpaceShip().getYPosition() - (mYDimension / 2) + count;
        if(proposedX > 0 && proposedX + mXDimension < mGame.getCurrentLevel().getWidth()){
            mXPosition = proposedX;
        }
        if(proposedY > 0 && proposedY + mYDimension < mGame.getCurrentLevel().getHeight()) {
            mYPosition = proposedY;
        }

        mMiniMap.update();
    }

    public void draw(){
        int newXPos = Math.round((mXPosition / mGame.getCurrentLevel().getWidth()) * 1300);
        int newYPos = Math.round((mYPosition / mGame.getCurrentLevel().getHeight()) * 1500);
        int newXDim = Math.round(newXPos + (mXDimension));// * mGame.getCurrentLevel().getWidth()) / 2048);
        int newYDim = Math.round(newYPos + (mYDimension));// * mGame.getCurrentLevel().getWidth()) / 2048);

        DrawingHelper.drawImage(ContentManager.getInstance().getImageId("images/space.bmp"),
                new Rect(newXPos, newYPos, newXDim, newYDim), null);

        mMiniMap.draw();
    }

    public class MiniMap{

        public MiniMap(){

        }

        public void draw(){
            //Draw outer (border) rectangle
            DrawingHelper.drawFilledRectangle(
                    new Rect((int)(DrawingHelper.getGameViewWidth() * .8f) - 6, 0,
                            DrawingHelper.getGameViewWidth(), (int)(DrawingHelper.getGameViewHeight() * .2f) + 6),
                    Color.BLUE,
                    200
            );

            //Draw inner rectangle
            DrawingHelper.drawFilledRectangle(
                    new Rect((int)(DrawingHelper.getGameViewWidth() * .8f), 3,
                            DrawingHelper.getGameViewWidth() - 3, (int)(DrawingHelper.getGameViewHeight() * .2f)),
                    Color.BLACK,
                    100
            );
        }

        public void update(){

        }
    }
}
