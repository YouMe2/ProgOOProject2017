package de.uni_kiel.progOOproject17.model.abs;

import de.uni_kiel.progOOproject17.view.abs.Viewable;

public abstract class GameCompound extends GameComponent {

	public GameCompound(int x, int y, int w, int h) {
		super(x, y, w, h);

	}

	public abstract Viewable[] getViewables();

}
