package edu.byu.cs.superasteroids.ship_builder;

import edu.byu.cs.superasteroids.base.IView;
import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.model.MainBody;

/**
 * Created by Jk on 2/17/2016.
 */
public class ShipBuildingController
        implements IShipBuildingController {

    private ShipBuildingActivity mShipBuildingActivity;

    @Override
    public IView getView() {
        return null;
    }

    @Override
    public void setView(IView view) {

    }

    @Override
    public void update(double elapsedTime) {

    }

    @Override
    public void unloadContent(ContentManager content) {

    }

    @Override
    public void draw() {

    }

    @Override
    public void onViewLoaded(IShipBuildingView.PartSelectionView partView) {
        //shipBuildingActivity.setArrow() ORR switch() on the partView
    }

    @Override
    public void loadContent(ContentManager content) {
        //Populate the model, then setPartViewImageList for each part
//        mShipBuildingActivity.setPartViewImageList(IShipBuildingView.PartSelectionView.MAIN_BODY,
//                MainBody.getListOfImageIds());
    }

    @Override
    public void onSlideView(IShipBuildingView.ViewDirection direction) {
        //animate the slide change OR switch() on the current state
//        mShipBuildingActivity.animateToView(partSeletionView,
//                mShipBuildingActivity.getOppositeDirection(direction));
        //Change state to new view state.
    }

    @Override
    public void onPartSelected(int index) {
        //Select the part with the given index
        //Attach part to the image
        //If the ship is complete, shipBuildingActivity.setStartGameButton(true);
    }

    @Override
    public void onStartGamePressed() {
        mShipBuildingActivity.startGame();
    }

    @Override
    public void onResume() {
        //Not necessary for our implementation?
    }
}
