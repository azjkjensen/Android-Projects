package edu.byu.cs.superasteroids.model;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

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

    private float mXPosition = DrawingHelper.getGameViewWidth();
    private float mYPosition = DrawingHelper.getGameViewHeight();
    private float mShipWidth = 0;
    private float mShipHeight = 0;
    private float mScale = (float) .35;

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

    /**
     * Shoot a laser in the direction that the ship is facing.
     */
    public void shoot(){
        if(InputManager.firePressed == false) return;
        else{
            //Create and draw a laser
            Laser shot = new Laser();
            //TODO: assign a location and such for laser and draw it!
            AsteroidsGameModel.getInstance().getLasers().add(shot);
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
                new Coordinate(getXPosition(), getYPosition()));
        float shipXCenter = viewPos.getXPos();
        float shipYCenter = viewPos.getYPos();

        float bodyHeight = mMainBody.getViewableInfo().getImageHeight() * mScale;
        float bodyWidth = mMainBody.getViewableInfo().getImageWidth() * mScale;
        float bodyTopLeftX = (shipXCenter - bodyWidth/2f);
        float bodyTopLeftY = (shipYCenter - bodyHeight/2f);
        //Draw Main Body
        drawMainBody(shipXCenter, shipYCenter);

        //Draw Cannon
        float cannonOffsetX = (mMainBody.getCannonAttach().getXPos()  -
                mMainBody.getViewableInfo().getImageWidth()/2 +
                mCannon.getMainViewableInfo().getImageWidth()/2  -
                mCannon.getAttachPoint().getXPos()) * mScale;
        float cannonOffsetY = (mMainBody.getCannonAttach().getYPos() -
                mMainBody.getViewableInfo().getImageHeight()/2 +
                mCannon.getMainViewableInfo().getImageHeight()/2 -
                mCannon.getAttachPoint().getYPos()) * mScale;

        PointF rotatedOffset = GraphicsUtils.rotate(new PointF(cannonOffsetX, cannonOffsetY),
                (Math.PI / 180) * mDirection);
        float cannonXLocation = shipXCenter + rotatedOffset.x;
        float cannonYLocation = shipYCenter + rotatedOffset.y;

        float emitOffsetX = (cannonOffsetX - mCannon.getMainViewableInfo().getImageWidth()/2 +
                mCannon.getEmitPoint().getXPos());
        float emitOffsetY = (cannonOffsetY - mCannon.getMainViewableInfo().getImageHeight()/2 +
                mCannon.getEmitPoint().getYPos());
        PointF mEmitPoint = GraphicsUtils.rotate(new PointF(emitOffsetX, emitOffsetY),
                Math.toRadians(mDirection));


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
        float extraPartXLocation = shipXCenter + rotatedOffset.x;
        float extraPartYLocation = shipYCenter + rotatedOffset.y;

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
        float engineXLocation = shipXCenter + rotatedOffset.x;
        float engineYLocation = shipYCenter + rotatedOffset.y;

        DrawingHelper.drawImage(mEngine.getViewableInfo().getImageID(),
                engineXLocation, engineYLocation, mDirection, mScale,
                mScale, 255);
    }

    private void drawMainBody(float shipX, float shipY){
        DrawingHelper.drawImage(mMainBody.getViewableInfo().getImageID(),
                shipX, shipY, mDirection, mScale, mScale, 255);
//        mDirection++;
    }

    /**
     * Rotates the ship toward the direction parameter.
     * @param direction that the ship should rotate toward
     */
    public void rotate(int direction){
        if(InputManager.movePoint == null) return;
        float xComponent;
        float yComponent;
        xComponent = InputManager.movePoint.x - DrawingHelper.getGameViewWidth() / 2;
        yComponent = InputManager.movePoint.y - DrawingHelper.getGameViewHeight() / 2;


        double angle = Math.toDegrees(Math.atan(xComponent / yComponent));
        if(yComponent > 0) {
            mDirection = (int) -angle + 180;
        } else{
            mDirection = (int) -angle;
        }
        Log.i("gameplay", Integer.toString(mDirection));
        Log.i("gameplay", "X " + xComponent + " Y " + yComponent);
    }

    /**
     * Updates the information associated with this object
     */
    public void update(){
        ViewPort viewPort = AsteroidsGameModel.getInstance().getViewPort();
        Level level = AsteroidsGameModel.getInstance().getCurrentLevel();
        rotate(mDirection);

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

            //TODO: Ask TA's about the last parameter of this function
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
        }
    }

    public boolean shipIsComplete() {
        return mCannon != null &&
                mEngine != null &&
                mExtraPart != null &&
                mMainBody != null &&
                mPowerCore != null;
    }
}
