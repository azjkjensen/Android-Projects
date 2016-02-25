package edu.byu.cs.superasteroids.model;

/**
 * Created by Jk on 2/12/2016.
 * This class represents the laser image that shoots from the ship.
 */
public class Laser {
    /** The image information for the laser */
    ViewableObject mAttackViewableInfo;
    /**The filepath for the sound associated with the laser */
    String mAttackSound;
    /**The number of damage that the laser does when it hits an object. */
    int mDamage;
    /**The id associated with this item in the content manager */
    int mAttackSoundID;

    public Laser() {
    }

    public Laser(ViewableObject attackViewableInfo, String attackSound, int attackSoundID, int damage) {
        mAttackViewableInfo = attackViewableInfo;
        mAttackSound = attackSound;
        mAttackSoundID = attackSoundID;
        mDamage = damage;
    }

    public Laser(ViewableObject attackViewableInfo, String attackSound, int damage) {
        mAttackViewableInfo = attackViewableInfo;
        mAttackSound = attackSound;
        mDamage = damage;
    }

    public ViewableObject getAttackViewableInfo() {
        return mAttackViewableInfo;
    }

    public void setAttackViewableInfo(ViewableObject attackViewableInfo) {
        mAttackViewableInfo = attackViewableInfo;
    }

    public String getAttackSound() {
        return mAttackSound;
    }

    public void setAttackSound(String attackSound) {
        mAttackSound = attackSound;
    }

    public int getDamage() {
        return mDamage;
    }

    public void setDamage(int damage) {
        mDamage = damage;
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
