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
public class Floor extends GameObject implements Deadly{

	private Color color;
	private Image img;
	private final Viewable view;
	
	private boolean deadly = false;
	private int killcounter;

	public Floor(int x, int y, int w, int h, Color c) {
		this(x, y, w, h, c, null);
	}

	public Floor(int x, int y, int w, int h, Color c, Image i) {
		super(x, y, w, h);
		color = c;
		img = i;

		view = new Viewable() {

			@Override
			public void renderLOW(Graphics gr) {
				gr.setColor(color);
				gr.fillRect(getX(), getY(), getWidth(), getHeight());
			}

			@Override
			public void render(Graphics gr) {
				if (img == null) {
					renderLOW(gr);
					return;

				}
				gr.drawImage(img, getX(), getY(), getWidth(), getHeight(), null);

			}
		};

	}

	@Override
	public void tick(long timestamp) {
		// TODO animated?

	}

	@Override
	public Viewable getViewable() {
		return view;
	}

	@Override
	public boolean isAlive() {
		return true;
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
