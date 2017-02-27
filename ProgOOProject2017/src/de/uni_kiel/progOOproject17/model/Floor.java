/**
 * 
 */
package de.uni_kiel.progOOproject17.model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import de.uni_kiel.progOOproject17.view.abs.Viewable;

/**
 * @author Yannik Eikmeier
 * @since 23.02.2017
 *
 */
public class Floor extends GameObject{

	
	private boolean deadly = false;
	private int killcounter;


	public Floor(int x, int y, int w, int h, Image hi, Image low) {
		super(x, y, w, h);

		setNewImageView(hi, low);

	}

	@Override
	public void tick(long timestamp) {
		// TODO animated?

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
