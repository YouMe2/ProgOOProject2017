package de.uni_kiel.progOOproject17.controller;

import de.uni_kiel.progOOproject17.controller.abs.TickedController;
import de.uni_kiel.progOOproject17.model.InputActionKey;
import de.uni_kiel.progOOproject17.model.PLBaseModel;
import de.uni_kiel.progOOproject17.resources.GameProperties;
import de.uni_kiel.progOOproject17.view.PLDektopView;
import de.uni_kiel.progOOproject17.view.PLLighthouseView;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * This class represents the controller of this MVC structure and therefore the
 * access point to the whole program it self. This class is the most powerful in
 * the whole Program structure followed by the {@link PLBaseModel}.
 * 
 * It is the intermediary between all the Views, which are split into inputs and
 * outputs, and the model, which itself contains a massively branched structure.
 * The creation of an {@link PLController} requires a {@link PLDektopView}
 * and a {@link PLBaseModel}. After creation the controller awaits a call of the
 * {@link #start(long)} method which will start the ticked gamecycle. It can
 * also be stooped with the {@link #stop()} method and then restarted with again
 * with the {@link #start(long)} method.
 *
 */
public class PLController extends TickedController {

	/**
	 * stores the LighthouseView if there is one.
	 */
	private PLLighthouseView lhView;
	/**
	 * stores the casted model to prevent casting on every call of
	 * {@link #getModel()}
	 */
	private PLBaseModel myModel;

	/**
	 * stores the gameTime for a (re-)start when stopped for debugging purposes
	 * 
	 * @see #stop()
	 * @see #start(long)
	 */
	private long gametimeSafer = 0;

	/**
	 * Constructs a new {@link PLController} and sets the standard view and
	 * model. Initializes the InputActions in the standardIn as well as the
	 * debugging action: E to freeze the ticks, T to print FPS and TPS
	 * 
	 * 
	 * @param view
	 *            the {@link PLDektopView} serving as standartIn and standardOut
	 * @param model
	 *            the {@link PLBaseModel} hosting the game.
	 */
	public PLController(PLDektopView view, PLBaseModel model) {
		super(view, view, model, Integer.valueOf(GameProperties.getInstance().getProperty("tickLength")));

		// standard Game Actions:
		standardIn.addAction("pressed S", model.getAction(InputActionKey.P_UP));
		standardIn.addAction("released S", model.getAction(InputActionKey.R_UP));
		standardIn.addAction("pressed D", model.getAction(InputActionKey.P_DOWN));
		standardIn.addAction("released D", model.getAction(InputActionKey.R_DOWN));
		standardIn.addAction("pressed SPACE", model.getAction(InputActionKey.P_SELECT));
		standardIn.addAction("released SPACE", model.getAction(InputActionKey.R_SELECT));

		
		// LH Action
		standardIn.addAction("pressed F", new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 8281682912401576328L;

			@Override
			public void actionPerformed(ActionEvent e) {

				toggleLighthouseView();
				view.requestFocus();

			}

		});

		
		// debugging actions
		standardIn.addAction("pressed E", new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -7423115611510814418L;

			@Override
			public void actionPerformed(ActionEvent e) {
				gametimeSafer = stop();
			}
		});

		standardIn.addAction("released E", new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 4385435299522741604L;

			@Override
			public void actionPerformed(ActionEvent e) {
				start(gametimeSafer);
			}
		});

		standardIn.addAction("pressed T", new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 305735706610084922L;

			@Override
			public void actionPerformed(ActionEvent e) {
				toggelEnableTpsFpsPrint();
			}
		});

	}

	/**
	 * Toggles the visibility of the {@link PLLighthouseView} and constructs a
	 * new one if there was none before.
	 */
	private void toggleLighthouseView() {
		if (lhView == null) {
			lhView = new PLLighthouseView("LighthouseView");
			addOutputView(lhView);
			addInputView(lhView);
			// add Actions to lhView here

		} else
			lhView.setVisible(!lhView.isVisible());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.uni_kiel.progOOproject17.controller.abs.TickedController#getModel()
	 */
	@Override
	public PLBaseModel getModel() {
		if (myModel == null)
			myModel = (PLBaseModel) model;
		return myModel;
	}

}
