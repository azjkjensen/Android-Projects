package edu.byu.cs.superasteroids.model;

import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by Jk on 2/12/2016.
 * This class represents the laser image that shoots from the ship.
 */
public class Laser {
    /** The image information for the laser */
    ViewableObject mAttackViewableInfo;
    /**The filepath for the sound associated with the laser */
    String mAttackSound;
    /**The number of damage that the laser does when it hits an object. */
    int mDamage;
    /**The id associated with this item in the content manager */
    int mAttackSoundID;
    /** The position of the laser */
    Coordinate mPosition;
    private int mSpeed = 1;
    private float mDirection;

    public Laser() {
    }

    public Laser(ViewableObject attackViewableInfo, String attackSound, int attackSoundID, int damage) {
        mAttackViewableInfo = attackViewableInfo;
        mAttackSound = attackSound;
        mAttackSoundID = attackSoundID;
        mDamage = damage;
    }

    public Laser(Laser laserIn){
        mAttackViewableInfo = laserIn.getAttackViewableInfo();
        mAttackSound = laserIn.getAttackSound();
        mAttackSoundID = laserIn.getAttackSoundID();
        mPosition = new Coordinate(0, 0);
    }

    public Laser(ViewableObject attackViewableInfo, String attackSound, int damage) {
        mAttackViewableInfo = attackViewableInfo;
        mAttackSound = attackSound;
        mDamage = damage;
    }

    public ViewableObject getAttackViewableInfo() {
        return mAttackViewableInfo;
    }

    public void setAttackViewableInfo(ViewableObject attackViewableInfo) {
        mAttackViewableInfo = attackViewableInfo;
    }

    public String getAttackSound() {
        return mAttackSound;
    }

    public void setAttackSound(String attackSound) {
        mAttackSound = attackSound;
    }

    public int getDamage() {
        return mDamage;
    }

    public void setDamage(int damage) {
        mDamage = damage;
    }

    public int getAttackSoundID() {
        return mAttackSoundID;
    }

    public void setAttackSoundID(int attackSoundID) {
        mAttackSoundID = attackSoundID;
    }

    public Coordinate getPosition() {
        return mPosition;
    }

    public void setPosition(Coordinate position) {
        mPosition = position;
    }

    /**
     * Draws the image associated with this object
     */
    public void draw(float direction, float scale){
        mDirection = direction;
        AsteroidsGameModel game = AsteroidsGameModel.getInstance();
        Log.i("laser", "drawing " + mAttackViewableInfo.getImage());
        DrawingHelper.drawImage(mAttackViewableInfo.getImageID(),
                mPosition.getXPos(),
                mPosition.getYPos(),
                direction, scale, scale, 255);
    }

    /**
     * Updates the information associated with this object
     */
    public void update(){
        //TODO: Make it so the lasers are in world coordinates, and not view coordinates

        Log.i("laser", "Updating " + mAttackViewableInfo.getImage());
//        float yComp = mPosition.getYPos() + (float)(Math.asin(mDirection) * mSpeed);
//        float xComp = mPosition.getXPos() + (float)(Math.acos(mDirection) * mSpeed);
//        mPosition.setXPos(Math.round(xComp));
//        mPosition.setYPos(Math.round(yComp));

        int topLeftX = mPosition.getXPos() - mAttackViewableInfo.getImageWidth()/2;
        int topLeftY = mPosition.getYPos() - mAttackViewableInfo.getImageHeight()/2;
        int bottomRightX = mPosition.getXPos() + mAttackViewableInfo.getImageWidth()/2;
        int bottomRightY = mPosition.getYPos() + mAttackViewableInfo.getImageHeight()/2;

        GraphicsUtils.MoveObjectResult result = GraphicsUtils.moveObject(
                new PointF(mPosition.getXPos(), mPosition.getYPos()),
                new RectF(topLeftX, topLeftY, bottomRightX, bottomRightY),
                mSpeed, Math.toRadians(mDirection - 90), 1);
        mPosition.setXPos(Math.round(result.getNewObjPosition().x));
        mPosition.setYPos(Math.round(result.getNewObjPosition().y));
    }
}
