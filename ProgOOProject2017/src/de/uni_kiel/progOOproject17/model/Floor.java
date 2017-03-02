package de.uni_kiel.progOOproject17.model;

import java.awt.Rectangle;

import de.uni_kiel.progOOproject17.model.abs.Environment;
import de.uni_kiel.progOOproject17.model.abs.GameObject;
import de.uni_kiel.progOOproject17.view.abs.Viewable;

public class Floor extends GameObject {

	private boolean deadly = false;
	private int killcounter;

	
	/**
	 * 
	 */
	public Floor(String resKey, Rectangle rect, Environment environment) {
		this(resKey, rect.x, rect.y, rect.width, rect.height, environment);
	}
	
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
