package edu.byu.cs.superasteroids.main_menu;

import edu.byu.cs.superasteroids.base.IView;

public interface IMainMenuView extends IView{
    /**
     * Instructs the ShipBuildingView to start the game.
     */
    void startGame();
}
