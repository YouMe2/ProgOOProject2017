package de.uni_kiel.progOOproject17.model;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.Action;

import de.uni_kiel.progOOproject17.view.abs.Viewable;

/**
 * This class represents a {@link MenuScreen} which will show after the game
 * ended.
 */
public class EndScreen extends MenuScreen {

	private Scoreboard scoreboard;

	/**
	 * Constructs a new {@link EndScreen} that will show the given {@link Stats}
	 * and a simple {@link MenuScreen} with a retry action and a exit action.
	 * 
	 * @param w the width
	 * @param h the height
	 * @param stats the {@link Stats} to show
	 * @param newGameAction the retry action
	 * @param exitAction the exit action
	 */
	public EndScreen(int w, int h, Stats stats, Action newGameAction, Action exitAction) {
		super(w, h, new String[] { "play", "exit" }, new Action[] { newGameAction, exitAction });

		scoreboard = new Scoreboard(stats);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.uni_kiel.progOOproject17.model.MenuScreen#tick(long)
	 */
	@Override
	public void tick(long timestamp) {
		scoreboard.tick(timestamp);
		super.tick(timestamp);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.uni_kiel.progOOproject17.model.MenuScreen#getViewables()
	 */
	@Override
	public Viewable[] getViewables() {

		ArrayList<Viewable> views = new ArrayList<>();
		views.addAll(Arrays.asList(scoreboard.getViewables()));
		views.addAll(Arrays.asList(super.getViewables()));
		return views.toArray(new Viewable[views.size()]);

	}

}
