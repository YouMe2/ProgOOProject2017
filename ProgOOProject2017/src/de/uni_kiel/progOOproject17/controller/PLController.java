/**
 *
 */
package de.uni_kiel.progOOproject17.controller;

import de.uni_kiel.progOOproject17.controller.abs.TickedController;
import de.uni_kiel.progOOproject17.model.PLGameModel;
import de.uni_kiel.progOOproject17.view.PLDektopView;
import de.uni_kiel.progOOproject17.view.PLLighthouseView;

/**
 * @author Yannik Eikmeier
 * @since 23.02.2017
 */
public class PLController extends TickedController {

	private PLLighthouseView lhView;
	private PLGameModel myModel;

	/**
	 * @param view
	 * @param model
	 */
	public PLController(PLDektopView view, PLGameModel model) {
		super(view, view, model, 400);
		

		// maybe add a button to toggel the lhView too?

		// init the actions for the standartIn (view)

	}

	private void toggelLighthouseView() {
		if (lhView == null) {
			lhView = new PLLighthouseView("LighthouseView");
			addOutputView(lhView);
			addInputView(lhView);

			// add Actions to lhView here

		} else
			lhView.setVisible(!lhView.isVisible());
	}

	@Override
	public PLGameModel getModel() {
		if (myModel == null)
			myModel = (PLGameModel) model;
		return myModel;
	}

}
