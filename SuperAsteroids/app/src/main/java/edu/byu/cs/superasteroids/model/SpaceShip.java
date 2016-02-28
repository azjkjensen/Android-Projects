package edu.byu.cs.superasteroids.model;

/**
 * Created by Jk on 2/12/2016.
 * This class contains the information for the given spaceship build
 */
public class SpaceShip {
    /**The speed of the ship */
    int mSpeed = 2;
    /**The direction (in degrees ccw from 0) of the ship */
    int mDirection = 90;
    /**The cannon part for the ship */
    Cannon mCannon;
    /**The engine part of the ship */
    Engine mEngine;
    /**The extra part part of the ship */
    ExtraPart mExtraPart;
    /**The main body part of the ship */
    MainBody mMainBody;
    /**The power core part of the ship */
    PowerCore mPowerCore;
    /**The information necessary for the ship to move and be redrawn */
    MovingObject mMovingObjectInfo;
    private float mXPosition = 0;
    private float mYPosition = 0;

    public SpaceShip() {
    }

    public SpaceShip(int speed, int direction, Cannon cannon, Engine engine, ExtraPart extraPart, MainBody mainBody, PowerCore powerCore, MovingObject movingObjectInfo) {
        mSpeed = speed;
        mDirection = direction;
        mCannon = cannon;
        mEngine = engine;
        mExtraPart = extraPart;
        mMainBody = mainBody;
        mPowerCore = powerCore;
        mMovingObjectInfo = movingObjectInfo;
    }

    public Cannon getCannon() {
        return mCannon;
    }

    public void setCannon(Cannon cannon) {
        mCannon = cannon;
    }

    public Engine getEngine() {
        return mEngine;
    }

    public void setEngine(Engine engine) {
        mEngine = engine;
    }

    public ExtraPart getExtraPart() {
        return mExtraPart;
    }

    public void setExtraPart(ExtraPart extraPart) {
        mExtraPart = extraPart;
    }

    public MainBody getMainBody() {
        return mMainBody;
    }

    public void setMainBody(MainBody mainBody) {
        mMainBody = mainBody;
    }

    public PowerCore getPowerCore() {
        return mPowerCore;
    }

    public void setPowerCore(PowerCore powerCore) {
        mPowerCore = powerCore;
    }

    public float getXPosition() {
        return mXPosition;
    }

    public void setXPosition(float XPosition) {
        mXPosition = XPosition;
    }

    public float getYPosition() {
        return mYPosition;
    }

    public void setYPosition(float YPosition) {
        mYPosition = YPosition;
    }

    /**
     * Shoot a laser in the direction that the ship is facing.
     */
    public void shoot(){

    }

    /**
     * Rotates the ship toward the direction parameter.
     * @param direction that the ship should rotate toward
     */
    public void rotate(int direction){

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

    public boolean shipIsComplete() {
        return mCannon != null &&
                mEngine != null &&
                mExtraPart != null &&
                mMainBody != null &&
                mPowerCore != null;
    }
}
