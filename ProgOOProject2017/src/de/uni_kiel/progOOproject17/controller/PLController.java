package de.uni_kiel.progOOproject17.controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import de.uni_kiel.progOOproject17.controller.abs.TickedController;
import de.uni_kiel.progOOproject17.model.PLGameModel;
import de.uni_kiel.progOOproject17.model.abs.ModelAction;
import de.uni_kiel.progOOproject17.view.PLDektopView;
import de.uni_kiel.progOOproject17.view.PLLighthouseView;

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


		standardIn.addAction("pressed W", ModelAction.getAction(PLGameModel.ACTIONKEY_PLAYER_JUMP));
		standardIn.addAction("pressed S", ModelAction.getAction(PLGameModel.ACTIONKEY_PLAYER_STARTCROUCH));
		standardIn.addAction("released S", ModelAction.getAction(PLGameModel.ACTIONKEY_PLAYER_STOPCROUCH));

		standardIn.addAction("pressed F", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {

				toggelLighthouseView();
				view.requestFocus();

			}

		});

		standardIn.addAction("pressed SPACE", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				gametimeSafer = stop();
			}
		});
		
		standardIn.addAction("released SPACE", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				start(gametimeSafer);
			}
		});
		
		//TODO Restart?

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
