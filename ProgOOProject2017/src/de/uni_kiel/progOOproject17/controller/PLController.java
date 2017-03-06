package de.uni_kiel.progOOproject17.controller;

import de.uni_kiel.progOOproject17.controller.abs.TickedController;
import de.uni_kiel.progOOproject17.model.InputActionKeys;
import de.uni_kiel.progOOproject17.model.PLGameModel;
import de.uni_kiel.progOOproject17.view.PLDektopView;
import de.uni_kiel.progOOproject17.view.PLLighthouseView;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

public class PLController extends TickedController {

	private PLLighthouseView lhView;
	private PLGameModel myModel;

	private long gametimeSafer = 0;

	/**
	 * @param view
	 * @param model
	 */
	public PLController(PLDektopView view, PLGameModel model) {
		super(view, view, model, 35);
//		super(view, view, model, 300);

		standardIn.addAction("pressed W", model.getAction(InputActionKeys.P_UP));
		standardIn.addAction("released W", model.getAction(InputActionKeys.R_UP));
		standardIn.addAction("pressed S", model.getAction(InputActionKeys.P_DOWN));
		standardIn.addAction("released S", model.getAction(InputActionKeys.R_DOWN));
		standardIn.addAction("pressed SPACE", model.getAction(InputActionKeys.P_SELECT));
		standardIn.addAction("released SPACE", model.getAction(InputActionKeys.R_SELECT));

		standardIn.addAction("pressed F", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {

				toggelLighthouseView();
				view.requestFocus();

			}

		});

		standardIn.addAction("pressed E", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				gametimeSafer = stop();
			}
		});

		standardIn.addAction("released E", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				start(gametimeSafer);
			}
		});

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
