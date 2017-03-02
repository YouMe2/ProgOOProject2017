/**
 *
 */
package de.uni_kiel.progOOproject17.controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.security.KeyStore;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import de.uni_kiel.progOOproject17.controller.abs.TickedController;
import de.uni_kiel.progOOproject17.model.PLGameModel;
import de.uni_kiel.progOOproject17.model.abs.ModelAction;
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
		super(view, view, model, 40);

		// maybe add a button to toggel the lhView too?

		// init the actions for the standartIn (view)

		standardIn.addAction("pressed W", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {

				ModelAction.performAction(PLGameModel.ACTIONKEY_PLAYER_JUMP, e);

			}
		});

		// standardIn.addAction("pressed F",
		// ModelAction.getAction(PLGameModel.ACTIONKEY_PLAYER_JUMP));

		standardIn.addAction("pressed S", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {

				ModelAction.performAction(PLGameModel.ACTIONKEY_PLAYER_STARTCROUCH, e);
				// ModelAction.getAction(PLGameModel.ACTIONKEY_PLAYER_STARTCROUCH).setEnabled(false);
			}

		});
		standardIn.addAction("released S", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {

				ModelAction.performAction(PLGameModel.ACTIONKEY_PLAYER_STOPCROUCH, e);
				// ModelAction.getAction(PLGameModel.ACTIONKEY_PLAYER_STARTCROUCH).setEnabled(true);
			}

		});

		standardIn.addAction("pressed F", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {

				toggelLighthouseView();
				view.requestFocus();
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
