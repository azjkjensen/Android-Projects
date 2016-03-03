package edu.byu.cs.superasteroids.game;

import edu.byu.cs.superasteroids.base.IGameDelegate;
import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.model.AsteroidsGameModel;

/**
 * Created by Jk on 2/27/2016.
 */
public class GameController implements IGameDelegate {
    AsteroidsGameModel game = AsteroidsGameModel.getInstance();
    int currentLevelNumber = 0;

    /**
     * Updates the game delegate. The game engine will call this function 60 times a second
     * once it enters the game loop.
     * @param elapsedTime Time since the last update. For this game, elapsedTime is always
     *                    1/60th of second
     */
    public void update(double elapsedTime){
        if(InputManager.movePoint != null){
            //Rotate the spaceship in the direction of the move point.
//            game.getSpaceShip().rotate(InputManager.movePoint);
        }
//        if(InputManager.firePressed){
//            game.getSpaceShip().shoot();
//        }
        game.update();
        if(game.getAsteroidTypes().size() == 0)
            game.setCurrentLevel(game.getLevels().get(currentLevelNumber++));
    }

    /**
     * Loads content such as image and sounds files and other data into the game. The GameActivty will
     * call this once right before entering the game engine enters the game loop. The ShipBuilding
     * activity calls this function in its onCreate() function.
     * @param content An instance of the content manager. This should be used to load images and sound
     *                files.
     */
    public void loadContent(ContentManager content){
        AsteroidsGameModel.getInstance().setCurrentLevel(
                AsteroidsGameModel.getInstance().getLevels().get(currentLevelNumber)
        );
    }

    /**
     * Unloads content from the game. The GameActivity will call this function after the game engine
     * exits the game loop. The ShipBuildingActivity will call this function after the "Start Game"
     * button has been pressed.
     * @param content An instance of the content manager. This should be used to unload image and
     *                sound files.
     */
    public void unloadContent(ContentManager content){

    }

    /**
     * Draws the game delegate. This function will be 60 times a second.
     */
    public void draw(){
        AsteroidsGameModel.getInstance().draw();
    }
}
