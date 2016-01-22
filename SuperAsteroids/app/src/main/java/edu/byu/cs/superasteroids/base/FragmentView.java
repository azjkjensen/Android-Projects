package edu.byu.cs.superasteroids.base;

import android.support.v4.app.Fragment;

/**
 * A base class with the functionality of a view and a an android fragment
 */
public class FragmentView extends Fragment implements IView{

	private IController controller;

	@Override
	public IController getController() {
		return controller;
	}

	@Override
	public void setController(IController controller) {
		this.controller = controller;
	}
	
}
