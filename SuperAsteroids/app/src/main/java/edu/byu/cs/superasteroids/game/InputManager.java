package edu.byu.cs.superasteroids.game;

import android.graphics.PointF;

/**
 * A simple class with static values for keeping track of user input
 */
public class InputManager {

    //This value will be null if there is not current move point, true otherwise
	public static PointF movePoint = null;

    //This value will be false if fire hasn't been pressed, true otherwise
	public static boolean firePressed = false;
	
}
