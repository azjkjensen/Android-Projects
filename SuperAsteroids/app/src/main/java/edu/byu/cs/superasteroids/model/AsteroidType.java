package edu.byu.cs.superasteroids.model;

import android.graphics.PointF;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.Random;

import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.game.InputManager;

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
    int mHitPoints = 5;
    /**The id associated with this item in the database */
    int mID;
    /**The number of this type of asteroid (for level asteroids) */
    int numberOfAsteroids;
    /**The position (world coordinates) of this asteroid */
    PointF mPosition;

    Random rng = new Random();
    private float mAsteroidSize = .5f;

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

    public PointF getPosition() {
        return mPosition;
    }

    /**
     * Accounts for when the asteroid takes a hit.
     */
    public void takeHit(int damage){
        mHitPoints = mHitPoints - damage;
    }

    /**
     * @return Whether the asteroid is ready to split or not.
     */
    public ArrayList<AsteroidType> split(){
        ArrayList<AsteroidType> result = new ArrayList<>();
        if(mHitPoints <= 0){
            if(mAsteroidSize == .5f){
                AsteroidType add1 = new AsteroidType();
                AsteroidType add2 = new AsteroidType();

                ViewableObject v = new ViewableObject();
                v.setImageID(this.mViewableInfo.mImageID);
                v.setImage(this.mViewableInfo.mImage);
                v.setImageHeight((int)(this.mViewableInfo.mImageHeight * .5f));
                v.setImageWidth((int)(this.mViewableInfo.mImageWidth * .5f));

                add1.setViewableInfo(v);
                add2.setViewableInfo(v);

                add1.setType(this.mType);
                add2.setType(this.mType);

                add1.setName(this.mName);
                add2.setName(this.mName);

                add1.setDirection(this.mDirection + 90);
                add2.setDirection(this.mDirection - 90);

                add1.setHitPoints(1);
                add2.setHitPoints(1);

                add1.setID(this.mID);
                add2.setID(this.mID);

                add1.setPosition(this.mPosition);
                add2.setPosition(this.mPosition);

                add1.setSpeed(this.mSpeed);
                add2.setSpeed(this.mSpeed);

                add1.mAsteroidSize = this.mAsteroidSize * .5f;
                add2.mAsteroidSize = this.mAsteroidSize* .5f;

                result.add(add1);
                result.add(add2);
            }
        }
        return result;
    }

    /**
     *
     * @return whether the asteroid has been shot enough to explode.
     */
    public boolean shouldExplode(){
        if(mHitPoints == 0 && mAsteroidSize == .25f) {
            return true;
        }
        else return false;
    }

    public void setPosition(Coordinate pos) {
        mPosition.x = pos.getXPos();
        mPosition.y = pos.getYPos();
    }

    public void setPosition(PointF pos) {
        mPosition.x = pos.x;
        mPosition.y = pos.y;
    }

    public void touch(Object o){
        if(o.getClass() == Laser.class){
            Laser l = (Laser) o;
            takeHit(l.getDamage());
        }
        else if(o.getClass() == SpaceShip.class){
            //Do nothing when ship collides with asteroid
        }
        else if(o.getClass() == AsteroidType.class){
            mDirection = -mDirection;
        }
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
                mDirection, scale * mAsteroidSize, scale * mAsteroidSize, 255);
    }

    /**
     * Updates the information associated with this object
     */
    public void update(){
        ViewPort vp = AsteroidsGameModel.getInstance().getViewPort();
        Level level = AsteroidsGameModel.getInstance().getCurrentLevel();
        Coordinate viewCoord = vp.toViewCoordinates(new Coordinate(mPosition.x, mPosition.y));

        int topLeftX = Math.round(mPosition.x - mViewableInfo.getImageWidth()/2);
        int topLeftY = Math.round(mPosition.y - mViewableInfo.getImageHeight()/2);
        int bottomRightX = Math.round(mPosition.x + mViewableInfo.getImageWidth()/2);
        int bottomRightY = Math.round(mPosition.y + mViewableInfo.getImageHeight() / 2);

        GraphicsUtils.MoveObjectResult result = GraphicsUtils.moveObject(
                new PointF(mPosition.x, mPosition.y),
                new RectF(topLeftX, topLeftY, bottomRightX, bottomRightY),
                mSpeed, Math.toRadians(mDirection - 90), 1);

        GraphicsUtils.RicochetObjectResult ricResult = GraphicsUtils.ricochetObject(
                result.getNewObjPosition(), result.getNewObjBounds(),
                Math.toRadians(mDirection - 90), level.getWidth(), level.getHeight());

        float newX = ricResult.getNewObjPosition().x;
        float newY = ricResult.getNewObjPosition().y;
        Coordinate newVP = vp.toViewCoordinates(new Coordinate(newX, newY));

        mPosition.x = newX;
        mPosition.y = newY;

        if(newX != result.getNewObjPosition().x || newY != result.getNewObjPosition().y){
            mDirection = (int)Math.toDegrees(ricResult.getNewAngleRadians()) + 90;
        }
    }
}
