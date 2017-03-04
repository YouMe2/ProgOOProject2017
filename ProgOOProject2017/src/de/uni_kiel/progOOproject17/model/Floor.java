package de.uni_kiel.progOOproject17.model;

import de.uni_kiel.progOOproject17.model.abs.GameObject;
import de.uni_kiel.progOOproject17.view.abs.Viewable;
import java.awt.Rectangle;

public class Floor extends GameObject {

	private boolean deadly = false;
	private int killcounter;

	public Floor(String resKey, Rectangle rect) {
		this(resKey, rect.x, rect.y, rect.width, rect.height);
	}

	public Floor(String resKey, int x, int y, int w, int h) {
		super(resKey, x, y, w, h);
		setLayer(Viewable.FLOOR_LAYER);
	}

	@Override
	public void tick(long timestamp) {
		// nothing here so far
	}

	@Override
	public boolean isDeadly() {
		return deadly;
	}

	@Override
	public void addKill() {
		killcounter++;
	}

	@Override
	public void setDeadly(boolean deadly) {
		this.deadly = deadly;
	}

}
