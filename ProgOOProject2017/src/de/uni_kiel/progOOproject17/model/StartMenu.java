package de.uni_kiel.progOOproject17.model;

import de.uni_kiel.progOOproject17.resources.GameProperties;
import de.uni_kiel.progOOproject17.view.abs.Viewable;
import javax.swing.Action;

/**
 * This class represents a {@link MenuScreen} which acts as the
 * {@link StartMenu} at the beginning of the game.
 * 
 */
public class StartMenu extends MenuScreen {

	/**
	 * Constructs a new {@link StartMenu}.
	 * 
	 * @param w the width
	 * @param h the height
	 */
	public StartMenu(int w, int h, Action newGameAction, Action exitAction) {
		super(w, h, new String[] { "play", "exit" }, new Action[] { newGameAction, exitAction });
		setBackground("black");

		addViewable(new SimpleViewable(GameProperties.getInstance().getProperty("titleResKey"), PLBaseModel.lhToGam(8, 0, 12, 3), Viewable.FLOOR_LAYER));

	}

}
