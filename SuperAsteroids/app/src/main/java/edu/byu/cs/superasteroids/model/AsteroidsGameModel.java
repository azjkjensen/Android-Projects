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

/**
 * Created by Jk on 2/19/2016.
 */
public class AsteroidsGameModel {
    SpaceShip mSpaceShip;
    Level mCurrentLevel;

    Set<AsteroidType> mAsteroidTypes;
    Map<Integer, AsteroidType> mLevelAsteroids;
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
}
