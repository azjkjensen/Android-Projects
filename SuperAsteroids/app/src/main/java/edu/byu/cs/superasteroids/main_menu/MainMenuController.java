package edu.byu.cs.superasteroids.main_menu;

import edu.byu.cs.superasteroids.base.IView;

/**
 * Created by Jk on 2/26/2016.
 */
public class MainMenuController implements IMainMenuController {
    @Override
    public void onQuickPlayPressed() {
        //TODO: Make sure that if the db is empty, quick play does not work.
        //TODO: Load ALL content once so we don't have to unload it later.
    }

    @Override
    public IView getView() {
        return null;
    }

    @Override
    public void setView(IView view) {

    }
}
