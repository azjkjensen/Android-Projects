package edu.byu.cs.superasteroids.model;

import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import edu.byu.cs.superasteroids.database.AsteroidTypeDAO;
import edu.byu.cs.superasteroids.database.BackgroundImageDAO;
import edu.byu.cs.superasteroids.database.CannonDAO;
import edu.byu.cs.superasteroids.database.EngineDAO;
import edu.byu.cs.superasteroids.database.ExtraPartDAO;
import edu.byu.cs.superasteroids.database.LevelDAO;
import edu.byu.cs.superasteroids.database.MainBodyDAO;
import edu.byu.cs.superasteroids.database.PowerCoreDAO;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by Jk on 2/19/2016.
 */
public class AsteroidsGameModel {
    private static final float ASTEROID_SCALE = 3f;
    private static Random rng = new Random();
    ViewPort mViewPort;
    SpaceShip mSpaceShip;
    Level mCurrentLevel;

    ArrayList<AsteroidType> mAsteroidTypes;
    Map<Integer, AsteroidType> mLevelAsteroids;
    ArrayList<BackgroundImage> mBackgroundImages;
    ArrayList<Cannon> mCannons;
    ArrayList<Engine> mEngines;
    ArrayList<ExtraPart> mExtraParts;
    ArrayList<Level> mLevels;
    ArrayList<MainBody> mMainBodies;
    ArrayList<PowerCore> mPowerCores;
//    ArrayList<Laser> mLasers;

    private static AsteroidsGameModel instance = null;

    private AsteroidsGameModel(){
        mCurrentLevel = new Level();
        mViewPort = new ViewPort(DrawingHelper.getGameViewWidth(),
                DrawingHelper.getGameViewHeight(), mCurrentLevel.getBackgroundImages(), this);
        mSpaceShip = new SpaceShip();
        mAsteroidTypes = new ArrayList<>();
    }

    public static AsteroidsGameModel getInstance() {
        if(instance == null) {
            instance = new AsteroidsGameModel();
        }
        return instance;
    }

    /**
     * Populates the model with all information from the database.
     */
    public void populate(){

        ArrayList<AsteroidType> mAsteroidTypePossibles;
        mAsteroidTypePossibles = AsteroidTypeDAO.getInstance().getAll();

        /*getAllImages() does NOT get the information for an object associated with a specific
        level. This will be done as we create the levels.*/
        mBackgroundImages = BackgroundImageDAO.getInstance().getAllImages();
        mCannons = CannonDAO.getInstance().getAll();
        mEngines = EngineDAO.getInstance().getAll();
        mExtraParts = ExtraPartDAO.getInstance().getAll();
        mLevels = LevelDAO.getInstance().getAll(mAsteroidTypePossibles, mBackgroundImages);
        mMainBodies = MainBodyDAO.getInstance().getAll();
        mPowerCores = PowerCoreDAO.getInstance().getAll();
    }

    public SpaceShip getSpaceShip() {
        return mSpaceShip;
    }

    public void setSpaceShip(SpaceShip spaceShip) {
        mSpaceShip = spaceShip;
    }

    public Level getCurrentLevel() {
        return mCurrentLevel;
    }

    public void setCurrentLevel(Level currentLevel) {

        mCurrentLevel = currentLevel;
        mSpaceShip.setXPosition(mCurrentLevel.getWidth() / 2);
        mSpaceShip.setYPosition(mCurrentLevel.getHeight() / 2);
        Map<AsteroidType, Integer> levelAsteroids = mCurrentLevel.getLevelAsteroids();
        for(AsteroidType asteroid : levelAsteroids.keySet()){
            for(int i = 0; i < levelAsteroids.get(asteroid); i++){
                AsteroidType newA = new AsteroidType(asteroid);
                Coordinate randPos = new Coordinate(
                        rng.nextInt(mCurrentLevel.getWidth()),
                        rng.nextInt(mCurrentLevel.getHeight())
                );
                Coordinate worldPos = mViewPort.toWorldCoordinates(randPos);
                newA.setPosition(worldPos);
                mAsteroidTypes.add(newA);
            }
        }
    }

    public ArrayList<AsteroidType> getAsteroidTypes() {
        return mAsteroidTypes;
    }

    public void setAsteroidTypes(ArrayList<AsteroidType> asteroidTypes) {
        mAsteroidTypes = asteroidTypes;
    }

    public Map<Integer, AsteroidType> getLevelAsteroids() {
        return mLevelAsteroids;
    }

    public void setLevelAsteroids(Map<Integer, AsteroidType> levelAsteroids) {
        mLevelAsteroids = levelAsteroids;
    }

    public ArrayList<BackgroundImage> getBackgroundImages() {
        return mBackgroundImages;
    }

    public void setBackgroundImages(ArrayList<BackgroundImage> backgroundImages) {
        mBackgroundImages = backgroundImages;
    }

    public ArrayList<Cannon> getCannons() {
        return mCannons;
    }

    public void setCannons(ArrayList<Cannon> cannons) {
        mCannons = cannons;
    }

    public ArrayList<Engine> getEngines() {
        return mEngines;
    }

    public void setEngines(ArrayList<Engine> engines) {
        mEngines = engines;
    }

    public ArrayList<ExtraPart> getExtraParts() {
        return mExtraParts;
    }

    public void setExtraParts(ArrayList<ExtraPart> extraParts) {
        mExtraParts = extraParts;
    }

    public ArrayList<Level> getLevels() {
        return mLevels;
    }

    public void setLevels(ArrayList<Level> levels) {
        mLevels = levels;
    }

    public ArrayList<MainBody> getMainBodies() {
        return mMainBodies;
    }

    public void setMainBodies(ArrayList<MainBody> mainBodies) {
        mMainBodies = mainBodies;
    }

    public ArrayList<PowerCore> getPowerCores() {
        return mPowerCores;
    }

    public void setPowerCores(ArrayList<PowerCore> powerCores) {
        mPowerCores = powerCores;
    }

    public ViewPort getViewPort() {
        return mViewPort;
    }

    public void setViewPort(ViewPort viewPort) {
        mViewPort = viewPort;
    }

    public List<Integer> getMainBodyImageIDs(){
        List<Integer> result = new ArrayList<>();
        for(MainBody mainBody : mMainBodies){
            result.add(mainBody.getViewableInfo().getImageID());
        }
        return result;
    }

    public List<Integer>  getCannonImageIDs(){
        List<Integer> result = new ArrayList<>();
        for(Cannon cannon : mCannons){
            result.add(cannon.getMainViewableInfo().getImageID());
        }
        return result;
    }

    public List<Integer>  getEngineImageIDs(){
        List<Integer> result = new ArrayList<>();
        for(Engine engine : mEngines){
            result.add(engine.getViewableInfo().getImageID());
        }
        return result;
    }

    public List<Integer>  getExtraPartImageIDs(){
        List<Integer> result = new ArrayList<>();
        for(ExtraPart extraPart : mExtraParts){
            result.add(extraPart.getViewableInfo().getImageID());
        }
        return result;
    }

    public List<Integer>  getPowerCoreImageIDs() {
        List<Integer> result = new ArrayList<>();
        for (PowerCore powerCore : mPowerCores) {
            result.add(powerCore.getImageID());
        }
        return result;
    }

    public boolean shipIsComplete() {
        return mSpaceShip.shipIsComplete();
    }

    public void assemblePresetShip(){
        mSpaceShip.setPowerCore(AsteroidsGameModel.getInstance().getPowerCores().get(0));
        mSpaceShip.setMainBody(AsteroidsGameModel.getInstance().getMainBodies().get(0));
        mSpaceShip.setCannon(AsteroidsGameModel.getInstance().getCannons().get(0));
        mSpaceShip.setExtraPart(AsteroidsGameModel.getInstance().getExtraParts().get(0));
        mSpaceShip.setEngine(AsteroidsGameModel.getInstance().getEngines().get(0));
    }

    public void drawShipPart(int imageID, float bodyAttachX, float bodyAttachY, int partWidth,
                             int partHeight, Coordinate partAttachPoint,
                             float scale, int direction){
//        float bodyAttachX = (bodyAttachPoint.getXPos() * scale) + bodyXOrigin;
//        float bodyAttachY = (bodyAttachPoint.getYPos() * scale) + bodyYOrigin;

        float partAttachX = partAttachPoint.getXPos() * scale;
        float partAttachY = partAttachPoint.getYPos() * scale;
        float partOriginX = bodyAttachX - partAttachX;
        float partOriginY = bodyAttachY - partAttachY;

        float scaledPartWidth = partWidth * scale;
        float scaledPartHeight = partHeight * scale;
        float partCenterX = partOriginX + scaledPartWidth/2;
        float partCenterY = partOriginY + scaledPartHeight/2;

        DrawingHelper.drawImage(imageID, partCenterX, partCenterY, 0, scale, scale, 255);
    }


    public void update(){
        mViewPort.setXDimension(DrawingHelper.getGameViewWidth());
        mViewPort.setYDimension(DrawingHelper.getGameViewHeight());

        ArrayList<AsteroidType> asteroidsToRemove = new ArrayList<>();
        ArrayList<AsteroidType> asteroidsToAdd = new ArrayList<>();

        mSpaceShip.update();

        //Set ship bounds to check for collisions
        float shipTop = mSpaceShip.getYPosition() - mSpaceShip.getShipHeight()/2;
        float shipLeft = mSpaceShip.getXPosition() - mSpaceShip.getShipWidth()/2;
        float shipBottom = mSpaceShip.getYPosition() + mSpaceShip.getShipHeight()/2;
        float shipRight = mSpaceShip.getXPosition() + mSpaceShip.getShipWidth()/2;
        RectF shipBounds = new RectF(shipLeft, shipTop, shipRight, shipBottom);

        mViewPort.update();

        mViewPort.getMiniMap().update();

        for(AsteroidType asteroid : mAsteroidTypes){

            asteroid.update();

            //Set asteroid bounds to check for collisions
            float asteroidTop = asteroid.mPosition.y - asteroid.getViewableInfo().getImageHeight()/2;
            float asteroidLeft = asteroid.mPosition.x - asteroid.getViewableInfo().getImageWidth()/2;
            float asteroidBottom = asteroid.mPosition.y +
                    asteroid.getViewableInfo().getImageHeight()/2;
            float asteroidRight = asteroid.mPosition.x +
                    asteroid.getViewableInfo().getImageWidth()/2;

            RectF aBounds = new RectF(asteroidLeft, asteroidTop, asteroidRight, asteroidBottom);

            //Check for asteroid/ship collisions
            if(RectF.intersects(aBounds, shipBounds)){
                asteroid.touch(mSpaceShip);
                mSpaceShip.touch(asteroid);
            }

            //Split asteroid if necessary
            ArrayList<AsteroidType> newAsteroids = asteroid.split();
            if(!newAsteroids.isEmpty()){
                for(AsteroidType a : newAsteroids){
                    asteroidsToAdd.add(a);
                }
                asteroidsToRemove.add(asteroid);
                continue;
            }

            //Get rid of destroyed asteroids
            if(asteroid.getHitPoints() <= 0){
                asteroidsToRemove.add(asteroid);
                continue;
            }

            //Check all lasers in level to update
            Iterator<Laser> laserIterator = mSpaceShip.getLasers().iterator();
            while(laserIterator.hasNext()){
                Laser l = laserIterator.next();

                //Check for collisions and lasers exiting the level
                float lTop = l.getPosition().getYPos() -
                        l.getAttackViewableInfo().getImageHeight()/2;
                float lLeft = l.getPosition().getXPos() -
                        l.getAttackViewableInfo().getImageWidth()/2;
                float lBottom = l.getPosition().getYPos() +
                        l.getAttackViewableInfo().getImageHeight()/2;
                float lRight = l.getPosition().getXPos() +
                        l.getAttackViewableInfo().getImageWidth()/2;
                RectF lBounds = new RectF(lLeft, lTop, lRight, lBottom);
                if(RectF.intersects(aBounds, lBounds)){
                    asteroid.touch(l);
                    l.touch(asteroid);
                    laserIterator.remove();
                }
                else if(l.mPosition.getXPos() < 0 || l.mPosition.getXPos() > mCurrentLevel.getWidth() ||
                        l.mPosition.getYPos() < 0 || l.mPosition.getYPos() > mCurrentLevel.getHeight()){
                    laserIterator.remove();
                }
            }
        }

        //Finalize all removals of asteroids
        mAsteroidTypes.removeAll(asteroidsToRemove);
        mAsteroidTypes.addAll(asteroidsToAdd);
    }

    public void draw(){
        mViewPort.draw();

        for(BackgroundImage backgroundImage : mCurrentLevel.getBackgroundImages()){
            backgroundImage.draw();
        }

        for(AsteroidType asteroid : mAsteroidTypes){
            asteroid.draw(ASTEROID_SCALE);
        }
        mSpaceShip.draw();
        mViewPort.getMiniMap().draw();
    }

    public static void resetGame() {
        instance = new AsteroidsGameModel();
    }

    public void addAsteroid(AsteroidType a) {
        mAsteroidTypes.add(a);
    }
}
