/**
 * 
 */
package de.uni_kiel.progOOproject17.model;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.Action;

import de.uni_kiel.progOOproject17.view.abs.Viewable;

/**
 * @author Yannik Eikmeier
 * @since 04.03.2017
 *
 */
public class EndScreen extends MenuScreen {

	private Scoreboard scoreboard;

	/**
	 * @param w
	 * @param h
	 */
	public EndScreen(int w, int h, Stats stats , Action newGameAction, Action exitAction) {
		super(w, h, new String[]{"play","exit"}, new Action[]{newGameAction, exitAction});
		

		scoreboard = new Scoreboard(stats);
		
	}
	
	/* (non-Javadoc)
	 * @see de.uni_kiel.progOOproject17.model.MenuScreen#tick(long)
	 */
	@Override
	public void tick(long timestamp) {
		scoreboard.tick(timestamp);
		super.tick(timestamp);
	}
	
	
	/* (non-Javadoc)
	 * @see de.uni_kiel.progOOproject17.model.MenuScreen#getViewables()
	 */
	@Override
	public Viewable[] getViewables() {
		
		ArrayList<Viewable> views = new ArrayList<>();
		views.addAll(Arrays.asList(scoreboard.getViewables()));
		views.addAll((Arrays.asList(super.getViewables())));
		return views.toArray(new Viewable[views.size()]);
		
	}

}
