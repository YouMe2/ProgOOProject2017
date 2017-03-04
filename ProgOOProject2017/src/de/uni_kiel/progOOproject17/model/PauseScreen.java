/**
 * 
 */
package de.uni_kiel.progOOproject17.model;

import javax.swing.Action;

/**
 * @author Yannik Eikmeier
 * @since 04.03.2017
 *
 */
public class PauseScreen extends MenuScreen {

	/**
	 * @param w
	 * @param h
	 */
	public PauseScreen(int w, int h, Action resumeAction, Action exitAction) {
		super(w, h, new String[]{"play","exit"}, new Action[]{resumeAction, exitAction});
		
	}


}
