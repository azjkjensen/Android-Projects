package edu.byu.cs.superasteroids.model;

import android.graphics.PointF;

/**
 * Created by Jordan on 2/12/2016.
 * This class stores all information for a cannon to attach to the ship.
 */
public class Cannon {
    /**The coordinate where the cannon attaches to the ship */
    Coordinate mAttachPoint;
    /**The coordinate where the laser emits from. */
    Coordinate mEmitPoint;
    /**The image information for the cannon */
    ViewableObject mMainViewableInfo;
    /**The laser that shoots from this cannon */
    Laser mLaserShot;
    private PointF mVPEmitPoint;

    public Cannon() {
    }

    public Cannon(Coordinate attachPoint, Coordinate emitPoint,
                  ViewableObject mainViewableInfo,
                  String attackSound, int damage, Laser laserShot) {
        mAttachPoint = attachPoint;
        mEmitPoint = emitPoint;
        mMainViewableInfo = mainViewableInfo;
        mLaserShot = laserShot;
    }

    public Coordinate getAttachPoint() {
        return mAttachPoint;
    }

    public void setAttachPoint(Coordinate attachPoint) {
        mAttachPoint = attachPoint;
    }

    public Coordinate getEmitPoint() {
        return mEmitPoint;
    }

    public void setEmitPoint(Coordinate emitPoint) {
        mEmitPoint = emitPoint;
    }

    public ViewableObject getMainViewableInfo() {
        return mMainViewableInfo;
    }

    public void setMainViewableInfo(ViewableObject mainViewableInfo) {
        mMainViewableInfo = mainViewableInfo;
    }

    public Laser getLaserShot() {
        return mLaserShot;
    }

    public void setLaserShot(Laser laserShot) {
        mLaserShot = laserShot;
    }

    public PointF getVPEmitPoint() {
        return mVPEmitPoint;
    }

    /**
     * Draws the image associated with this object
     */
    public void draw(float bodyXAttach, float bodyYAttach,
                     int shipDirection, float scale){
        float newBodyAttachX = (float)((mAttachPoint.getXPos()) *
                Math.cos((Math.PI/180) * shipDirection));
        float newBodyAttachY = (float)((mAttachPoint.getYPos()) *
                Math.sin((Math.PI/180) * shipDirection));

        AsteroidsGameModel.getInstance().drawShipPart(mMainViewableInfo.getImageID(),
                bodyXAttach, bodyYAttach, mMainViewableInfo.getImageWidth(),
                mMainViewableInfo.getImageHeight(),
                new Coordinate(newBodyAttachX, newBodyAttachY),
                       .15f, shipDirection);
    }

    /**
     * Updates the information associated with this object
     */
    public void update(){
        //Updates based on the main body
    }

    public void setVPEmitPoint(float emitPointX, float emitPointY) {
        mVPEmitPoint = new PointF(emitPointX, emitPointY);
    }
}
