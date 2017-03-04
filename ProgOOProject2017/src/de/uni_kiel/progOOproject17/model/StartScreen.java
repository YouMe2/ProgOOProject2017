/**
 * 
 */
package de.uni_kiel.progOOproject17.model;

import javax.swing.Action;
import javax.swing.text.LayeredHighlighter;

import de.uni_kiel.progOOproject17.view.abs.Viewable;

/**
 * @author Yannik Eikmeier
 * @since 04.03.2017
 *
 */
public class StartScreen extends MenuScreen {

	
	
	
	/**
	 * @param w
	 * @param h
	 */
	public StartScreen(int w, int h, Action newGameAction, Action exitAction) {
		super(w, h, new String[]{"play","exit"}, new Action[]{newGameAction, exitAction});
		setBackground("black");
		
		addViewable(new ImageViewable("title",PLGameModel.lhToGam(8, 0, 12, 3), Viewable.FLOOR_LAYER));
		
	}

}
