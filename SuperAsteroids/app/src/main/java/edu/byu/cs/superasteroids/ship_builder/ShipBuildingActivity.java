package edu.byu.cs.superasteroids.ship_builder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.superasteroids.R;
import edu.byu.cs.superasteroids.base.ActivityView;
import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.game.GameActivity;

/**
 * The Android Activity used for the ship building process
 */
public class ShipBuildingActivity extends ActivityView implements IShipBuildingView {
	
	private GestureDetectorCompat mDetector; 
	
	private PartSelectionFragment mainBodyFragment = new PartSelectionFragment();
	private PartSelectionFragment engineFragment = new PartSelectionFragment();
	private PartSelectionFragment cannonFragment = new PartSelectionFragment();
	private PartSelectionFragment extraPartFragment = new PartSelectionFragment();
	private PartSelectionFragment powerCoreFragment = new PartSelectionFragment();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// remove title bar.
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_ship_building);


        //TODO: Set this activity's controller to an instance of your ShipBuildingController
        //TODO: Pass the ShipBuildingController's constructor a reference to its IShipBuildingView (this)
        IShipBuildingController controller = new ShipBuildingController(this);
        setController(controller);


        //Configure the part selection fragments
        mainBodyFragment.setPartView(PartSelectionView.MAIN_BODY);
        mainBodyFragment.setController(getController());
        mainBodyFragment.setPartsAdapter(new PartsAdapter(this, 0, new ArrayList<Integer>()));
        engineFragment.setPartView(PartSelectionView.ENGINE);
        engineFragment.setController(getController());
        engineFragment.setPartsAdapter(new PartsAdapter(this, 0, new ArrayList<Integer>()));
        cannonFragment.setPartView(PartSelectionView.CANNON);
        cannonFragment.setController(getController());
        cannonFragment.setPartsAdapter(new PartsAdapter(this, 0, new ArrayList<Integer>()));
        extraPartFragment.setPartView(PartSelectionView.EXTRA_PART);
        extraPartFragment.setController(getController());
        extraPartFragment.setPartsAdapter(new PartsAdapter(this, 0, new ArrayList<Integer>()));
        powerCoreFragment.setPartView(PartSelectionView.POWER_CORE);
        powerCoreFragment.setController(getController());
        powerCoreFragment.setPartsAdapter(new PartsAdapter(this, 0, new ArrayList<Integer>()));

        if(getController() != null)
            getController().loadContent(ContentManager.getInstance());


        //Move the main body  part selection view into this view
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, mainBodyFragment)
        	.commit();
        
        mDetector = new GestureDetectorCompat(this, new FlingListener());
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getController() != null)
            getController().onResume();
    }

	@Override 
	public boolean onTouchEvent(MotionEvent event){ 
	    this.mDetector.onTouchEvent(event);
	    // Be sure to call the superclass implementation
	    return super.onTouchEvent(event);
	}

	@Override
	public void animateToView(PartSelectionView view, ViewDirection animationDirection) {
		Fragment viewFragment = getPieceSelectionView(view);
		this.getSupportFragmentManager().beginTransaction()
			.setCustomAnimations(getSlideInDirection(animationDirection), getSlideOutDirection(animationDirection))
			.replace(R.id.fragment_container, viewFragment)
			.commit();
	}
	
	@Override
	public IShipBuildingController getController() {
		return (IShipBuildingController)super.getController();
	}

    /**
     * A customized gesture detector used to detect and respond to flinging motions.
     */
    class FlingListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, 
                float velX, float velY) {

            if(getController() != null) {

                float absX = Math.abs(event1.getRawX() - event2.getRawX());
                float absY = Math.abs(event1.getRawY() - event2.getRawY());

                if (event2.getRawX() < event1.getRawX() && absX > absY) {
                    getController().onSlideView(ViewDirection.LEFT);
                    return true;
                }
                if (event2.getRawX() > event1.getRawX()  && absX > absY) {
                    getController().onSlideView(ViewDirection.RIGHT);
                    return true;
                }
                if (event2.getRawY() < event1.getRawY()  && absX < absY) {
                    getController().onSlideView(ViewDirection.UP);
                    return true;
                }
                if (event2.getRawY() > event1.getRawY() && absX < absY) {
                    getController().onSlideView(ViewDirection.DOWN);
                    return true;
                }

/*
                float absX = Math.abs(velX);
                float absY = Math.abs(velY);
                if (velX < 0 && absX > absY)
                    getController().onSlideView(ViewDirection.LEFT);
                if (velX > 0 && absX > absY)
                    getController().onSlideView(ViewDirection.RIGHT);
                if (velY < 0 && absX < absY)
                    getController().onSlideView(ViewDirection.UP);
                if (velY > 0 && absX < absY)
                    getController().onSlideView(ViewDirection.DOWN);
*/
            }
    		
    		return false;
        }
    }
	
	private PartSelectionFragment getPieceSelectionView(PartSelectionView view) {
		switch(view) {
			case MAIN_BODY:		return mainBodyFragment;
			case EXTRA_PART: 	return extraPartFragment;
			case ENGINE:		return engineFragment;
			case CANNON:		return cannonFragment;
			case POWER_CORE:	return powerCoreFragment;
			default: assert false; return null;
		}
	}
	
	private int getSlideInDirection(ViewDirection animationDirection) {
		switch(animationDirection) {
			case LEFT: 		return android.R.anim.slide_in_left;
			case RIGHT: 	return R.anim.slide_in_right;
			case DOWN: 		return R.anim.abc_slide_in_bottom;
			case UP:		return R.anim.abc_slide_in_top;
			default:	assert false; return -1;
		}
	}
	
	private int getSlideOutDirection(ViewDirection animationDirection) {
		ViewDirection opposite = getOppositeDirection(animationDirection);
		switch(opposite) {
			case LEFT: 		return R.anim.slide_out_left;
			case RIGHT: 	return android.R.anim.slide_out_right;
			case DOWN: 		return R.anim.abc_slide_out_bottom;
			case UP:		return R.anim.abc_slide_out_top;
			default:	assert false; return -1;
		}
	}
	
	public ViewDirection getOppositeDirection(ViewDirection direction) {
		switch(direction) {
		case LEFT: 		return ViewDirection.RIGHT;
		case RIGHT: 	return ViewDirection.LEFT;
		case DOWN: 		return ViewDirection.UP;
		case UP:		return ViewDirection.DOWN;
		default:	assert false; return null;
	}
	}

	@Override
	public void setPartViewImageList(PartSelectionView view, List<Integer> partImageIds) {
		getPieceSelectionView(view).setPartImages(partImageIds);
	}

	@Override
	public void startGame() {
        mainBodyFragment.stopDrawing();
        extraPartFragment.stopDrawing();
        cannonFragment.stopDrawing();
        engineFragment.stopDrawing();
        powerCoreFragment.stopDrawing();
        Intent intent = new Intent(this, GameActivity.class);
        intent.setFlags(android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP);
        this.startActivity(intent);
	}

    @Override
    public void setStartGameButton(boolean enabled) {
        mainBodyFragment.setStartGameButton(enabled);
        extraPartFragment.setStartGameButton(enabled);
        cannonFragment.setStartGameButton(enabled);
        engineFragment.setStartGameButton(enabled);
        powerCoreFragment.setStartGameButton(enabled);
    }

    @Override
    public void setArrow(PartSelectionView partView, ViewDirection arrow, boolean visible, String text) {
        getPieceSelectionView(partView).setArrow(arrow, visible, text);
    }
}
