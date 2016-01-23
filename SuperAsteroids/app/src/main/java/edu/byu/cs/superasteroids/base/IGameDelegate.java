package edu.byu.cs.superasteroids.base;

import edu.byu.cs.superasteroids.content.ContentManager;

/**
 *
 */
public interface IGameDelegate {

    /**
     * Updates the game delegate. The game engine will call this function 60 times a second
     * once it enters the game loop.
     * @param elapsedTime Time since the last update. For this game, elapsedTime is always
     *                    1/60th of second
     */
	void update(double elapsedTime);

    /**
     * Loads content such as image and sounds files and other data into the game. The GameActivty will
     * call this once right before entering the game engine enters the game loop. The ShipBuilding
     * activity calls this function in its onCreate() function.
     * @param content An instance of the content manager. This should be used to load images and sound
     *                files.
     */
	void loadContent(ContentManager content);

    /**
     * Unloads content from the game. The GameActivity will call this function after the game engine
     * exits the game loop. The ShipBuildingActivity will call this function after the "Start Game"
     * button has been pressed.
     * @param content An instance of the content manager. This should be used to unload image and
     *                sound files.
     */
    void unloadContent(ContentManager content);

    /**
     * Draws the game delegate. This function will be 60 times a second.
     */
	void draw();

}
