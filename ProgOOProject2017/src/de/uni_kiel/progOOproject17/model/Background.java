/**
 *
 */
package de.uni_kiel.progOOproject17.model;

import java.awt.Graphics;
import java.awt.Image;

import de.uni_kiel.progOOproject17.view.abs.ImageViewable;
import de.uni_kiel.progOOproject17.view.abs.Viewable;


/**
 * @author Yannik Eikmeier
 * @since 23.02.2017
 */
public class Background extends GameComponent {

	private final Viewable view;


	/**
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param i
	 */
	public Background(int x, int y, int w, int h, Image hi, Image low) {
		super(x, y, w, h);

		
		view = new ImageViewable(hi, low, getBoundingRect());


	}

	@Override
	public void tick(long timestamp) {
		// TODO could be animated? xD

	}

	@Override
	public Viewable getViewable() {
		return view;
	}

}
