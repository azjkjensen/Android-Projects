package edu.byu.cs.superasteroids.base;

public interface IController {

    /**
     * Gets the controller's view
     * @return the controller's view
     */
	IView getView();

    /**
     * Sets the controller's view
     * @param view the view to set
     */
	void setView(IView view);
}
