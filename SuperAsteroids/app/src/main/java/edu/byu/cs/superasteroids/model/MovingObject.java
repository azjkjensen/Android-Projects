package edu.byu.cs.superasteroids.model;

/**
 * Created by Jk on 2/12/2016.
 * This class contains the information an object needs when it is to move on the
 * screen
 */
public class MovingObject {
    Coordinate mPosition;
    int mSpeed;

    public MovingObject() {
    }

    public MovingObject(Coordinate position, int speed) {
        mPosition = position;
        mSpeed = speed;
    }
}
