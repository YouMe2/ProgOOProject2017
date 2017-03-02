package de.uni_kiel.progOOproject17.model;

import de.uni_kiel.progOOproject17.view.abs.Viewable;

public class Floor extends GameObject {

	private boolean deadly = false;
	private int killcounter;

	public Floor(String resKey, int x, int y, int w, int h, Environment environment) {
		super(resKey, x, y, w, h, environment);
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

	public void setDeadly(boolean deadly) {
		this.deadly = deadly;
	}

}
