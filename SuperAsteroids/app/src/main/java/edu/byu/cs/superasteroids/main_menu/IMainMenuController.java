package edu.byu.cs.superasteroids.main_menu;

import edu.byu.cs.superasteroids.base.IController;

public interface IMainMenuController extends IController {

    /**
     * The MainActivity calls this function when the "quick play" button is pressed.
     */
    void onQuickPlayPressed();
}
