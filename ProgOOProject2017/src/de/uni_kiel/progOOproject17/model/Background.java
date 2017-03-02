/**
 *
 */
package de.uni_kiel.progOOproject17.model;

import java.awt.Rectangle;

import de.uni_kiel.progOOproject17.view.abs.Viewable;


/**
 * @author Yannik Eikmeier
 * @since 23.02.2017
 */
public class Background extends GameComponent implements Viewable{
	
	private final String resKey;
	

	/**
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param i
	 */
	public Background(String resKey, int x, int y, int w, int h) {
		super(x, y, w, h);
		this.resKey = resKey;
	}

	@Override
	public void tick(long timestamp) {
		//TODOanimatedBG?
	}

	@Override
	public String getResourceKey() {
		return resKey;
	}
	
	@Override
	public Rectangle getViewRect() {
		return Background.this.getBoundingRect();
	}
	
	@Override
	public int getLayer() {
		return 0;
	}

}
