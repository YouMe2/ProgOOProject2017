/**
 *
 */
package de.uni_kiel.progOOproject17.model;

import de.uni_kiel.progOOproject17.view.abs.Viewable;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

/**
 * @author Yannik Eikmeier
 * @since 23.02.2017
 */
public class Floor extends GameObject {

	private boolean deadly = false;
	private int killcounter;

	public Floor(String resKey, int x, int y, int w, int h) {
		super(resKey, x, y, w, h);
		setLayer(Viewable.FLOOR_LAYER);

	}

	@Override
	public void tick(long timestamp) {
		//nothing here so far
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
