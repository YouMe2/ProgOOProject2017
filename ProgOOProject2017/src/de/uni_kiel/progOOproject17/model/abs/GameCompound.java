package de.uni_kiel.progOOproject17.model.abs;

import de.uni_kiel.progOOproject17.view.abs.OutputView;
import de.uni_kiel.progOOproject17.view.abs.Viewable;

/**
 * This class represents a {@link GameComponent} that might be holding a number of {@link Viewable}s in specific area.
 */
public abstract class GameCompound extends GameComponent {

	/**
	 * Constructs a new {@link GameCompound}.
	 * 
	 * @param x the x coord
	 * @param y the y coord
	 * @param w the width
	 * @param h the height
	 */
	public GameCompound(int x, int y, int w, int h) {
		super(x, y, w, h);

	}

	/**
	 * Returns all the {@link Viewable}s this {@link GameCompound} holds.
	 *
	 * @return all the {@link Viewable}s this holds
	 */
	public abstract Viewable[] getViewables();

}
