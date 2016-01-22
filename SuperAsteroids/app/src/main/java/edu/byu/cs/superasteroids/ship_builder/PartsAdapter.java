package edu.byu.cs.superasteroids.ship_builder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

import edu.byu.cs.superasteroids.content.ContentManager;

/**
 * The list adapter used to adapt a list of image IDs to a list of views containing the images of the
 * image IDs.
 */
public class PartsAdapter extends ArrayAdapter<Integer> {

	private List<Integer> parts;
	private Context context;
	
	public PartsAdapter(Context context, int resource, List<Integer> parts) {
		super(context, resource, parts);
		this.context = context;
		this.parts = parts;
	}
	
@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
	    ImageView view = new ImageView(context);
        Bitmap image = ContentManager.getInstance().getImage(parts.get(position));
		image = getResizedBitmap(image,1,1);
        view.setImageBitmap(image);
		//view.setRotation(45.0f);
        //view.setScaleX(0.35f);
        //view.setScaleY(0.35f);
        view.setPadding(5, 5, 5, 5);
		return view;
	}

	public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
		int width = bm.getWidth();
		int height = bm.getHeight();
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		scaleHeight = 0.55f;
		scaleWidth = 0.60f;
		// CREATE A MATRIX FOR THE MANIPULATION
		Matrix matrix = new Matrix();
		// RESIZE THE BIT MAP
		matrix.postScale(scaleWidth, scaleHeight);

		// "RECREATE" THE NEW BITMAP
		Bitmap resizedBitmap = Bitmap.createBitmap(
				bm, 0, 0, width, height, matrix, false);
		//bm.recycle();
		return resizedBitmap;
	}	
	
	public void setImages(List<Integer> parts) {
		this.parts.clear();
		for(int i : parts) {
			this.parts.add(i);
		}
		this.notifyDataSetChanged();
	}
}
