package de.uni_kiel.progOOproject17.model;

import de.uni_kiel.progOOproject17.resources.GameProperties;
import de.uni_kiel.progOOproject17.view.abs.Viewable;
import javax.swing.Action;

public class StartScreen extends MenuScreen {

	/**
	 * @param w
	 * @param h
	 */
	public StartScreen(int w, int h, Action newGameAction, Action exitAction) {
		super(w, h, new String[] { "play", "exit" }, new Action[] { newGameAction, exitAction });
		setBackground("black");

		addViewable(new ImageViewable(GameProperties.getInstance().getProperty("titleResKey"), PLBaseModel.lhToGam(8, 0, 12, 3), Viewable.FLOOR_LAYER));

	}

}
