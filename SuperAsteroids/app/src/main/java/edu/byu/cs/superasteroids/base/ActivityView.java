package edu.byu.cs.superasteroids.base;

import android.support.v4.app.FragmentActivity;

/**
 * A base class with the functionality of an android FragmentActivity and a generic view/controller
 * view.
 */
public class ActivityView extends FragmentActivity implements IView {

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
