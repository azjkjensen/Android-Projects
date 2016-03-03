package edu.byu.cs.superasteroids.model;

import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.game.InputManager;

/**
 * Created by Jk on 2/12/2016.
 * This class contains the information for the given spaceship build
 */
public class SpaceShip {
    /**The speed of the ship */
    int mSpeed = 18;
    /**The direction (in degrees ccw from 0) of the ship */
    int mDirection = 0;
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
    /** The emit point for the cannon on the ship */
    PointF mEmitPoint;
    /** Lasers from this ship */
    ArrayList<Laser> mLasers;

    private float mXPosition = 0;
    private float mYPosition = 0;
    private float mShipWidth = 0;
    private float mShipHeight = 0;
    private float mScale = (float) .35;
    private float mCannonOffsetX;
    private float mCannonOffsetY;
    private int mShipXCenter;
    private int mShipYCenter;
    private boolean mSafeMode = false;
    private int hitPoints = 5;
    private long mSafeModeStartTime;

    public SpaceShip() {
        mXPosition = DrawingHelper.getGameViewWidth();
        mYPosition = DrawingHelper.getGameViewHeight();
        mLasers = new ArrayList<>();
        mEmitPoint = new PointF(0,0);
    }

    public SpaceShip(int speed, int direction, Cannon cannon, Engine engine, ExtraPart extraPart, MainBody mainBody, PowerCore powerCore, MovingObject movingObjectInfo) {
        mSpeed = speed;
        mDirection = direction;
        mCannon = cannon;
        mEngine = engine;
        mExtraPart = extraPart;
        mMainBody = mainBody;
        mPowerCore = powerCore;
        mXPosition = DrawingHelper.getGameViewWidth();
        mYPosition = DrawingHelper.getGameViewHeight();
        mLasers = new ArrayList<>();
        mEmitPoint = new PointF(0,0);
//        mMovingObjectInfo = movingObjectInfo;
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

    public PointF getEmitPoint() {
        return mEmitPoint;
    }

    public void setEmitPoint(PointF emitPoint) {
        mEmitPoint = emitPoint;
    }

    public float getShipWidth() {
        return mShipWidth;
    }

    public void setShipWidth(float shipWidth) {
        mShipWidth = shipWidth;
    }

    public float getShipHeight() {
        return mShipHeight;
    }

    public void setShipHeight(float shipHeight) {
        mShipHeight = shipHeight;
    }

    public void touch(Object o){
        if(o.getClass() == AsteroidType.class){
            if(!this.inSafeMode())
                this.takeHit();
        }
    }

    private void takeHit() {
        this.hitPoints--;
        this.mSafeMode = true;
        //TODO: finish this timer to function as a 5-second countdown, and when 5s is up set safemode to false
        this.mSafeModeStartTime = System.currentTimeMillis();
    }

    private boolean inSafeMode() {
        return mSafeMode;
    }

    /**
     * Shoot a laser in the direction that the ship is facing.
     */
    public void shoot(){
        if(InputManager.firePressed == false) return;
        else{
            ViewPort vp = AsteroidsGameModel.getInstance().getViewPort();
//            Create and draw a laser
            Laser shot = new Laser(mCannon.getLaserShot());

            Coordinate laserWorld = vp.toWorldCoordinates(
                    new Coordinate(mCannon.getVPEmitPoint().x , mCannon.getVPEmitPoint().y)
            );
            shot.setDirection(mDirection);
            shot.mPosition.setXPos(laserWorld.getXPos());
            shot.mPosition.setYPos(laserWorld.getYPos());
            mLasers.add(shot);
        }
    }

    /**
     * Draws the image associated with this object
     */
    public void draw(){
        ContentManager cm = ContentManager.getInstance();
        if(mShipWidth == 0 || mShipHeight == 0) {
            mShipWidth = mExtraPart.getViewableInfo().getImageWidth() * mScale +
                    mMainBody.getViewableInfo().getImageWidth() * mScale +
                    mCannon.getMainViewableInfo().getImageWidth() * mScale;
            mShipHeight = mEngine.getViewableInfo().getImageHeight() * mScale +
                    mMainBody.getViewableInfo().getImageHeight() * mScale;
        }

        Coordinate viewPos = AsteroidsGameModel.getInstance().getViewPort().toViewCoordinates(
                new Coordinate(mXPosition, mYPosition));
        mShipXCenter = viewPos.getXPos();
        mShipYCenter = viewPos.getYPos();

        float bodyHeight = mMainBody.getViewableInfo().getImageHeight() * mScale;
        float bodyWidth = mMainBody.getViewableInfo().getImageWidth() * mScale;
        float bodyTopLeftX = (mShipXCenter - bodyWidth/2f);
        float bodyTopLeftY = (mShipYCenter - bodyHeight/2f);
        //Draw Main Body
        drawMainBody(mShipXCenter, mShipYCenter);

        //Draw Cannon
        mCannonOffsetX = (mMainBody.getCannonAttach().getXPos()  -
                mMainBody.getViewableInfo().getImageWidth()/2 +
                mCannon.getMainViewableInfo().getImageWidth()/2  -
                mCannon.getAttachPoint().getXPos()) * mScale;
        mCannonOffsetY = (mMainBody.getCannonAttach().getYPos() -
                mMainBody.getViewableInfo().getImageHeight()/2 +
                mCannon.getMainViewableInfo().getImageHeight()/2 -
                mCannon.getAttachPoint().getYPos()) * mScale;

        PointF rotatedOffset = GraphicsUtils.rotate(new PointF(mCannonOffsetX, mCannonOffsetY),
                (Math.PI / 180) * mDirection);
        float cannonXLocation = mShipXCenter + rotatedOffset.x;
        float cannonYLocation = mShipYCenter + rotatedOffset.y;

        //Draw all updated Lasers
        for(Laser laser : mLasers){
            laser.draw(mScale);
        }

        DrawingHelper.drawImage(mCannon.getMainViewableInfo().getImageID(),
                cannonXLocation, cannonYLocation, mDirection, mScale,
                mScale, 255);

        //Draw Extra Part
        float extraPartOffsetX = (mMainBody.getExtraAttach().getXPos()  -
                mMainBody.getViewableInfo().getImageWidth()/2 +
                mExtraPart.getViewableInfo().getImageWidth()/2  -
                mExtraPart.getAttachPoint().getXPos()) * mScale;
        float extraPartOffsetY = (mMainBody.getExtraAttach().getYPos() -
                mMainBody.getViewableInfo().getImageHeight()/2 +
                mExtraPart.getViewableInfo().getImageHeight()/2 -
                mExtraPart.getAttachPoint().getYPos()) * mScale;

        rotatedOffset = GraphicsUtils.rotate(new PointF(extraPartOffsetX, extraPartOffsetY),
                (Math.PI / 180) * mDirection);
        float extraPartXLocation = mShipXCenter + rotatedOffset.x;
        float extraPartYLocation = mShipYCenter + rotatedOffset.y;

        DrawingHelper.drawImage(mExtraPart.getViewableInfo().getImageID(),
                extraPartXLocation, extraPartYLocation, mDirection, mScale,
                mScale, 255);

        //Draw Engine
        float engineOffsetX = (mMainBody.getEngineAttach().getXPos()  -
                mMainBody.getViewableInfo().getImageWidth()/2 +
                mEngine.getViewableInfo().getImageWidth()/2  -
                mEngine.getAttachPoint().getXPos()) * mScale;
        float engineOffsetY = (mMainBody.getEngineAttach().getYPos() -
                mMainBody.getViewableInfo().getImageHeight()/2 +
                mEngine.getViewableInfo().getImageHeight()/2 -
                mEngine.getAttachPoint().getYPos()) * mScale;

        rotatedOffset = GraphicsUtils.rotate(new PointF(engineOffsetX, engineOffsetY),
                (Math.PI / 180) * mDirection);
        float engineXLocation = mShipXCenter + rotatedOffset.x;
        float engineYLocation = mShipYCenter + rotatedOffset.y;

        DrawingHelper.drawImage(mEngine.getViewableInfo().getImageID(),
                engineXLocation, engineYLocation, mDirection, mScale,
                mScale, 255);

        if(mSafeMode) {
            DrawingHelper.drawFilledCircle(new PointF(mShipXCenter, mShipYCenter),
                    mShipHeight * mScale, Color.GREEN, 70);
        }

    }

    private void drawMainBody(float shipX, float shipY) {
        DrawingHelper.drawImage(mMainBody.getViewableInfo().getImageID(),
                shipX, shipY, mDirection, mScale, mScale, 255);
//        mDirection++;
    }

    /**
     * Rotates the ship toward the direction parameter.
     */
    public void rotate(){
        if(InputManager.movePoint == null) return;
        ViewPort vp = AsteroidsGameModel.getInstance().getViewPort();
        float xComponent;
        float yComponent;
//        Coordinate vpShipPosition = new Coordinate(mXPosition, mYPosition);
//                xComponent = InputManager.movePoint.x - vpShipPosition.getXPos();
//                yComponent = InputManager.movePoint.y - vpShipPosition.getYPos();

        xComponent = InputManager.movePoint.x - DrawingHelper.getGameViewWidth()/2;
        yComponent = InputManager.movePoint.y - DrawingHelper.getGameViewHeight()/2;


        double angle = Math.toDegrees(Math.atan(xComponent / yComponent));
        if(yComponent > 0) {
            mDirection = (int) -angle + 180;
        } else{
            mDirection = (int) -angle;
        }
//        Log.i("gameplay", Integer.toString(mDirection));
//        Log.i("gameplay", "X " + xComponent + " Y " + yComponent);
    }

    /**
     * Updates the information associated with this object
     */
    public void update(){
        ViewPort viewPort = AsteroidsGameModel.getInstance().getViewPort();
        Level level = AsteroidsGameModel.getInstance().getCurrentLevel();

        if(System.currentTimeMillis() - mSafeModeStartTime >= 5000 && mSafeMode == true)
            mSafeMode = false;

        rotate();

        float emitOffsetX = ((mCannonOffsetX + (mCannon.getAttachPoint().getXPos() * mScale)));
        float emitOffsetY = ((mCannonOffsetY + (mCannon.getAttachPoint().getYPos() -
                mCannon.getLaserShot().getAttackViewableInfo().getImageHeight()) * mScale));

        mEmitPoint = GraphicsUtils.rotate(new PointF(emitOffsetX, emitOffsetY),
                Math.toRadians(mDirection));
        mCannon.setVPEmitPoint(mShipXCenter + mEmitPoint.x, mShipYCenter + mEmitPoint.y);

        for(Laser laser : mLasers){
            laser.update();
        }

        //Update movement
        if(InputManager.movePoint != null){

            Coordinate viewPos = viewPort.toViewCoordinates(
                    new Coordinate(getXPosition(), getYPosition()));
            float shipXCenter = viewPos.getXPos();
            float shipYCenter = viewPos.getYPos();
            float shipTopLeftX = shipXCenter - mShipWidth/2;
            float shipTopLeftY = shipYCenter - mShipHeight/2;
            float shipBottomRightX = shipXCenter + mShipWidth/2;
            float shipBottomRightY = shipYCenter + mShipHeight/2;

            GraphicsUtils.MoveObjectResult result = GraphicsUtils.moveObject(
                    new PointF(mXPosition, mYPosition),
                    new RectF(shipTopLeftX, shipTopLeftY, shipBottomRightX, shipBottomRightY),
                    mSpeed, Math.toRadians(mDirection - 90), 1);
            float newX = result.getNewObjPosition().x;
            float newY = result.getNewObjPosition().y;

            //If the ship is at the edge of the screen, don't update it.
            if(newX - mShipWidth/2 >= 0 &&
                    newX + mShipWidth/2 <= level.getWidth()) {
                mXPosition = newX;
            }
            if(newY - mShipHeight/2 >= 0 &&
                    newY + mShipHeight/2 <= level.getHeight()){
                mYPosition = newY;
            }

            if(InputManager.firePressed){
                shoot();
            }
        }
    }

    public boolean shipIsComplete() {
        return mCannon != null &&
                mEngine != null &&
                mExtraPart != null &&
                mMainBody != null &&
                mPowerCore != null;
    }

    public ArrayList<Laser> getLasers() {
        return mLasers;
    }
}
