package edu.byu.cs.superasteroids.ship_builder;

import android.graphics.Color;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.superasteroids.base.IView;
import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.model.AsteroidsGameModel;
import edu.byu.cs.superasteroids.model.Cannon;
import edu.byu.cs.superasteroids.model.Coordinate;
import edu.byu.cs.superasteroids.model.Engine;
import edu.byu.cs.superasteroids.model.ExtraPart;
import edu.byu.cs.superasteroids.model.MainBody;

/**
 * Created by Jk on 2/17/2016.
 */
public class ShipBuildingController
        implements IShipBuildingController {

    private ShipBuildingActivity mShipBuildingActivity;
    private ShipBuilderStates.State mBuilderState = ShipBuilderStates.States.MAIN_BODY_STATE;
    private boolean mShipComplete = false;
    private int mMainBodyID = -1;
    private int mEngineID = -1;
    private int mExtraPartID = -1;
    private int mCannonID = -1;
    private int mPowerCoreID = -1;
    private boolean mNoPartsSelected = true;

    private List<Integer> mMainBodyImageIDs;
    private List<Integer> mEngineImageIDs;
    private List<Integer> mExtraPartImageIDs;
    private List<Integer> mCannonImageIDs;
    private List<Integer> mPowerCoreImageIDs;

    public ShipBuildingController(ShipBuildingActivity shipBuildingActivity){
        mShipBuildingActivity = shipBuildingActivity;
        mMainBodyImageIDs = new ArrayList<>();
        mEngineImageIDs = new ArrayList<>();
        mExtraPartImageIDs= new ArrayList<>();
        mCannonImageIDs = new ArrayList<>();
        mPowerCoreImageIDs = new ArrayList<>();
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
        //Can leave empty
    }

    @Override
    public void draw() {
        int height = DrawingHelper.getGameViewHeight();
        int width = DrawingHelper.getGameViewWidth();
        float xCenter = width / 2;
        float yCenter = height / 2;

        //If no parts have been selected, write a prompt to the screen.
        if(mNoPartsSelected) DrawingHelper.drawCenteredText(
                "Build your ship by choosing parts from above.", 25, Color.LTGRAY);
        else {
            //draw main body
            float scale = (float) 0.5;
            MainBody mainBody = AsteroidsGameModel.getInstance().getMainBodies().get(mMainBodyID);
            float bodyHeight = mainBody.getViewableInfo().getImageHeight() * scale;
            float bodyWidth = mainBody.getViewableInfo().getImageWidth() * scale;
            float mainBodyImageOriginX = xCenter - bodyWidth / 2;
            float mainBodyImageOriginY = yCenter - bodyHeight / 2;
            DrawingHelper.drawImage(mMainBodyImageIDs.get(mMainBodyID), xCenter, yCenter, 0, scale, scale, 255);

            if (mCannonID != -1) {
                Cannon cannon = AsteroidsGameModel.getInstance().getCannons().get(mCannonID);
                drawShipPart(mCannonImageIDs.get(mCannonID), mainBodyImageOriginX,
                        mainBodyImageOriginY, cannon.getMainViewableInfo().getImageWidth(),
                        cannon.getMainViewableInfo().getImageHeight(), cannon.getAttachPoint(),
                        mainBody.getCannonAttach(), scale);
            }
            if(mExtraPartID != -1) {
                ExtraPart extraPart = AsteroidsGameModel.getInstance().getExtraParts()
                        .get(mExtraPartID);
                drawShipPart(mExtraPartImageIDs.get(mExtraPartID), mainBodyImageOriginX,
                        mainBodyImageOriginY, extraPart.getViewableInfo().getImageWidth(),
                        extraPart.getViewableInfo().getImageHeight(), extraPart.getAttachPoint(),
                        mainBody.getExtraAttach(), scale);
            }
            if(mEngineID != -1) {
                Engine engine = AsteroidsGameModel.getInstance().getEngines()
                        .get(mEngineID);
                drawShipPart(mEngineImageIDs.get(mEngineID), mainBodyImageOriginX,
                        mainBodyImageOriginY, engine.getViewableInfo().getImageWidth(),
                        engine.getViewableInfo().getImageHeight(), engine.getAttachPoint(),
                        mainBody.getEngineAttach(), scale);
            }
        }

        DrawingHelper.drawText( new Point(Math.round(xCenter) + 320, Math.round(height) - 10),
                mBuilderState.getName(), Color.WHITE, 50);
    }

    private void drawShipPart(int imageID, float bodyXOrigin, float bodyYOrigin, int partWidth,
                              int partHeight, Coordinate partAttachPoint,
                              Coordinate bodyAttachPoint, float scale){
        float bodyAttachX = (bodyAttachPoint.getXPos() * scale) + bodyXOrigin;
        float bodyAttachY = (bodyAttachPoint.getYPos() * scale) + bodyYOrigin;

        float partAttachX = partAttachPoint.getXPos() * scale;
        float partAttachY = partAttachPoint.getYPos() * scale;
        float partOriginX = bodyAttachX - partAttachX;
        float partOriginY = bodyAttachY - partAttachY;

        float scaledPartWidth = partWidth * scale;
        float scaledPartHeight = partHeight * scale;
        float partCenterX = partOriginX + scaledPartWidth/2;
        float partCenterY = partOriginY + scaledPartHeight/2;

        DrawingHelper.drawImage(imageID, partCenterX, partCenterY, 0, scale, scale, 255);
    }

    @Override
    public void onViewLoaded(IShipBuildingView.PartSelectionView partView) {
        //shipBuildingActivity.setArrow() OR switch() on the partView
        setArrows();
    }

    @Override
    public void loadContent(ContentManager content) {
        //Load ALL content once so that we don't have to do it again.
        //Populate the model, then setPartViewImageList for each part
        AsteroidsGameModel.getInstance().populate();
        //Set the images to display for each part in the builder.
        setPartViews();
    }

    private void setPartViews(){
        mMainBodyImageIDs =  AsteroidsGameModel.getInstance().getMainBodyImageIDs();
        mCannonImageIDs =  AsteroidsGameModel.getInstance().getCannonImageIDs();
        mEngineImageIDs =  AsteroidsGameModel.getInstance().getEngineImageIDs();
        mExtraPartImageIDs =  AsteroidsGameModel.getInstance().getExtraPartImageIDs();
        mPowerCoreImageIDs =  AsteroidsGameModel.getInstance().getPowerCoreImageIDs();

        mShipBuildingActivity.setPartViewImageList(IShipBuildingView.PartSelectionView.MAIN_BODY,
                mMainBodyImageIDs);
        mShipBuildingActivity.setPartViewImageList(IShipBuildingView.PartSelectionView.CANNON,
                mCannonImageIDs);
        mShipBuildingActivity.setPartViewImageList(IShipBuildingView.PartSelectionView.ENGINE,
                mEngineImageIDs);
        mShipBuildingActivity.setPartViewImageList(IShipBuildingView.PartSelectionView.EXTRA_PART,
                mExtraPartImageIDs);
        mShipBuildingActivity.setPartViewImageList(IShipBuildingView.PartSelectionView.POWER_CORE,
                mPowerCoreImageIDs);
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
        if(mNoPartsSelected) mNoPartsSelected = false;
        //Select the part with the given index
        if(mBuilderState == ShipBuilderStates.States.MAIN_BODY_STATE) {
            mMainBodyID = index;
            AsteroidsGameModel.getInstance().getSpaceShip().setMainBody(
                    AsteroidsGameModel.getInstance().getMainBodies().get(mMainBodyID)
            );
        }
        else if(mBuilderState == ShipBuilderStates.States.ENGINE_STATE) {
            mEngineID = index;
            AsteroidsGameModel.getInstance().getSpaceShip().setEngine(
                    AsteroidsGameModel.getInstance().getEngines().get(mEngineID)
            );
        }
        else if(mBuilderState == ShipBuilderStates.States.EXTRA_PART_STATE) {
            mExtraPartID = index;
            AsteroidsGameModel.getInstance().getSpaceShip().setExtraPart(
                    AsteroidsGameModel.getInstance().getExtraParts().get(mExtraPartID)
            );
        }
        else if(mBuilderState == ShipBuilderStates.States.CANNON_STATE) {
            mCannonID = index;
            AsteroidsGameModel.getInstance().getSpaceShip().setCannon(
                    AsteroidsGameModel.getInstance().getCannons().get(mCannonID)
            );
        }
        else {
            mPowerCoreID = index;
            AsteroidsGameModel.getInstance().getSpaceShip().setPowerCore(
                    AsteroidsGameModel.getInstance().getPowerCores().get(mPowerCoreID)
            );
        }

        //Attach part to the image
        //If the ship is complete, shipBuildingActivity.setStartGameButton(true);
        mShipComplete = AsteroidsGameModel.getInstance().shipIsComplete();
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

    private void setArrows(){
        mShipBuildingActivity.setArrow(mBuilderState.getPartSelectionView(),
                IShipBuildingView.ViewDirection.RIGHT, true, mBuilderState.getNextName());
        mShipBuildingActivity.setArrow(mBuilderState.getPartSelectionView(),
                IShipBuildingView.ViewDirection.LEFT, true, mBuilderState.getPrevName());
        //Get rid of the up and down arrows
        mShipBuildingActivity.setArrow(mBuilderState.getPartSelectionView(),
                IShipBuildingView.ViewDirection.UP, false, "");
        mShipBuildingActivity.setArrow(mBuilderState.getPartSelectionView(),
                IShipBuildingView.ViewDirection.DOWN, false, "");
//        setArrow(PartSelectionView partView, ViewDirection arrow, boolean visible, String text)
    }
}
