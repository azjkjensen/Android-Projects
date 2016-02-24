package edu.byu.cs.superasteroids.model;

import java.util.Set;

import edu.byu.cs.superasteroids.database.AsteroidTypeDAO;

/**
 * Created by Jk on 2/19/2016.
 */
public class AsteroidsGameModel {
    SpaceShip mSpaceShip;
    Level mCurrentLevel;

    Set<AsteroidType> mAsteroidTypes;
    Set<BackgroundImage> mBackgroundImages;
    Set<Cannon> mCannons;
    Set<Engine> mEngines;
    Set<ExtraPart> mExtraParts;
    Set<Level> mLevels;
    Set<MainBody> mMainBodies;
    Set<PowerCore> mPowerCores;

    private static AsteroidsGameModel instance = null;

    private AsteroidsGameModel(){}

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
        //TODO: Make sure that images are getting loaded into contentManager properly.
        mAsteroidTypes = AsteroidTypeDAO.getInstance().getAll();
        //mBackgroundImages = BackgroundImageDAO.getInstance().getAll();
        //mCannons = CannonDAO.getInstance().getAll();
        //mEngines = EngineDAO.getInstance().getAll();
        //mExtraParts = ExtraPartDAO.getInstance().getAll();
        //mLevels = LevelDAO.getInstance().getAll();
        //mMainBodies = MainBodyDAO.getInstance().getAll();
        //mPowerCores = PowerCareDAO.getInstance().getAll();
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
}
