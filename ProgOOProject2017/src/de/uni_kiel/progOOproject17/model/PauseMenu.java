package de.uni_kiel.progOOproject17.model;

import javax.swing.Action;

/**
 * This class represents a simple {@link MenuScreen} that acts as a overlay friendly {@link PauseMenu} with no background.
 * 
 */
public class PauseMenu extends MenuScreen {

	
	/**
	 * Constructs a new {@link PauseMenu}.
	 * 
	 * @param w the width
	 * @param h the height
	 * @param resumeAction the resume action
	 * @param exitAction the exit action
	 */
	public PauseMenu(int w, int h, Action resumeAction, Action exitAction) {
		super(w, h, new String[] { "play", "exit" }, new Action[] { resumeAction, exitAction });

	}

}
