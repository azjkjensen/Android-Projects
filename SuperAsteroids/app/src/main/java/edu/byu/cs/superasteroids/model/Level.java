package edu.byu.cs.superasteroids.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Jordan on 2/12/2016.
 * This class stores the information corresponding to a given level in the game.
 */
public class Level {
    /**The number of the level */
    int mNumber;
    /**The width (in pixels) of the level */
    int mWidth;
    /**The height (in pixels) of the level */
    int mHeight;
    /**The title of the level */
    String mTitle;
    /**The hint string for the level */
    String mHint;
    /**Filepath to the music file for the level */
    String mMusic;
    /**An array of images associated with the level */
    List<BackgroundImage> mBackgroundImages;
    /**A map of the asteroids in the level and the number of each */
    Map<AsteroidType, Integer> mLevelAsteroids;

    public Level() {
        mBackgroundImages = new ArrayList<>();
        mLevelAsteroids = new HashMap<>();
    }

    public Level(int number, int width, int height,
                 String title, String hint, String music,
                 List<BackgroundImage> backgroundImages, Map<AsteroidType, Integer> levelAsteroids) {
        mNumber = number;
        mWidth = width;
        mHeight = height;
        mTitle = title;
        mHint = hint;
        mMusic = music;
        mBackgroundImages = backgroundImages;
        mLevelAsteroids = levelAsteroids;
    }

    public Level(int number, int width, int height,
                 String title, String hint, String music) {
        mNumber = number;
        mWidth = width;
        mHeight = height;
        mTitle = title;
        mHint = hint;
        mMusic = music;
    }


    public int getNumber() {
        return mNumber;
    }

    public void setNumber(int number) {
        mNumber = number;
    }

    public int getWidth() {
        return mWidth;
    }

    public void setWidth(int width) {
        mWidth = width;
    }

    public int getHeight() {
        return mHeight;
    }

    public void setHeight(int height) {
        mHeight = height;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getHint() {
        return mHint;
    }

    public void setHint(String hint) {
        mHint = hint;
    }

    public String getMusic() {
        return mMusic;
    }

    public void setMusic(String music) {
        mMusic = music;
    }

    public List<BackgroundImage> getBackgroundImages() {
        return mBackgroundImages;
    }

    public void setBackgroundImages(List<BackgroundImage> backgroundImages) {
        mBackgroundImages = backgroundImages;
    }

    public Map<AsteroidType, Integer> getLevelAsteroids() {
        return mLevelAsteroids;
    }

    public void setLevelAsteroids(Map<AsteroidType, Integer> levelAsteroids) {
        mLevelAsteroids = levelAsteroids;
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
}
