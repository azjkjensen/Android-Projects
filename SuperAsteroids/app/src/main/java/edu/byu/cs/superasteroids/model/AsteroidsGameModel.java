package edu.byu.cs.superasteroids.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    private static AsteroidsGameModel instance = null;

    private AsteroidsGameModel(){
        mCurrentLevel = new Level();
        mViewPort = new ViewPort(DrawingHelper.getGameViewWidth(),
                DrawingHelper.getGameViewHeight(), mCurrentLevel.getBackgroundImages(), this);
        mSpaceShip = new SpaceShip();
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
        mAsteroidTypes = AsteroidTypeDAO.getInstance().getAll();

        /*getAllImages() does NOT get the information for an object associated with a specific
        level. This will be done as we create the levels.*/
        mBackgroundImages = BackgroundImageDAO.getInstance().getAllImages();
        mCannons = CannonDAO.getInstance().getAll();
        mEngines = EngineDAO.getInstance().getAll();
        mExtraParts = ExtraPartDAO.getInstance().getAll();
        mLevels = LevelDAO.getInstance().getAll(mAsteroidTypes, mBackgroundImages);
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

    public List<Integer>  getPowerCoreImageIDs(){
        List<Integer> result = new ArrayList<>();
        for(PowerCore powerCore : mPowerCores){
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

    public void update(){
        mViewPort.setXDimension(DrawingHelper.getGameViewWidth());
        mViewPort.setYDimension(DrawingHelper.getGameViewHeight());
        mSpaceShip.update();

        mViewPort.update();
    }

    public void draw(){
        mViewPort.draw();
        for(BackgroundImage backgroundImage : mCurrentLevel.getBackgroundImages()){
            backgroundImage.draw();
        }
    }
}
