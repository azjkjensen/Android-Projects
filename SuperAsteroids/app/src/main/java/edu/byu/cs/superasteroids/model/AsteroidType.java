package edu.byu.cs.superasteroids.model;

import android.graphics.PointF;
import android.graphics.RectF;

import java.util.Random;

import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by Jk on 2/12/2016.
 * This class contains each asteroid type available to the game to use.
 */
public class AsteroidType {
    /**The name of the asteroid. */
    String mName;
    /**The type of asteroid */
    String mType;
    /**The image information for the asteroid image. */
    ViewableObject mViewableInfo;
    /** The direction the Asteroid is moving */
    int mDirection;
    /**The speed of the Asteroid, in pixels/second */
    int mSpeed = 5;
    /**The number of hitpoints remaining on the Asteroid */
    int mHitPoints;
    /**The id associated with this item in the database */
    int mID;
    /**The number of this type of asteroid (for level asteroids) */
    int numberOfAsteroids;
    /**The position (world coordinates) of this asteroid */
    PointF mPosition;

    Random rng = new Random();

    /**
    * Default Constructor
     */
    public AsteroidType(){
        //Placeholder position
        this.mPosition = new PointF(0,0);
        this.mDirection = rng.nextInt(181);
    }

    public AsteroidType(AsteroidType toCopy){
        this.mID = toCopy.getID();
        this.mViewableInfo = toCopy.getViewableInfo();
        this.mType = toCopy.getType();
        this.mHitPoints = toCopy.getHitPoints();
        this.mSpeed = toCopy.getSpeed();
        this.mName = toCopy.getName();

        //Point the asteroid in a random direction between 0 and 180 degrees
        this.mDirection = rng.nextInt(181);
        //Placeholder position
        this.mPosition = new PointF(0,0);
    }

    /**
     * Field filling constructor
     * @param name
     * @param type
     * @param viewableInfo
     */
    public AsteroidType(String name, String type, ViewableObject viewableInfo, int id) {
        mName = name;
        mType = type;
        mViewableInfo = viewableInfo;
        mID = id;
//        mImageID = imageID;
    }

    public int getID() {
        return mID;
    }

    public void setID(int ID) {
        mID = ID;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public ViewableObject getViewableInfo() {
        return mViewableInfo;
    }

    public void setViewableInfo(ViewableObject viewableInfo) {
        mViewableInfo = viewableInfo;
    }

    public int getDirection() {
        return mDirection;
    }

    public void setDirection(int direction) {
        mDirection = direction;
    }

    public int getSpeed() {
        return mSpeed;
    }

    public void setSpeed(int speed) {
        mSpeed = speed;
    }

    public int getHitPoints() {
        return mHitPoints;
    }

    public void setHitPoints(int hitPoints) {
        mHitPoints = hitPoints;
    }

    public int getNumberOfAsteroids() {
        return numberOfAsteroids;
    }

    public void setNumberOfAsteroids(int numberOfAsteroids) {
        this.numberOfAsteroids = numberOfAsteroids;
    }

    /**
     * Accounts for when the asteroid takes a hit.
     */
    public void takeHit(){
        mHitPoints--;
    }

    /**
     * @return Whether the asteroid is ready to split or not.
     */
    public boolean shouldSplit(){
        return false;
    }

    /**
     *
     * @return whether the asteroid has been shot enough to explode.
     */
    public boolean shouldExplode(){
        if(mHitPoints == 0) {
            return true;
        }
        else return false;
    }

    public void setPosition(Coordinate pos) {
        mPosition.x = pos.getXPos();
        mPosition.y = pos.getYPos();
    }

    /**
     * Draws the image associated with this object
     */
    public void draw(float scale){
        ViewPort vp = AsteroidsGameModel.getInstance().getViewPort();
        Coordinate c = new Coordinate(mPosition.x, mPosition.y);
        Coordinate vc = vp.toViewCoordinates(c);
        DrawingHelper.drawImage(mViewableInfo.getImageID(),
                vc.getXPos(),
                vc.getYPos(),
                mDirection, scale, scale, 255);
    }

    /**
     * Updates the information associated with this object
     */
    public void update(){
        //TODO: Make the asteroids go in random directions
        //TODO: Make the asteroids have random starting positions.
        //TODO: Make the asteroids bounce off of the wall, instead of stopping (See spec/notes, I think there's a collide function)
        ViewPort vp = AsteroidsGameModel.getInstance().getViewPort();
        int topLeftX = Math.round(mPosition.x - mViewableInfo.getImageWidth()/2);
        int topLeftY = Math.round(mPosition.y - mViewableInfo.getImageHeight()/2);
        int bottomRightX = Math.round(mPosition.x + mViewableInfo.getImageWidth()/2);
        int bottomRightY = Math.round(mPosition.y + mViewableInfo.getImageHeight()/2);

        GraphicsUtils.MoveObjectResult result = GraphicsUtils.moveObject(
                new PointF(mPosition.x, mPosition.x),
                new RectF(topLeftX, topLeftY, bottomRightX, bottomRightY),
                mSpeed, Math.toRadians(mDirection - 90), 1);

        float newX = result.getNewObjPosition().x;
        float newY = result.getNewObjPosition().y;
        Coordinate newVP = vp.toViewCoordinates(new Coordinate(newX, newY));

        Level level = AsteroidsGameModel.getInstance().getCurrentLevel();
        //If the ship is at the edge of the screen, don't update it.
        if(newX - mViewableInfo.getImageWidth()/2 >= 0 &&
                newX + mViewableInfo.getImageWidth()/2 <= level.getWidth()) {
            mPosition.x = newX;
        }
        if(newY - mViewableInfo.getImageHeight()/2 >= 0 &&
                newY + mViewableInfo.getImageHeight()/2 <= level.getHeight()){
            mPosition.y = newY;
        }
    }
}
