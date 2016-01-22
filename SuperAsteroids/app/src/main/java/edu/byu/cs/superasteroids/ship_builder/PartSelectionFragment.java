package edu.byu.cs.superasteroids.ship_builder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.byu.cs.superasteroids.R;
import edu.byu.cs.superasteroids.base.FragmentView;
import edu.byu.cs.superasteroids.content.ContentManager;

/**
 * This class represents a part selection fragment that can be reused for different ship parts.
 */
public class PartSelectionFragment extends FragmentView {

	private ShipBuilderShipView gameView;
	private PartsAdapter partsAdapter;
    private IShipBuildingView.PartSelectionView partView;
    private View partChooserView;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        partChooserView = inflater.inflate(R.layout.fragment_part_chooser, container, false);
        setStartGameButton(false);
		
		FrameLayout gameFrame = (FrameLayout)partChooserView.findViewById(R.id.part_chooser_frame);
		gameView = new ShipBuilderShipView(getActivity(), getController());
		gameView.loadContent(ContentManager.getInstance());
		gameFrame.addView(gameView);
        gameView.runLoop();

		GridView partsList = (GridView)partChooserView.findViewById(R.id.part_chooser_parts_gridview);
		partsList.setAdapter(partsAdapter);
		partsList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				getController().onPartSelected(position);
			}
		});
		
		Button button = (Button)partChooserView.findViewById(R.id.part_chooser_start_button);

        if(getController() != null)
		    button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				getController().onStartGamePressed();
			}
		});
		
		partsAdapter.notifyDataSetChanged();

        if(getController() != null)
            getController().onViewLoaded(partView);

		return partChooserView;
	}
	
	@Override
	public IShipBuildingController getController() {
		return (IShipBuildingController)super.getController();
	}

	public void setPartsAdapter(PartsAdapter p) {
		partsAdapter = p;
	}
	
	public void setPartImages(List<Integer> partImages) {		
		partsAdapter.setImages(partImages);
	}

    public void setStartGameButton(boolean enabled) {
        if(partChooserView != null) {
            Button button = (Button) partChooserView.findViewById(R.id.part_chooser_start_button);
            button.setEnabled(enabled);
        }
    }

    public void setArrow(IShipBuildingView.ViewDirection arrow, boolean visible, String text) {

        if(text == null)
            text = "";

        ImageView arrowImage = null;
        TextView textView = null;
        if(arrow == IShipBuildingView.ViewDirection.LEFT) {
            arrowImage = (ImageView)partChooserView.findViewById(R.id.left_arrow);
            textView = (TextView)partChooserView.findViewById(R.id.left_text);
        }
        else if(arrow == IShipBuildingView.ViewDirection.UP) {
            arrowImage = (ImageView)partChooserView.findViewById(R.id.top_arrow);
            textView = (TextView)partChooserView.findViewById(R.id.top_text);
        }
        else if(arrow == IShipBuildingView.ViewDirection.RIGHT) {
            arrowImage = (ImageView)partChooserView.findViewById(R.id.right_arrow);
            textView = (TextView)partChooserView.findViewById(R.id.right_text);
        }
        else if(arrow == IShipBuildingView.ViewDirection.DOWN) {
            arrowImage = (ImageView)partChooserView.findViewById(R.id.bottom_arrow);
            textView = (TextView)partChooserView.findViewById(R.id.bottom_text);
        }

        if(visible) {
            arrowImage.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
        }
        else {
            arrowImage.setVisibility(View.INVISIBLE);
            arrowImage.setVisibility(View.INVISIBLE);
        }

        textView.setText(text);
    }

    public IShipBuildingView.PartSelectionView getPartView() {
        return partView;
    }

    public void stopDrawing() {
        gameView.stopDrawing();
    }

    public void setPartView(IShipBuildingView.PartSelectionView partView) {
        this.partView = partView;
    }
}
