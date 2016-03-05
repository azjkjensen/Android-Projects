package edu.byu.cs.superasteroids.main_menu;

import android.util.Log;

import edu.byu.cs.superasteroids.base.IView;
import edu.byu.cs.superasteroids.database.DbOpenHelper;
import edu.byu.cs.superasteroids.model.AsteroidsGameModel;
import edu.byu.cs.superasteroids.model.ExtraPart;
import edu.byu.cs.superasteroids.model.SpaceShip;

/**
 * Created by Jk on 2/26/2016.
 */
public class MainMenuController implements IMainMenuController {

    private MainActivity mMainMenuActivity;

    public MainMenuController(MainActivity mainActivity){
        mMainMenuActivity = mainActivity;
    }

    @Override
    public void onQuickPlayPressed() {
        try {
            if (DbOpenHelper.getInstance(null).dbIsEmpty())
                throw new Exception("Quick Play Failure");
            Log.i("quickPlay", "Successfully pressed quickplay");

            //Reset the game
            AsteroidsGameModel.resetGame();

            //Load Content
            AsteroidsGameModel.getInstance().populate();

            //Build a preset ship.
            AsteroidsGameModel.getInstance().assemblePresetShip();

            //Start the game
            mMainMenuActivity.startGame();
        } catch (Exception e){
            Log.i("quickPlay", "Failed to quickplay", e);
        }
    }

    @Override
    public IView getView() {
        return null;
    }

    @Override
    public void setView(IView view) {

    }
}
