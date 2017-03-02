package de.uni_kiel.progOOproject17.model;

import de.uni_kiel.progOOproject17.view.abs.Viewable;
import java.awt.Rectangle;

public class Background extends GameComponent implements Viewable {

	private final String resKey;

	public Background(String resKey, int x, int y, int w, int h) {
		super(x, y, w, h);
		this.resKey = resKey;
	}

	@Override
	public void tick(long timestamp) {
		// TODOanimatedBG?
	}

	@Override
	public String getResourceKey() {
		return resKey;
	}

	@Override
	public Rectangle getViewRect() {
		return getBoundingRect();
	}

	@Override
	public int getLayer() {
		return 0;
	}

}
