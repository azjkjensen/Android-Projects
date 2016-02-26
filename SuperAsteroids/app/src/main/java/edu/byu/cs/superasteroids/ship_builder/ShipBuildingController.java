package edu.byu.cs.superasteroids.ship_builder;

import edu.byu.cs.superasteroids.base.IView;
import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.model.AsteroidsGameModel;

/**
 * Created by Jk on 2/17/2016.
 */
public class ShipBuildingController
        implements IShipBuildingController {

    private ShipBuilderStates.State mBuilderState = ShipBuilderStates.States.MAIN_BODY_STATE;
    private boolean mShipComplete = false;
    private ShipBuildingActivity mShipBuildingActivity;

    public ShipBuildingController(ShipBuildingActivity shipBuildingActivity){
        mShipBuildingActivity = shipBuildingActivity;
    }

    @Override
    public IView getView() {
        return null;
    }

    @Override
    public void setView(IView view) {

    }

    @Override
    public void update(double elapsedTime) {
        //Empty
    }

    @Override
    public void unloadContent(ContentManager content) {

    }

    @Override
    public void draw() {
    }

    @Override
    public void onViewLoaded(IShipBuildingView.PartSelectionView partView) {
        //shipBuildingActivity.setArrow() OR switch() on the partView
    }

    @Override
    public void loadContent(ContentManager content) {
        //Populate the model, then setPartViewImageList for each part
        AsteroidsGameModel.getInstance().populate();
        //Set the images to display for each part in the builder.
        setPartViews();
    }

    private void setPartViews(){
        mShipBuildingActivity.setPartViewImageList(IShipBuildingView.PartSelectionView.MAIN_BODY,
                AsteroidsGameModel.getInstance().getMainBodyImageIDs());
        mShipBuildingActivity.setPartViewImageList(IShipBuildingView.PartSelectionView.CANNON,
                AsteroidsGameModel.getInstance().getCannonImageIDs());
        mShipBuildingActivity.setPartViewImageList(IShipBuildingView.PartSelectionView.ENGINE,
                AsteroidsGameModel.getInstance().getEngineImageIDs());
        mShipBuildingActivity.setPartViewImageList(IShipBuildingView.PartSelectionView.EXTRA_PART,
                AsteroidsGameModel.getInstance().getExtraPartImageIDs());
        mShipBuildingActivity.setPartViewImageList(IShipBuildingView.PartSelectionView.POWER_CORE,
                AsteroidsGameModel.getInstance().getPowerCoreImageIDs());
    }

    @Override
    public void onSlideView(IShipBuildingView.ViewDirection direction) {
        //animate the slide change
        if(direction == IShipBuildingView.ViewDirection.LEFT) {
            mShipBuildingActivity.animateToView(mBuilderState.getNextView(),
                    mShipBuildingActivity.getOppositeDirection(direction));
            mBuilderState = mBuilderState.getNext();
        }
        else if(direction == IShipBuildingView.ViewDirection.RIGHT) {
            mShipBuildingActivity.animateToView(mBuilderState.getPrevView(),
                    mShipBuildingActivity.getOppositeDirection(direction));
            mBuilderState = mBuilderState.getPrev();
        }
        //Else do nothing. Only the left and right swipes will do anything.
    }

    @Override
    public void onPartSelected(int index) {
        //TODO: Figure out where the ship is supposed to be drawn/how to use DrawingHelper.drawImage
        //Select the part with the given index
        //Attach part to the image
        //If the ship is complete, shipBuildingActivity.setStartGameButton(true);
        if(mShipComplete) mShipBuildingActivity.setStartGameButton(true);
    }

    @Override
    public void onStartGamePressed() {
        mShipBuildingActivity.startGame();
    }

    @Override
    public void onResume() {
        //Not necessary for our implementation?
    }

    private void SetArrow(IShipBuildingView.ViewDirection viewDirection, Boolean visible, String text){
    }
}
